package com.zhy.listen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Author;
import com.zhy.listen.dao.AuthorDAO;
import com.zhy.listen.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorDAO authorDAO;
    
    @Override
    public List<Author> getAuthorsByConditions(Author author) {
        return authorDAO.getAuthorsByConditions(author);
    }

}
