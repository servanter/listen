package com.zhy.listen.create.impl;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.indexer.Indexer;
import com.zhy.listen.common.Constant;
import com.zhy.listen.create.CreateService;
import com.zhy.listen.create.GenerateIndexServiceAdapter;
import com.zhy.listen.template.TemplateService;

@Service
public class CreateServiceImpl implements CreateService {

    @Autowired
    private GenerateIndexServiceAdapter generateIndexServiceAdapter;

    @Autowired
    private TemplateService templateService;
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean found(Indexer indexer) {
        try {
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, new StandardAnalyzer(Version.LUCENE_36));
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            IndexWriter writer = new IndexWriter(getIndexDirectory(), config);
            writer.addDocuments(generateIndexServiceAdapter.getCreateIndexImpl(indexer.getIndexerClass()).create(
                    indexer.getNeedIndexList()));
            writer.commit(); 
            writer.forceMerge(3);
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
}
