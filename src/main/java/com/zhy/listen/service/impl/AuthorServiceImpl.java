package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Author;
import com.zhy.listen.bean.Paging;
import com.zhy.listen.dao.AuthorDAO;
import com.zhy.listen.query.AbstractDBSearch;
import com.zhy.listen.service.AuthorService;

@Service("authorService")
public class AuthorServiceImpl extends AbstractDBSearch<Author> implements AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    @Override
    public Paging<Author> findAuthorsByConditions(Author author) {
        List<Author> result = getAuthorsByConditions(author);
        int total = getAuthorsByConditionsCount(author);
        return new Paging<Author>(total, author.getPage(), author.getPageSize(), result);
    }

    @Override
    public boolean add(Author author) {
        return authorDAO.save(author) > 0;
    }

    @Override
    public void batchAdd(List<Author> authors) {
        authorDAO.batchSave(authors);
    }
    
    public List<Author> getAuthorsByConditions(Author author){
        return authorDAO.getAuthorsByConditions(author);
    }
    
    public int getAuthorsByConditionsCount(Author author){
        return authorDAO.getAuthorsByConditionsCount(author);
    }

}
