package com.zhy.listen.solr.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.create.GenerateIndexServiceAdapter;
import com.zhy.listen.solr.SolrService;

@Service
public class SolrServiceImpl implements SolrService {

    @Autowired
    private GenerateIndexServiceAdapter generateIndexServiceAdapter;
    
    @Override
    public boolean create(Indexer indexer) {
        HttpSolrServer server = getConnection("http://localhost:8090/solr/listen_music");
        try {
            List<SolrInputDocument> documents = generateIndexServiceAdapter.getCreateIndexImpl(indexer.getIndexerClass()).create(indexer.getNeedIndexList());
            server.add(documents);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public QueryResult query(QueryResult queryResult) {
        HttpSolrServer server = getConnection("http://localhost:8090/solr/listen_music");
        SolrQuery query = new SolrQuery();
        query.setQuery("all_in_one:" + queryResult.getKeywords());
        if(queryResult.getQueryFields() != null) {
            for(QueryField field : queryResult.getQueryFields()) {
                query.setQuery(field.getFieldName() + ":" + field.getFieldExcept());
            }
        }
        query.setStart((queryResult.getPage() - 1 ) * queryResult.getCount());
        query.setRows(queryResult.getCount());
        try {
            SolrDocumentList solrDocumentList = server.query(query).getResults();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpSolrServer getConnection(String url) {
        HttpSolrServer server = new HttpSolrServer(url);
        server.setSoTimeout(10000);
        server.setConnectionTimeout(1000);
        server.setDefaultMaxConnectionsPerHost(100);
        server.setMaxTotalConnections(100);
        server.setFollowRedirects(false);
        server.setAllowCompression(true);
        server.setMaxRetries(1);
        return server;
    }
}
