package com.wjj.mybatis.test;
 
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wjj.entity.User;
import com.wjj.entity.UserToken;

 
public class UserTokenTest {
 
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		/* 得到 SqlSession 对象*/
        SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");
 
		String statement = null;
		
//		//添加
//		statement = "com.wjj.dao.UserTokenDao.addUserToken";
//		int insert = sqlSession.insert(statement, new UserToken(1, "abc", new Date(), 1));
//		System.out.println(insert);
		
//		//删除
//		statement = "com.wjj.dao.UserTokenDao.deleteUserToken";
//		int delete = sqlSession.delete(statement, 1);
//		System.out.println(delete);
		
//		//查询
//		statement = "com.wjj.dao.UserTokenDao.getUserTokenById";
//		UserToken userToken = sqlSession.selectOne(statement, 1);
//		System.out.println(userToken);
//		
//		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
//		userToken = sqlSession.selectOne(statement, "ABC");
//		System.out.println(userToken);
//		
//		//修改
//		statement = "com.wjj.dao.UserTokenDao.updateUserToken";
//		int update = sqlSession.update(statement, new UserToken(1, "ABC",new Date(),2));
//		System.out.println(update);
		
	}
}
