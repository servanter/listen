package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.zhy.listen.create.GenerateIndexService;
import com.zhy.listen.create.GenerateIndexServiceAdapter;
import com.zhy.listen.entities.Music;
import com.zhy.listen.entities.Path;

/**
 * 地理信息索引创建
 * 
 * @author zhanghongyan@outlook.com
 *
 */
@Service
public class CreatePathIndexServiceImpl implements GenerateIndexService<Path> {

    @Override
    public List<SolrInputDocument> create(List<Path> list) {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (Path path : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", path.getId());
            document.addField("user_id", path.getUserId());
            document.addField("loc", path.getLoc());
            document.addField("province", path.getProvince());
            document.addField("city", path.getCity());
            document.addField("is_clean", path.getIsClean());
            document.addField("discovery_time", path.getDiscoveryTime());
            docs.add(document);
        }
        return docs;
    }
}
