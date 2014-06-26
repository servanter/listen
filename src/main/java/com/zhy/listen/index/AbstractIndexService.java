package com.zhy.listen.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.common.Constant;
import com.zhy.listen.template.TemplateService;

/**
 * 索引相关父类
 * 
 * @author zhy19890221@gmail.com
 * 
 */
public class AbstractIndexService {

    @Autowired
    private TemplateService templateService;

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
