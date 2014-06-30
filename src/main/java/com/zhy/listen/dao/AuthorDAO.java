package com.zhy.listen.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhy.listen.bean.Author;

@Repository
public interface AuthorDAO {

    public List<Author> getAuthorsByConditions(Author author);

}
