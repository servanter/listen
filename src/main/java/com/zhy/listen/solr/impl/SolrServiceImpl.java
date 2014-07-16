package com.zhy.listen.solr.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.directwebremoting.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Page;
import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.common.Constant;
import com.zhy.listen.common.SolrConstant;
import com.zhy.listen.create.GenerateIndexServiceAdapter;
import com.zhy.listen.solr.SolrService;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.util.FieldUtils;

@Service
public class SolrServiceImpl implements SolrService {

    public static Logger logger = Logger.getLogger(SolrServiceImpl.class);
    
    @Autowired
    private GenerateIndexServiceAdapter generateIndexServiceAdapter;
    
    @Autowired
    private TemplateService templateService;
    
    @Resource
    private Map<String, List<String>> solrCreateFields;
    
    @Resource
    private Map<String, List<String>> solrQueryFields;
    
    /**
     * 缓存字段<br>
     * TODO 后期不用,此缓存跟requiredFields相同,用一个
     */
    private static Map<Class<?>, Field[]> cacheKey = new HashMap<Class<?>, Field[]>();
    
    @Override
    public boolean create(Indexer indexer) {
        HttpSolrServer server = getConnection(templateService.getMessage(Constant.SOLR_URL));
        try {
            List<SolrInputDocument> documents = generateIndexServiceAdapter.getCreateIndexImpl(indexer.getIndexerClass()).create(indexer.getNeedIndexList());
            server.add(documents);
            server.commit();
        } catch (Exception e){
            e.printStackTrace();
            logger.error("[Solr]: Create index occur error.");
            return false;
        }
        return true;
    }

    @Override
    public QueryResult query(QueryResult queryResult) {
        HttpSolrServer server = getConnection(templateService.getMessage(Constant.SOLR_URL));
        SolrQuery query = new SolrQuery();
        query.setQuery(SolrConstant.ALL_IN_ONE + SolrConstant.FIELD_SPLIT + queryResult.getKeywords());
        if(queryResult.getQueryFields() != null) {
            for(QueryField field : queryResult.getQueryFields()) {
                query.setQuery(field.getFieldName() + SolrConstant.FIELD_SPLIT + field.getValue());
            }
        }
        int currentPage = 1;
        if(queryResult.getPage() >= 1) {
            currentPage = queryResult.getPage() - 1;
        }
        query.setStart(currentPage * queryResult.getPageSize());
        query.setRows(queryResult.getPageSize());
        try {
            SolrDocumentList solrDocumentList = server.query(query).getResults();
            Class clazz = Class.forName(Constant.BEAN_PACKAGE_PATH + queryResult.getIndexerClass().getAlias());
            List<Page> pageList = new ArrayList<Page>(); 
            for(SolrDocument doc : solrDocumentList) {
                Page p = packageDoc(doc, clazz);
                if(p != null) {
                    pageList.add(p);
                }
            }
            queryResult.setResult(pageList);
            queryResult.setHitCount(pageList.size());
            queryResult.setTotalRecord(solrDocumentList.getNumFound());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return queryResult;
    }

    /**
     * 获取solr connection
     * @param url
     * @return
     */
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
    
    /**
     * 封装每个类
     * 
     * @param solrDocumentList
     */
    private <X extends Page> Page packageDoc(SolrDocument solrDocument, Class<X> t) {
        X bean = null;
        try {
            bean = t.newInstance();
            List<String> fieldList = solrCreateFields.get(t.getSimpleName().toLowerCase());
            if(fieldList == null) {
                return null;
            }
            Field[] fields = cacheKey.get(t);
            
            // setfileds
            if(fields == null || fields.length == 0) {
                fields = new Field[fieldList.size()];
                for (int i = 0; i < fieldList.size(); i++) {
                    String f = fieldList.get(i);
                    try {
                        fields[i] = t.getDeclaredField(f);
                        fields[i].setAccessible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("[Solr]: Get fields occur error, The " + f + " has not property");
                    }
                }
                cacheKey.put(t, fields);
            }
            for (Field field : fields) {

                // 设置每一个field
                String docField = FieldUtils.getDocField(field);
                Object value = solrDocument.get(docField);
                if (value == null) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                    field.set(bean, Long.valueOf(value.toString()));
                    continue;
                }
                if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    field.set(bean, Double.valueOf(value.toString()));
                    continue;
                }
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.set(bean, Integer.valueOf(value.toString()));
                    continue;
                }
                if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    field.set(bean, Boolean.valueOf(value.toString()));
                    continue;
                }
                if (fieldType.equals(Timestamp.class)) {
                    Date date = (Date)value;
                    field.set(bean, new Timestamp(date.getTime()));
                    continue;
                }
                field.set(bean, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[Solr] Convert bean occur error");
        }
        return bean;
    }

}
