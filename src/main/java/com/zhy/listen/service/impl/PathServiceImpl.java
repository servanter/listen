package com.zhy.listen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.entities.Path;
import com.zhy.listen.entities.User;
import com.zhy.listen.service.PathService;
import com.zhy.listen.service.UserService;
import com.zhy.listen.solr.SolrService;

@Service
public class PathServiceImpl implements PathService {

    @Autowired
    private SolrService solrService;

    @Autowired
    private UserService userService;

    @Override
    public boolean sign(Path path) {
        Indexer indexer = new Indexer();
        indexer.setIndexerClass(IndexerClass.PATH);
        List<Path> needIndexList = new ArrayList<Path>();
        needIndexList.add(path);
        indexer.setNeedIndexList(needIndexList);
        return solrService.create(indexer);
    }

    @Override
    public List<User> queryByPath(Path path, Integer mile) {
        List<User> result = new ArrayList<User>();
        QueryResult queryResult = new QueryResult();
        List<QueryField> fields = new ArrayList<QueryField>();
        QueryField field = new QueryField("pt", path.getLoc());
        QueryField field2 = new QueryField("mileage", String.valueOf(mile));
        QueryField field3 = new QueryField("province", path.getProvince());
        QueryField field4 = new QueryField("city", path.getCity());
        fields.add(field);
        fields.add(field2);
        fields.add(field3);
        fields.add(field4);
        queryResult.setIndexerClass(IndexerClass.PATH);
        queryResult.setQueryFields(fields);
        solrService.query(queryResult);
        StringBuilder builder = new StringBuilder();
        String rowString = "";
        if (queryResult.getHitCount() > 0) {
            List<Path> paths = (List<Path>) queryResult.getResult();
            for (Path p : paths) {
                builder.append("id:(" + p.getUserId() + ") AND ");
            }
            if(builder.length() > 0) {
                rowString = builder.substring(0, builder.length() - 4);
            }
        }
        QueryResult userQuery = new QueryResult();
        userQuery.setIndexerClass(IndexerClass.USER);
        fields.add(field3);
        fields.add(field4);
        userQuery.setRawQuery(rowString);
        solrService.query(userQuery);
        if(userQuery.getResult() != null && userQuery.getResult().size() > 0) {
            result = (List<User>) userQuery.getResult();
        }
        return result;
    }

}
