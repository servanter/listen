package com.zhy.listen.init;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Author;
import com.zhy.listen.service.AuthorService;
import com.zhy.listen.util.PinyinUtil;

public class TestInitMusicAuthor extends SuperTest {

    @Autowired
    private AuthorService authorService;
    
    @Test
    public void test() {
        Document document;
        try {
            document = Jsoup.parse(new File("D:\\music.txt"), "UTF-8");
            Elements elements = document.getElementsByTag("a");
            List<Author> authors = new ArrayList<Author>();
            for (Element e : elements) {
                if(e.text() == null || e.text().length() == 0){
                    continue;
                }
                Author author = new Author();
                String enName = PinyinUtil.getPinYin(e.text());
                String firstEnName = PinyinUtil.getPinYin(new String(new char[]{e.text().charAt(0)})).toUpperCase();
                if(firstEnName.length() > 1) {
                    firstEnName = firstEnName.substring(0, 1);
                }
                author.setName(e.text());
                author.setEnName(enName);
                author.setFirstEnName(firstEnName);
                authors.add(author);
            }
            authorService.batchAdd(authors);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
