package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import com.zhy.listen.bean.Music;
import com.zhy.listen.create.GenerateIndexService;
import com.zhy.listen.create.GenerateIndexServiceAdapter;

public class CreateMusicIndexServiceImpl extends GenerateIndexServiceAdapter implements GenerateIndexService<Music> {

    @Override
    public List<Document> create(List<Music> list) {
        List<Document> docs = new ArrayList<Document>();
        for (Music music : list) {
            Document document = new Document();
            document.add(new Field("id", music.getId().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("title", music.getTitle(), Store.YES, Index.ANALYZED));
            document.add(new Field("author", music.getAuthor(), Store.YES, Index.ANALYZED));
            document.add(new Field("url", music.getUrl(), Store.YES, Index.NO));
            document.add(new Field("lrc", music.getLrc(), Store.YES, Index.NO));
            document.add(new Field("is_upload", music.getIsUpload().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("is_index", music.getIsIndex().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("is_valid", music.getIsValid().toString(), Store.YES, Index.NOT_ANALYZED));
            docs.add(document);
        }
        return docs;
    }
}
