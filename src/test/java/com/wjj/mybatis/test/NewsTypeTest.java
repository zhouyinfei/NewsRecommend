package com.wjj.mybatis.test;
 
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wjj.entity.News;
import com.wjj.entity.NewsType;
import com.wjj.entity.User;
import com.wjj.entity.UserToken;

 
public class NewsTypeTest {
 
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		/* 得到 SqlSession 对象*/
        SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");
 
		String statement = null;
		
		//添加
//		statement = "com.wjj.dao.NewsTypeDao.addNewsType";
//		int insert = sqlSession.insert(statement, new NewsType(0, "军事新闻"));
//		System.out.println(insert);
//		
//		//删除
//		statement = "com.wjj.dao.NewsTypeDao.deleteNewsType";
//		int delete = sqlSession.delete(statement, 1);
//		System.out.println(delete);
		
		//查询
//		statement = "com.wjj.dao.NewsTypeDao.getNewsTypeById";
//		NewsType newsType = sqlSession.selectOne(statement, 5);
//		System.out.println(newsType);
//		
		statement = "com.wjj.dao.NewsTypeDao.getNewsTypeByName";
		NewsType newsType = sqlSession.selectOne(statement, "八卦a新闻");
		System.out.println(newsType);
		
//		statement = "com.wjj.dao.NewsTypeDao.getAllNewsType";
//		List<NewsType> newsT = sqlSession.selectList(statement);
//		System.out.println(newsT);
		
		
		
//		//修改
//		statement = "com.wjj.dao.NewsTypeDao.updateNewsType";
//		int update = sqlSession.update(statement, new NewsType(2, "实事新闻"));
//		System.out.println(update);
		
	}
}
