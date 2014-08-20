package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.CommentCount;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.SubType;
import com.zhy.listen.cache.CacheConstants;
import com.zhy.listen.cache.JedisClient;
import com.zhy.listen.cache.KeyGenerator;
import com.zhy.listen.cache.Memcached;
import com.zhy.listen.dao.CommentDAO;
import com.zhy.listen.entities.Comment;
import com.zhy.listen.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private Memcached memcached;

    @Override
    public boolean comment(Comment comment) {
        boolean isSuccess = commentDAO.save(comment) > 0;

        // 根据dependId增加评论个数
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + comment.getCommentType().name(), comment.getDependId());
        jedisClient.jedis.incr(key);

        // 放入单条
        findById(comment.getId());

        // 放入关联到的新鲜事
        String newsKey = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_ID_LIST + comment.getCommentType().name(), comment.getDependId());
        jedisClient.lpush(newsKey, comment.getId());
        return isSuccess;
    }

    @Override
    public Paging<Comment> getCommentsByTypeAndDependId(Comment comment) {
        List<Comment> result = new ArrayList<Comment>();
        int totalRecord = 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_ID_LIST + comment.getCommentType().name(), comment.getDependId());
        List<Long> ids = jedisClient.lrange(key, comment.getSinceCount(), comment.getEndPoint());
        if (ids == null) {
            List<Comment> comments = commentDAO.getCommentsByTypeAndDependId(comment);

            // 存放单条数据
            if (comments != null) {
                for (Comment c : comments) {
                    String everyKey = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_DETAIL, c.getId());
                    memcached.set(everyKey, c, CacheConstants.TIME_HOUR * 4);
                }

                // 存放id list
                List<Long> idList = commentDAO.getCommentIdsByTypeAndDependIdCount(comment);
                jedisClient.rpush(key, idList);
            }

            int total = getCommentsByTypeAndDependIdCount(comment);
            return new Paging<Comment>(total, comment.getPage(), comment.getPageSize(), comments);
        } else {

            // 从memcached获取单条
            String keys[] = new String[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                keys[i] = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_DETAIL, ids.get(i));
            }
            Map<String, Comment> comments = memcached.getMulti(keys);
            if (comments == null) {
                comments = new HashMap<String, Comment>();
            }

            // memcache中没有该数据
            if (comments.size() != keys.length) {
                List<Long> needFromDBIds = new ArrayList<Long>();

                // 按照当前分页的id列表查询
                if (comments.size() > 0) {
                    for (int i = 0; i < keys.length; i++) {
                        if (!comments.containsKey(keys[i])) {
                            needFromDBIds.add(ids.get(i));
                        }
                    }
                }
                List<Comment> currentComments = findByIds(needFromDBIds);
                if (currentComments != null && currentComments.size() > 0) {
                    for (Comment c : currentComments) {
                        comments.put(KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_DETAIL, c.getId()), c);
                        String everyKey = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_DETAIL, c.getId());
                        memcached.set(everyKey, c, CacheConstants.TIME_HOUR * 4);
                    }
                }
            }

            // 重新排序
            for (String eKey : keys) {
                result.add(comments.get(eKey));
            }
        }

        // 获取总条数
        totalRecord = getCommentsByTypeAndDependIdCount(comment);
        return new Paging<Comment>(totalRecord, comment.getPage(), comment.getPageSize(), result);
    }

    @Override
    public int getCommentsByTypeAndDependIdCount(Comment comment) {
        int totalRecord = 0;
        String countKey = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + comment.getCommentType().name(), comment.getDependId());
        String value = jedisClient.get(countKey);
        if (value == null || value.length() == 0) {
            totalRecord = commentDAO.getCommentsByTypeAndDependIdCount(comment);
            jedisClient.set(countKey, totalRecord);
        } else {
            totalRecord = Integer.parseInt(value);
        }
        return totalRecord;
    }

    @Override
    public boolean remove(Long commentId) {
        Comment comment = commentDAO.getById(commentId);
        if (comment != null) {
            boolean isSuccess = commentDAO.delete(commentId) != 0 ? true : false;
            if(isSuccess) {
                
                // 评论条数-1
                String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + comment.getCommentType().name(), comment.getDependId());
                Long value = jedisClient.jedis.decr(key);
                if (value < 0) {
                    jedisClient.set(key, 0);
                }
                
                //  评论id list 删除 该id
                String idListKey = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_ID_LIST + comment.getCommentType().name(), comment.getDependId());
                jedisClient.lrem(idListKey, 1, commentId);
                
                
                // 删除单条
                String detailKey = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_DETAIL, commentId);
                memcached.delete(detailKey);
            }
            return isSuccess;
        }
        return false;
    }

    @Override
    public Map<Long, Integer> findCommentsCountsByIds(List<SubType> types, List<Long> ids) {
        Map<Long, Integer> result = new HashMap<Long, Integer>();
        List<Long> needFromDBIds = new ArrayList<Long>();
        List<SubType> needFromDBTypes = new ArrayList<SubType>();
        String[] keys = new String[ids.size()];
        for (int i = 0; i < keys.length; i++) {
            String keyPrefix = CacheConstants.CACHE_COMMENT_COUNT + types.get(i);
            String key = KeyGenerator.generateKey(keyPrefix, ids.get(i));
            String value = jedisClient.jedis.get(key);
            if (value == null) {
                needFromDBIds.add(ids.get(i));
                needFromDBTypes.add(types.get(i));
            } else {
                result.put(ids.get(i), Integer.parseInt(value));
            }
        }
        if (result.size() != ids.size()) {
            List<CommentCount> commentCounts = commentDAO.getCommentsCountsByIds(needFromDBTypes, needFromDBIds);
            if (commentCounts != null && commentCounts.size() > 0) {
                for (CommentCount commentCount : commentCounts) {
                    String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + commentCount.getType().name(), commentCount.getDependId());
                    jedisClient.set(key, commentCount.getCount());
                    result.put(commentCount.getDependId(), commentCount.getCount());
                }
            } else {
                for (int i = 0; i < needFromDBIds.size(); i++) {
                    String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + needFromDBTypes.get(i), needFromDBIds.get(i));
                    jedisClient.set(key, 0);
                    result.put(ids.get(i), 0);
                }
            }
        }
        return result;
    }

    /**
     * 根据id获取评论
     * 
     * @param commentId
     * @return
     */
    private Comment findById(Long commentId) {
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_DETAIL, commentId);
        Comment comment = memcached.get(key);
        if (comment == null) {
            comment = commentDAO.getById(commentId);
            if (comment != null) {
                memcached.set(key, comment, CacheConstants.TIME_HOUR * 4);
            }
        }
        return comment;
    }

    /**
     * 根据id列表获取评论
     * 
     * @param ids
     * @return
     */
    private List<Comment> findByIds(List<Long> ids) {
        return commentDAO.getByIds(ids);
    }

}
