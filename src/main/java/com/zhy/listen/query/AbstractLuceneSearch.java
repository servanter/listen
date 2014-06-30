package com.zhy.listen.query;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.bean.Paging;
import com.zhy.listen.bean.indexer.IndexerClass;
import com.zhy.listen.bean.query.QueryField;
import com.zhy.listen.bean.query.QueryResult;
import com.zhy.listen.common.Constant;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.util.StringUtil;

/**
 * 基于lucene查询
 * 
 * @author zhanghongyan
 * 
 */
public abstract class AbstractLuceneSearch<T> extends AbstractSearch<T> {

    @Autowired
    private TemplateService templateService;

    /**
     * 需要的字段
     */
    @Resource
    private Map<String, List<String>> requiredFields;
    
    /**
     * 获取indexreader<br>
     * 用indexreader读取路径
     * 
     * @return
     * @throws IOException
     */
    protected IndexReader getIndexReader() throws IOException {
        try {
            return IndexReader.open(getIndexDirectory());
        } catch (IOException e) {
            throw new IOException("Error while opening index file", e);
        }
    }

    protected Directory getIndexDirectory() throws IOException {
        String path = templateService.getMessage(Constant.TEMPLATE_LUCENE_PATH_WORDS);
        return FSDirectory.open(new File(path));
    }
    
    /**
     * 缓存字段<br>
     * TODO 后期不用,此缓存跟requiredFields相同,用一个
     */
    private static Map<Class<?>, Field[]> cacheKey = new HashMap<Class<?>, Field[]>();

    protected QueryResult query(QueryResult queryResult) {
        try {
            IndexSearcher searcher = new IndexSearcher(getIndexReader());
            TopDocs topDocs = searcher.search(createQuery(queryResult), queryResult.getCount());
            ScoreDoc[] docs = topDocs.scoreDocs;
            queryResult.setHitCount(docs.length);
            List<Paging> result = new ArrayList<Paging>();
            Class clazz = Class.forName("com.zhy.listen.bean." + queryResult.getIndexerClass().getAlias());
            for (ScoreDoc doc : docs) {
                Paging p = getBean(searcher.doc(doc.doc), clazz);
                result.add(p);
            }
            queryResult.setResult(result);
            String message = "";
            if (docs.length == 0) {
                message = templateService.getMessage(Constant.TEMPLATE_LUCENE_SEARCH_NORESULT_WORDS);
            } else {
                message = templateService.getMessage(Constant.TEMPLATE_LUCENE_SEARCH_HASRESULT_WORDS, String.valueOf(docs.length));
            }

            // 设置结果
            queryResult.setMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        return queryResult;
    }

    /**
     * 创建查询query 所有字段
     * 
     * @param queryResult
     * @return
     * @throws IllegalArgumentException
     */
    private Query createQuery(QueryResult queryResult) throws IllegalArgumentException {
        System.out.println(StringUtil.signUpChar(0, queryResult.getIndexerClass().name()));
        if (requiredFields.get(queryResult.getIndexerClass().name().toLowerCase()) == null) {
            throw new IllegalArgumentException("Can't find the class name.");
        }
        List<String> fields = requiredFields.get(queryResult.getIndexerClass().name().toLowerCase());
        BooleanQuery booleanQuery = new BooleanQuery();
        for (QueryField queryField : queryResult.getQueryFields()) {

            // 验证是否跟期待是一样 requiredFields在配置文件中配置,严防恶意访问
            if (fields.contains(queryField.getFieldName())) {
                addTermQuery(queryField, booleanQuery);
            }
        }
        return booleanQuery;
    }

    /**
     * 生成TermQuery并存放在BooleanQuery中,如果except以空格分开(多个)那么每一个都为一个TermQuery
     * 
     * @param queryField
     * @param booleanQuery
     */
    private void addTermQuery(QueryField queryField, BooleanQuery booleanQuery) {
        for (String value : queryField.getFieldExcept().split(" ")) {
            TermQuery query = new TermQuery(new Term(queryField.getFieldName(), value));
            booleanQuery.add(query, Occur.SHOULD);
        }
    }

    /**
     * doc 转 bean
     * 
     * @param doc
     * @param type
     * @return
     */
    private <T extends Paging> Paging getBean(Document doc, Class<T> type) {
        Field[] fields = getFields(type);
        try {

            // 实例化
            T bean = type.newInstance();
            for (Field field : fields) {
                // 设置每一个field
                String name = field.getName();
                String value = doc.get(name);
                if (value == null || value.trim().length() == 0) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                    field.set(bean, Long.valueOf(value));
                    continue;
                }
                if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    field.set(bean, Double.valueOf(value));
                    continue;
                }
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.set(bean, Integer.valueOf(value));
                    continue;
                }
                if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    field.set(bean, Boolean.valueOf(value));
                    continue;
                }
                if (fieldType.equals(Timestamp.class)) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = df.parse(value);
                    field.set(bean, new Timestamp(date.getTime()));
                    continue;
                }
                field.set(bean, value);
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating bean from ducument.", e);
        }
    }

    private Field[] getFields(Class<?> type) {
        Field[] fields = cacheKey.get(type);
        if (fields == null) {
            fields = type.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            cacheKey.put(type, fields);
        }
        return fields;
    }
    
    protected QueryResult generateQueryResult(T t, IndexerClass indexerClass) {
        QueryResult queryResult = new QueryResult();
        queryResult.setIndexerClass(indexerClass);
        try {
            List<QueryField> queryFields = new ArrayList<QueryField>();
            List<String> fields = requiredFields.get(queryResult.getIndexerClass().name().toLowerCase());
            for (String f : fields) {
                String getMethodName = "get" + StringUtil.signUpChar(0, f);
                Object method = t.getClass().getMethod(getMethodName).invoke(t);
                if(method == null) {
                    continue;
                }
                String value = String.valueOf(method.toString());
                QueryField field = new QueryField(f, value);
                queryFields.add(field);
            }
            queryResult.setQueryFields(queryFields);
            
            // 获取当前查询的条数
            queryResult.setCount(Integer.parseInt(t.getClass().getSuperclass().getDeclaredMethod("getPageSize").invoke(t).toString()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return queryResult;
    }
}
