package com.wjj.dao;

import java.util.List;

import com.wjj.entity.News;

public interface NewsDao {

    void addNews(News news);
    
    void deleteNews(int id);
    
    void updateNews(News news);
    
    News getNewsById(int id);
    
    List<News> getNewsByType(String type);
    
    List<News> getNewsByLabel(String label);
    
    List<News> getNewsByKeyword(String keyword);
    
    List<News> getNewsByTitleKeyword(String keyword);
    
}