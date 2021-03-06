package com.zhy.listen.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhy.listen.entities.Author;

@Repository
public interface AuthorDAO {

    public List<Author> getAuthorsByConditions(Author author);

    public int getAuthorsByConditionsCount(Author author);
    
    public int save(Author author);

    public void batchSave(List<Author> authors);

}
