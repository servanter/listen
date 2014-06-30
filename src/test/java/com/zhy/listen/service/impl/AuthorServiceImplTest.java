package com.zhy.listen.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhy.listen.SuperTest;
import com.zhy.listen.bean.Author;
import com.zhy.listen.service.AuthorService;

public class AuthorServiceImplTest extends SuperTest{

    @Autowired
    private AuthorService authorService;
    
    @Test
    public void testGetAuthorsByConditions() {
        Author author = new Author();
        author.setEnName("zhoujielun");
        author.setName("周杰伦");
        author.setFirstEnName("Z");
        author.setIsValid(true);
        List<Author> authors = authorService.getAuthorsByConditions(author);
        Assert.assertTrue("Can't find the author.", authors != null && authors.size() > 0);
    }

}
