package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.UserStatusPointPath;
import com.zhy.listen.create.GenerateIndexService;

@Service
public class CreateUserStatusPointPathIndexServiceImpl implements GenerateIndexService<UserStatusPointPath> {

    @Override
    public List<SolrInputDocument> create(List<UserStatusPointPath> list) {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (UserStatusPointPath u : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", u.getId());
            document.addField("sex", u.getSex());
            document.addField("user_nick", u.getUserNick());
            document.addField("user_img", u.getUserImg());
            document.addField("introduction", u.getIntroduction());
            document.addField("province", u.getProvince());
            document.addField("city", u.getCity());
            document.addField("mobile", u.getMobile());
            document.addField("email", u.getEmail());
            document.addField("reg_time", u.getRegTime());
            document.addField("modify_time", u.getModifyTime());
            document.addField("content", u.getContent());
            document.addField("status_time", u.getStatusTime());
            document.addField("point", u.getPoint());
            document.addField("honour", u.getHonour());
            document.addField("loc", u.getLoc());
            document.addField("discovery_province", u.getDiscoveryProvince());
            document.addField("discovery_city", u.getDiscoveryCity());
            document.addField("discovery_time", u.getDiscoveryTime());
            document.addField("is_clean", u.getIsClean());
            docs.add(document);
        }
        return docs;
    }

}
