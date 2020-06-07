package com.wjj.dao;

import java.util.List;

import com.wjj.entity.NewsType;

public interface NewsTypeDao {

    void addNewsType(NewsType newsType);
    
    void deleteNewsType(int id);
    
    void updateNewsType(NewsType newsType);
    
    NewsType getNewsTypeById(int id);
    
    NewsType getNewsTypeByName(String type);
    
    List<NewsType> getAllNewsType();
    
}