package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.User;
import com.zhy.listen.create.GenerateIndexService;

@Service
public class CreateUserIndexServiceImpl implements GenerateIndexService<User> {

    @Override
    public List<SolrInputDocument> create(List<User> list) {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (User user : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", user.getId());
            document.addField("sex", user.getSex());
            document.addField("user_nick", user.getUserNick());
            document.addField("user_img", user.getUserImg());
            document.addField("introduction", user.getIntroduction());
            document.addField("province", user.getProvince());
            document.addField("city", user.getCity());
            document.addField("mobile", user.getMobile());
            document.addField("email", user.getEmail());
            document.addField("reg_time", user.getRegTime());
            document.addField("modify_time", user.getModifyTime());
            document.addField("is_valid", user.getIsValid());
            docs.add(document);
        }
        return docs;
    }

}
