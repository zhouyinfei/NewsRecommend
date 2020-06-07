package com.wjj.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjj.entity.User;
import com.wjj.entity.UserToken;

@Controller
public class UserControllerr {
	
	@Autowired
	SqlSession sqlSession;

	
	//####################用户注册#######################
    @RequestMapping(value="/userRegist", produces="application/json; charset=utf-8")
    @ResponseBody
    public String userRegist(String username, String password, String level, HttpServletResponse response){   
    	//参数格式错误
    	if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(level)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	String statement = null;
    	
    	//查找用户名是否存在
		statement = "com.wjj.dao.UserDao.getUserByName";
		User user = sqlSession.selectOne(statement, username);
		System.out.println("user=" + user);
		
		//用户已存在
		if (user != null) {
			System.out.println("user is already existed");
			return "{\"retcode\": \"301\"}";
		}
		
		//如果是注册管理员，则只能存在一个管理员
		if (Integer.parseInt(level) == 1) {
			statement = "com.wjj.dao.UserDao.getUserByLevel";
			user = sqlSession.selectOne(statement, 1);
			if (user != null) {
				System.out.println("manager is already existed");
				return "{\"retcode\": \"302\"}";
			}
		}
    	
		statement = "com.wjj.dao.UserDao.addUser";
    	sqlSession.insert(statement, new User(0, username, password, null, null, Integer.parseInt(level), null, null));
    	
    	try {
			response.sendRedirect("./index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "{\"retcode\": \"200\"}";
    }
    
    
    //####################用户登录#######################
	@RequestMapping(value="/userLogin", produces="application/json; charset=utf-8")
    @ResponseBody
    public String userLogin(String username, String password, String level, HttpServletResponse response){
		//参数格式错误
    	if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(level)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	
    	String statement = null;
    	
    	//查找用户名是否存在
		statement = "com.wjj.dao.UserDao.getUserByName";
		User user = sqlSession.selectOne(statement, username);
		System.out.println("user=" + user);
		
		//用户不存在
		if (user == null) {
			System.out.println("user is not existed");
			return "{\"retcode\": \"303\"}";
		}
		
		//密码错误
		if (!password.equals(user.getPassword())) {
			System.out.println("password incorrect");
			return "{\"retcode\": \"306\"}";
		}
		
		//用户权限错误
		if (user.getLevel() != Integer.parseInt(level)) {
			System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}
		
		statement = "com.wjj.dao.UserTokenDao.getUserTokenById";
		UserToken userToken = sqlSession.selectOne(statement, user.getId());
		if (userToken == null) {		//token不存在，则插入token
			statement = "com.wjj.dao.UserTokenDao.addUserToken";
			userToken = new UserToken();
			userToken.setId(user.getId());
			userToken.setToken(RandomStringUtils.randomAlphanumeric(10));
			userToken.setCreate_time(new Date());
			userToken.setLevel(Integer.parseInt(level));
			sqlSession.insert(statement, userToken);
		} else {						//否则更新token的值，和过期时间
			statement = "com.wjj.dao.UserTokenDao.updateUserToken";
			userToken.setToken(RandomStringUtils.randomAlphanumeric(10));
			userToken.setCreate_time(new Date());
			sqlSession.update(statement, userToken);
		}
    	try {
			response.sendRedirect("./newslist.html?t="+userToken.getToken() + "&username=" + username);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "{\"retcode\": \"200\", \"token\": \"" + userToken.getToken() + "\"}";
    }
	
	//####################修改用户信息#######################
	/**
	 * 
	 * @param token				修改人的token
	 * @param level1			修改人的权限：1、管理员， 2、普通用户
	 * @param username			被修改人的用户名
	 * @param level2			被修改人的权限：1、管理员， 2、普通用户
	 * @param password			修改后的密码
	 * @param phonenum			修改后的手机号
	 * @param email				修改后的邮箱
	 * @return
	 */
	@RequestMapping(value="/userModify", produces="application/json; charset=utf-8")
    @ResponseBody
    public String userModify(String token, String level1, String username, String level2,
    		String password, String phonenum, String email){
		//参数格式错误
    	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(level1) || StringUtils.isEmpty(level2)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	
    	int modifyLevel = Integer.parseInt(level1);		//修改人的权限
    	int beModifyLevel = Integer.parseInt(level2);		//被修改人的权限
    	
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
		
		//token不存在
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		User user;
		//如果是二级用户，则不需要传username，修改的用户一定是自己
		if (modifyLevel == 2 && beModifyLevel == 2) {
			statement = "com.wjj.dao.UserDao.getUserById";
			user = sqlSession.selectOne(statement, userToken.getId());
			System.out.println("user=" + user);
		} else {
			//查找被修改的用户是否存在
			statement = "com.wjj.dao.UserDao.getUserByName";
			user = sqlSession.selectOne(statement, username);
			System.out.println("user=" + user);
		}
		
		//被修改的用户不存在
		if (user == null) {
			System.out.println("user is not existed");
			return "{\"retcode\": \"303\"}";
		}
		
		//用户权限错误
    	if ((modifyLevel == 2 && beModifyLevel == 1) || 			//普通用户修改管理员，权限错误
    			userToken.getLevel() != modifyLevel || user.getLevel() != beModifyLevel) {	//权限不一致
    		System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}

    	if (modifyLevel == 2 && userToken.getId() != user.getId()) {			//普通用户不能修改其他普通用户信息
    		System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}
		
    	if (!StringUtils.isEmpty(password)) {
			user.setPassword(password);
		}
    	if (!StringUtils.isEmpty(phonenum)) {
			user.setPhonenum(phonenum);
		}
    	if (!StringUtils.isEmpty(email)) {
			user.setEmail(email);
		}
		statement = "com.wjj.dao.UserDao.updateUser";
		sqlSession.update(statement, user);
    	
    	return "{\"retcode\": \"200\"}";
    }
	
		//####################删除用户信息#######################
		/**
		 * 只能管理员删除普通用户
		 * @param token			管理员token
		 * @param username		普通用户用户名
		 * @return
		 */
		@RequestMapping(value="/userDelete", produces="application/json; charset=utf-8")
	    @ResponseBody
	    public String userDelete(String token, String username){
		//参数格式错误
    	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(username)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
		
		//token不存在
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//查找被修改的用户是否存在
		statement = "com.wjj.dao.UserDao.getUserByName";
		User user = sqlSession.selectOne(statement, username);
		System.out.println("user=" + user);
		
		//被修改的用户不存在
		if (user == null) {
			System.out.println("user is not existed");
			return "{\"retcode\": \"303\"}";
		}
		
		//用户权限错误
    	if (userToken.getLevel() !=1 || user.getLevel() != 2) {					//只能管理员删除普通用户
    		System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}

		statement = "com.wjj.dao.UserDao.deleteUser";
		sqlSession.delete(statement, user.getId());
    	
    	return "{\"retcode\": \"200\"}";
    }
    
		//####################获取用户信息(管理员、普通用户)#######################
		/**
		 * @param token			用户token
		 * @return
		 */
		@RequestMapping(value="/userGetInfo", produces="application/json; charset=utf-8")
	    @ResponseBody
	    public String userGetInfo(String token){
		//参数格式错误
    	if (StringUtils.isEmpty(token)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
		
		//token不存在
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}

		//查找用户
		statement = "com.wjj.dao.UserDao.getUserById";
		User user = sqlSession.selectOne(statement, userToken.getId());
		System.out.println("user=" + user);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("retcode", "200");
		jsonObject.put("username", user.getUsername());
		jsonObject.put("password", user.getPassword());
		jsonObject.put("phonenum", user.getPhonenum());
		jsonObject.put("email", user.getEmail());
    	return jsonObject.toString();
    }
		
		//####################管理员获取普通用户信息(管理员)#######################
		/**
		 * @param token			用户token
		 * @return
		 */
		@RequestMapping(value="/userGetInfoByAdmin", produces="application/json; charset=utf-8")
	    @ResponseBody
	    public String userGetInfoByAdmin(String token, String username){
		//参数格式错误
    	if (StringUtils.isEmpty(token)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
		
		//token不存在
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
    	if (userToken.getLevel() !=1) {					//只能管理员删除普通用户
    		System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}

		//查找用户
		statement = "com.wjj.dao.UserDao.getUserByName";
		User user = sqlSession.selectOne(statement, username);
		System.out.println("user=" + user);
		
		//用户不存在
		if (user == null) {
			System.out.println("user is not existed");
			return "{\"retcode\": \"303\"}";
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("retcode", "200");
		jsonObject.put("username", user.getUsername());
		jsonObject.put("password", user.getPassword());
		jsonObject.put("phonenum", user.getPhonenum());
		jsonObject.put("email", user.getEmail());
    	return jsonObject.toString();
    }
		
		//####################获取用户信息列表(管理员)#######################
		/**
		 * @param token			用户token
		 * @return
		 */
		@RequestMapping(value="/userGetInfoList", produces="application/json; charset=utf-8")
	    @ResponseBody
	    public String userGetInfoList(String token){
		//参数格式错误
    	if (StringUtils.isEmpty(token)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
		
		//token不存在
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
    	if (userToken.getLevel() !=1) {					//只能管理员删除普通用户
    		System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}

		//查找用户列表
		statement = "com.wjj.dao.UserDao.getAllUsers";
		List<User> list = sqlSession.selectList(statement);
		System.out.println("list=" + list);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("retcode", "200");
		jsonObject.put("userList", list);

    	return jsonObject.toString();
    }
 
}