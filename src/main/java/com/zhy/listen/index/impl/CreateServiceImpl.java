package com.zhy.listen.index.impl;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.index.AbstractIndexService;
import com.zhy.listen.index.CreateService;
import com.zhy.listen.index.GenerateIndexServiceAdapter;

public class CreateServiceImpl extends AbstractIndexService implements CreateService {

    @Autowired
    private GenerateIndexServiceAdapter generateIndexServiceAdapter;

    @Override
    public boolean found(Indexer indexer) {

        try {
            IndexWriter writer = new IndexWriter(getIndexDirectory(), new IndexWriterConfig(Version.LUCENE_36,
                    new StandardAnalyzer(Version.LUCENE_36)));
            writer.addDocuments(generateIndexServiceAdapter.getCreateIndexImpl(indexer.getIndexerClass()).create(
                    indexer.getNeedIndexList()));
            writer.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
