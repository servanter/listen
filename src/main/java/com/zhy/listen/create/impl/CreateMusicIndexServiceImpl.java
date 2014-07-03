package com.zhy.listen.create.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.create.GenerateIndexService;
import com.zhy.listen.create.GenerateIndexServiceAdapter;

@Service
public class CreateMusicIndexServiceImpl extends GenerateIndexServiceAdapter implements GenerateIndexService<Music> {

    @Override
    public List<Document> create(List<Music> list) {
        List<Document> docs = new ArrayList<Document>();
        for (Music music : list) {
            Document document = new Document();
            document.add(new Field("id", music.getId().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("title", music.getTitle(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("author", music.getAuthor(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("title_author", music.getAuthor(), Store.YES, Index.ANALYZED));
            document.add(new Field("url", music.getUrl(), Store.YES, Index.NO));
            document.add(new Field("lrc", music.getLrc(), Store.YES, Index.NO));
            document.add(new Field("isUpload", music.getIsUpload().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("isIndex", music.getIsIndex().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("isValid", music.getIsValid().toString(), Store.YES, Index.NOT_ANALYZED));
            document.add(new Field("createTime", music.getCreateTime().toString(), Store.YES, Index.NOT_ANALYZED));
            docs.add(document);
        }
        return docs;
    }
}
