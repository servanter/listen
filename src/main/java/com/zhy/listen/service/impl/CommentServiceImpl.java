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
import com.zhy.listen.dao.CommentDAO;
import com.zhy.listen.entities.Comment;
import com.zhy.listen.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public boolean comment(Comment comment) {
        boolean isSuccess = commentDAO.save(comment) > 0;
        String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + comment.getCommentType().name(), comment.getDependId());
        jedisClient.jedis.incr(key);
        return isSuccess;
    }

    @Override
    public Paging<Comment> getCommentsByTypeAndDependId(Comment comment) {
        List<Comment> comments = commentDAO.getCommentsByTypeAndDependId(comment);
        int total = getCommentsByTypeAndDependIdCount(comment);
        return new Paging<Comment>(total, comment.getPage(), comment.getPageSize(), comments);
    }

    @Override
    public int getCommentsByTypeAndDependIdCount(Comment comment) {
        return commentDAO.getCommentsByTypeAndDependIdCount(comment);
    }

    @Override
    public boolean remove(Long commentId) {
        return commentDAO.delete(commentId) != 0 ? true : false;
    }

    @Override
    public Map<Long, Integer> findCommentsCountsByIds(List<SubType> types, List<Long> ids) {
        Map<Long, Integer> result = new HashMap<Long, Integer>();
        List<Long> needFromDBIds = new ArrayList<Long>();
        String[] keys = new String[ids.size()];
        for (int i = 0; i < keys.length; i++) {
            String keyPrefix = CacheConstants.CACHE_COMMENT_COUNT + types.get(i);
            String key = KeyGenerator.generateKey(keyPrefix, ids.get(i));
            String value = jedisClient.jedis.get(key);
            if (value == null) {
                needFromDBIds.add(ids.get(i));
            } else {
                result.put(ids.get(i), Integer.parseInt(value));
            }
        }
        if (result.size() != ids.size()) {
            List<CommentCount> commentCounts = commentDAO.getCommentsCountsByIds(types, ids);
            if (commentCounts != null && commentCounts.size() > 0) {
                for (CommentCount commentCount : commentCounts) {
                    String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + commentCount.getType().name(), commentCount.getDependId());
                    jedisClient.set(key, commentCount.getCount());
                    result.put(commentCount.getDependId(), commentCount.getCount());
                }
            } else {
                for (int i = 0; i < ids.size(); i++) {
                    String key = KeyGenerator.generateKey(CacheConstants.CACHE_COMMENT_COUNT + types.get(i), ids.get(i));
                    jedisClient.set(key, 0);
                    result.put(ids.get(i), 0);
                }
            }
        }
        return result;
    }
}
