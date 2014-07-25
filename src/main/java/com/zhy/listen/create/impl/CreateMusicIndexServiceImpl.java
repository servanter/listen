package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.zhy.listen.create.GenerateIndexService;
import com.zhy.listen.create.GenerateIndexServiceAdapter;
import com.zhy.listen.entities.Music;

@Service
public class CreateMusicIndexServiceImpl implements GenerateIndexService<Music> {

    @Override
    public List<SolrInputDocument> create(List<Music> list) {
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (Music music : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", music.getId());
            document.addField("title", music.getTitle());
            document.addField("author", music.getAuthor());
            document.addField("url", music.getUrl());
            document.addField("lrc", music.getLrc());
            document.addField("is_upload", music.getIsUpload());
            document.addField("is_index", music.getIsIndex());
            document.addField("is_valid", music.getIsValid());
            document.addField("create_time", music.getCreateTime());
            docs.add(document);
        }
        return docs;
    }
}
