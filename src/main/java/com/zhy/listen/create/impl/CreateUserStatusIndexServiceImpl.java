package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.UserStatus;
import com.zhy.listen.create.GenerateIndexService;
import com.zhy.listen.entities.User;

@Service
public class CreateUserStatusIndexServiceImpl implements GenerateIndexService<UserStatus> {

    @Override
    public List<SolrInputDocument> create(List<UserStatus> list) {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (UserStatus userStatus : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", userStatus.getId());
            document.addField("sex", userStatus.getSex());
            document.addField("user_nick", userStatus.getUserNick());
            document.addField("user_img", userStatus.getUserImg());
            document.addField("introduction", userStatus.getIntroduction());
            document.addField("province", userStatus.getProvince());
            document.addField("city", userStatus.getCity());
            document.addField("mobile", userStatus.getMobile());
            document.addField("email", userStatus.getEmail());
            document.addField("reg_time", userStatus.getRegTime());
            document.addField("modify_time", userStatus.getModifyTime());
            document.addField("content", userStatus.getContent());
            document.addField("status_time", userStatus.getStatusTime());
            document.addField("is_valid", userStatus.getIsValid());
            docs.add(document);
        }
        return docs;
    }

}
