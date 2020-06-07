package com.wjj.mybatis.test;
 
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wjj.entity.News;
import com.wjj.entity.User;
import com.wjj.entity.UserToken;

 
public class NewsTest {
 
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		/* 得到 SqlSession 对象*/
        SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");
 
		String statement = null;
		
		//添加
//		statement = "com.wjj.dao.NewsDao.addNews";
//		int insert = sqlSession.insert(statement, new News(0, "aa","aa","bb","cc","abcd","http://xxx"));
//		System.out.println(insert);
//		
//		//删除
//		statement = "com.wjj.dao.NewsDao.deleteNews";
//		int delete = sqlSession.delete(statement, 1);
//		System.out.println(delete);
		
		//查询
//		statement = "com.wjj.dao.NewsDao.getNewsById";
//		News news = sqlSession.selectOne(statement, 1);
//		System.out.println(news);
//		
//		statement = "com.wjj.dao.NewsDao.getNewsByType";
//		List<News> news = sqlSession.selectList(statement, "aa");
//		System.out.println(news);
//		
//		statement = "com.wjj.dao.NewsDao.getNewsByLabel";
//		news = sqlSession.selectList(statement, "bb");
//		System.out.println(news);
//		
//		statement = "com.wjj.dao.NewsDao.getNewsByKeyword";
//		news = sqlSession.selectList(statement, "cc");
//		System.out.println(news);
		
		
//		
//		//修改
//		statement = "com.wjj.dao.NewsDao.updateNews";
//		int update = sqlSession.update(statement, new News(1, "aa","aa","aa","aa","aa","aa"));
//		System.out.println(update);
		
	}
}
