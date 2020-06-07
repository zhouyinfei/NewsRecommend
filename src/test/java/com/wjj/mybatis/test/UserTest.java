package com.wjj.mybatis.test;
 
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wjj.entity.User;

 
public class UserTest {
 
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		/* 得到 SqlSession 对象*/
        SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");
 
		String statement = null;
		
//		//添加
//		statement = "com.wjj.dao.UserDao.addUser";
//		int insert = sqlSession.insert(statement, new User(1, "yinfei", "123456", null, null, 28));
//		System.out.println(insert);
		
//		//删除
//		statement = "com.wjj.dao.UserDao.deleteUser";
//		int delete = sqlSession.delete(statement, 1);
//		System.out.println(delete);
		
//		//查询
//		statement = "com.wjj.dao.UserDao.getUserById";
//		User user = sqlSession.selectOne(statement, 1);
//		System.out.println(user);
//		
//		statement = "com.wjj.dao.UserDao.getUserByName";
//		user = sqlSession.selectOne(statement, "yinfei");
//		System.out.println(user);
//		
//		//修改
//		statement = "com.wjj.dao.UserDao.updateUser";
//		int update = sqlSession.update(statement, new User(1, "infi","456","13015929018",null, 30));
//		System.out.println(update);
		
//		//查询所有
//		statement = "com.wjj.dao.UserDao.getAllUsers";
//		List<User> list = sqlSession.selectList(statement);
//		System.out.println(list);
	}
}
