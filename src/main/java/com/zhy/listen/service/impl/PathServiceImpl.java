package com.zhy.listen.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.ErrorCode;
import com.zhy.listen.bean.Response;
import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.service.PathService;
import com.zhy.listen.solr.SolrService;

@Service
public class PathServiceImpl implements PathService {

    @Autowired
    private SolrService solrService;

    @Override
    public boolean sign(UserStatusPointPath path) {
        QueryResult queryResult = new QueryResult();
        QueryField field = new QueryField("id", String.valueOf(path.getId()));
        List<QueryField> fields = new ArrayList<QueryField>();
        fields.add(field);
        queryResult.setIndexerClass(IndexerClass.USER);
        queryResult.setQueryFields(fields);
        solrService.query(queryResult);
        if (queryResult.getHitCount() > 0) {
            List<UserStatusPointPath> upa = (List<UserStatusPointPath>) queryResult.getResult();
            UserStatusPointPath p = upa.get(0);
            p.setLoc(path.getLoc());
            p.setDiscoveryProvince(path.getDiscoveryProvince());
            p.setDiscoveryCity(path.getDiscoveryCity());
            p.setDiscoveryTime(new Timestamp(System.currentTimeMillis()));
            p.setIsClean(false);
            Indexer indexer = new Indexer();
            indexer.setIndexerClass(IndexerClass.USER);
            List<UserStatusPointPath> needIndexList = new ArrayList<UserStatusPointPath>();
            needIndexList.add(p);
            indexer.setNeedIndexList(needIndexList);
            return solrService.create(indexer);
        }
        return false;
    }

    @Override
    public QueryResult queryByPath(UserStatusPointPath path, Integer mile) {
        QueryResult queryResult = new QueryResult();
        List<QueryField> fields = new ArrayList<QueryField>();
        QueryField field = new QueryField("pt", path.getLoc());
        QueryField field2 = new QueryField("mileage", String.valueOf(mile));
        QueryField field3 = new QueryField("discoveryProvince", path.getDiscoveryProvince());
        QueryField field4 = new QueryField("discoveryCity", path.getDiscoveryCity());
        QueryField field5 = new QueryField("id", String.valueOf(path.getId())); 
        fields.add(field);
        fields.add(field2);
        fields.add(field3);
        fields.add(field4);
        fields.add(field5);
        queryResult.setIndexerClass(IndexerClass.USER);
        queryResult.setQueryFields(fields);
        solrService.queryPath(queryResult);

        // 更新该用户地理信息
        sign(path);
        return queryResult;
    }

    @Override
    public Response pathSetting(UserStatusPointPath path) {
        boolean isSuccess = false;
        QueryResult queryResult = new QueryResult();
        QueryField field = new QueryField("id", String.valueOf(path.getId()));
        List<QueryField> fields = new ArrayList<QueryField>();
        fields.add(field);
        queryResult.setIndexerClass(IndexerClass.USER);
        queryResult.setQueryFields(fields);
        solrService.query(queryResult);
        if (queryResult.getHitCount() > 0) {
            List<UserStatusPointPath> upa = (List<UserStatusPointPath>) queryResult.getResult();
            UserStatusPointPath p = upa.get(0);
            p.setIsClean(path.getIsClean());
            Indexer indexer = new Indexer();
            indexer.setIndexerClass(IndexerClass.USER);
            List<UserStatusPointPath> needIndexList = new ArrayList<UserStatusPointPath>();
            needIndexList.add(p);
            indexer.setNeedIndexList(needIndexList);
            isSuccess = solrService.create(indexer);
        }
        Response response = new Response();
        response.setErrorCode(isSuccess ? ErrorCode.SUCCESS : ErrorCode.ERROR);
        return response;
    }

}
