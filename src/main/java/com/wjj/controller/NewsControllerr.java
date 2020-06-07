package com.wjj.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjj.entity.News;
import com.wjj.entity.NewsType;
import com.wjj.entity.User;
import com.wjj.entity.UserToken;
import com.wjj.util.KeyWord;

@Controller
public class NewsControllerr {
	
	@Autowired
	SqlSession sqlSession;

	
	//####################添加新闻类别(管理员)#######################
	/**
	 * 
	 * @param token		管理员token
	 * @param type		新闻类别
	 * @return
	 */
    @RequestMapping(value="/newsTypeAdd", produces="application/json; charset=utf-8")
    @ResponseBody
    public String newsTypeAdd(String token, String type){   
    	//参数格式错误
    	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(type)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
    	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
		if (userToken.getLevel() != 1) {
			System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}
		
		try {
			type=URLDecoder.decode(type,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//新闻类别已存在
		statement = "com.wjj.dao.NewsTypeDao.getNewsTypeByName";
		NewsType newsType = sqlSession.selectOne(statement, type);
		System.out.println("newsType=" + newsType);
		if (newsType != null) {
			System.out.println("news type is already existed");
			return "{\"retcode\": \"401\"}";
		}
		
		statement = "com.wjj.dao.NewsTypeDao.addNewsType";
    	sqlSession.insert(statement, new NewsType(0, type));
        return "{\"retcode\": \"200\"}";
    }
    
    //####################获取新闻类别列表#######################
  	/**
  	 * 
  	 * @param token		token
  	 * @return
  	 */
      @RequestMapping(value="/newsTypeGetList", produces="application/json; charset=utf-8")
      @ResponseBody
      public String newsTypeGetList(String token){   
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
      	
  		//无效的token
  		if (userToken == null) {
  			System.out.println("invalid token");
  			return "{\"retcode\": \"307\"}";
  		}
  		
  		statement = "com.wjj.dao.NewsTypeDao.getAllNewsType";
      	List<NewsType> list = sqlSession.selectList(statement);
          
  	  	JSONObject ret = new JSONObject();
  	  	ret.put("retcode", "200");
  	  	ret.put("newsTypeList", list);
  	    return ret.toJSONString();
      }
    
    //####################添加新闻(管理员)#######################
  	/**
  	 * 
  	 * @param token		管理员token
  	 * @param type		新闻类别
  	 * @return
  	 */
      @RequestMapping(value="/newsAdd", method=RequestMethod.POST, produces="application/json; charset=utf-8")
      @ResponseBody
      public String newsAdd(String token, String title, String type, String label, String content, String src){   
    	  System.out.println("token=" + token);
    	  System.out.println("title=" + title);
    	  System.out.println("type=" + type);
    	  System.out.println("content=" + content);
      	//参数格式错误
      	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(title) || StringUtils.isEmpty(type)
      			|| StringUtils.isEmpty(content)) {
      		System.out.println("invalid parm");
  			return "{\"retcode\": \"305\"}";
  		}
      	
      	try {
      		title=URLDecoder.decode(title,"utf-8");
      		type=URLDecoder.decode(type,"utf-8");
      		label=URLDecoder.decode(label,"utf-8");
      		content=URLDecoder.decode(content,"utf-8");
      		src=URLDecoder.decode(src,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
      	String statement = null;
      	
      	//查找token是否存在
  		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
  		UserToken userToken = sqlSession.selectOne(statement, token);
  		System.out.println("userToken=" + userToken);
      	
  		//无效的token
  		if (userToken == null) {
  			System.out.println("invalid token");
  			return "{\"retcode\": \"307\"}";
  		}
  		
  		//用户权限错误
  		if (userToken.getLevel() != 1) {
  			System.out.println("user permission error");
  			return "{\"retcode\": \"304\"}";
  		}
  		
  		//新闻类别不存在
  		statement = "com.wjj.dao.NewsTypeDao.getNewsTypeByName";
  		NewsType newsType = sqlSession.selectOne(statement, type);
  		System.out.println("newsType=" + newsType);
  		if (newsType == null) {
  			System.out.println("news type not existe");
  			return "{\"retcode\": \"402\"}";
  		}
  		
  		String keyWord = KeyWord.getKeyWord(content);
  		
  		statement = "com.wjj.dao.NewsDao.addNews";
      	sqlSession.insert(statement, new News(0, title, type, label, keyWord, content, src));
          return "{\"retcode\": \"200\"}";
      }

    //####################删除新闻(管理员)#######################
	/**
	 * 
	 * @param token		管理员token
	 * @param id		新闻id
	 * @return
	 */
    @RequestMapping(value="/newsDelete", produces="application/json; charset=utf-8")
    @ResponseBody
    public String newsDelete(String token, String id){   
    	//参数格式错误
    	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(id)) {
    		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
    	String statement = null;
    	
    	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
    	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
		if (userToken.getLevel() != 1) {
			System.out.println("user permission error");
			return "{\"retcode\": \"304\"}";
		}
		
		//新闻不存在
		statement = "com.wjj.dao.NewsDao.getNewsById";
		News news = sqlSession.selectOne(statement, Integer.parseInt(id));
		System.out.println("news=" + news);
		if (news == null) {
			System.out.println("news not exist)");
			return "{\"retcode\": \"403\"}";
		}
		
		statement = "com.wjj.dao.NewsDao.deleteNews";
    	sqlSession.delete(statement, Integer.parseInt(id));
        return "{\"retcode\": \"200\"}";
    }
    
    //####################获取新闻列表(普通用户/管理员)#######################
	/**
	 * 
	 * @param token		管理员token
	 * @param type		新闻类别
	 * @return
	 */
	  @RequestMapping(value="/newsGetList", produces="application/json; charset=utf-8")
	  @ResponseBody
	  public String newsGetList(String token, String type){   
	  	//参数格式错误
	  	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(type)) {
	  		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
	  	String statement = null;
	  	try {
			type=URLDecoder.decode(type,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
	  	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
//		if (userToken.getLevel() != 2) {
//			System.out.println("user permission error");
//			return "{\"retcode\": \"304\"}";
//		}
		
		//新闻类别不存在
  		statement = "com.wjj.dao.NewsTypeDao.getNewsTypeByName";
  		NewsType newsType = sqlSession.selectOne(statement, type);
  		System.out.println("newsType=" + newsType);
  		if (newsType == null) {
  			System.out.println("news type not exist");
  			return "{\"retcode\": \"402\"}";
  		}
		
		statement = "com.wjj.dao.NewsDao.getNewsByType";
	  	List<News> newsList = sqlSession.selectList(statement, type);
	  	JSONArray jsonArray = new JSONArray();
	  	for (News news : newsList) {
	  		JSONObject j = new JSONObject();
	  		j.put("id", news.getId());
	  		j.put("title", news.getTitle());
			jsonArray.add(j);
		}
	  	
	  	JSONObject ret = new JSONObject();
	  	ret.put("retcode", "200");
	  	ret.put("newsList", jsonArray);
	    return ret.toJSONString();
	  }
	  
	  //####################获取新闻内容(普通用户/管理员)#######################
		/**
		 * 
		 * @param token		普通用户token
		 * @param id		新闻id
		 * @return
		 */
	  @RequestMapping(value="/newsGet", produces="application/json; charset=utf-8")
	  @ResponseBody
	  public String newsGet(String token, String id){   
	  	//参数格式错误
	  	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(id) ) {
	  		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
	  	String statement = null;
	  	
	  	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
	  	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
//		if (userToken.getLevel() != 2) {
//			System.out.println("user permission error");
//			return "{\"retcode\": \"304\"}";
//		}
		
		//新闻不存在
		statement = "com.wjj.dao.NewsDao.getNewsById";
		News news = sqlSession.selectOne(statement, Integer.parseInt(id));
		System.out.println("news=" + news);
		if (news == null) {
			System.out.println("news not exist)");
			return "{\"retcode\": \"403\"}";
		}
		
		//更新用户的历史浏览记录
		statement = "com.wjj.dao.UserDao.getUserById";
		User user = sqlSession.selectOne(statement, userToken.getId());
		System.out.println("user=" + user);
		
		if (StringUtils.isEmpty(user.getHistory_news())) {			//历史浏览记录为空
			user.setHistory_news(id);
		} else {										//历史浏览记录不为空
			//数组转set
	        Set<String> set = Stream.of(user.getHistory_news().split(",")).collect(Collectors.toSet());
	        set.add(id);
	        //set转数组
	        String[] array= set.stream().toArray(String[]::new);
	        StringBuffer sb = new StringBuffer();
	        for (String string : array) {
	        	sb.append(string + ",");
			}
	        user.setHistory_news(sb.toString());
		}
		//将修改后的历史记录更新到数据库
		statement = "com.wjj.dao.UserDao.updateUser";
		sqlSession.update(statement, user);
		
	  	JSONObject ret = new JSONObject();
	  	ret.put("retcode", "200");
	  	ret.put("news", news);
	    return ret.toJSONString();
	  }
	  
	//####################获取历史浏览新闻列表(普通用户)#######################
	/**
	 * 
	 * @param token		管理员token
	 * @return
	 */
	  @RequestMapping(value="/newsGetHistoryList", produces="application/json; charset=utf-8")
	  @ResponseBody
	  public String newsGetHistoryList(String token){   
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
	  	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
//		if (userToken.getLevel() != 2) {
//			System.out.println("user permission error");
//			return "{\"retcode\": \"304\"}";
//		}
		
		//获取用户的历史记录id
		statement = "com.wjj.dao.UserDao.getUserById";
	  	User user = sqlSession.selectOne(statement, userToken.getId());
	  	String historyNews = user.getHistory_news();
	  	String[] historyList = historyNews.split(",");
	  	
	  	//根据历史记录id获取列表
	  	JSONArray jsonArray = new JSONArray();
	  	for (String history : historyList) {
	  		statement = "com.wjj.dao.NewsDao.getNewsById";
		  	News news = sqlSession.selectOne(statement, Integer.parseInt(history));
		  	JSONObject j = new JSONObject();
	  		j.put("id", news.getId());
	  		j.put("title", news.getTitle());
			jsonArray.add(j);
		}
	  	
	  	JSONObject ret = new JSONObject();
	  	ret.put("retcode", "200");
	  	ret.put("historyList", jsonArray);
	    return ret.toJSONString();
	  }
	  
	  //####################搜索新闻(普通用户/管理员)#######################
		/**
		 * 
		 * @param token		用户token
		 * @param keyword	搜索的关键字
		 * @return
		 */
	  @RequestMapping(value="/newsSearch", produces="application/json; charset=utf-8")
	  @ResponseBody
	  public String newsSearch(String token, String keyword){   
	  	//参数格式错误
	  	if (StringUtils.isEmpty(token) || StringUtils.isEmpty(keyword) ) {
	  		System.out.println("invalid parm");
			return "{\"retcode\": \"305\"}";
		}
	  	String statement = null;
	  	
	  	//查找token是否存在
		statement = "com.wjj.dao.UserTokenDao.getUserTokenByName";
		UserToken userToken = sqlSession.selectOne(statement, token);
		System.out.println("userToken=" + userToken);
	  	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
//		if (userToken.getLevel() != 2) {
//			System.out.println("user permission error");
//			return "{\"retcode\": \"304\"}";
//		}
		
		try {
			keyword=URLDecoder.decode(keyword,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//根据关键字查询新闻
		statement = "com.wjj.dao.NewsDao.getNewsByTitleKeyword";
		List<News> newsList = sqlSession.selectList(statement, "%" + keyword + "%");
		System.out.println("news=" + newsList);
		
		JSONArray jsonArray = new JSONArray();
		for (News news : newsList) {
		  	JSONObject j = new JSONObject();
			j.put("id", news.getId());
			j.put("title", news.getTitle());
			jsonArray.add(j);
		}
		
		//更新用户的搜索记录
		statement = "com.wjj.dao.UserDao.getUserById";
		User user = sqlSession.selectOne(statement, userToken.getId());
		System.out.println("user=" + user);
		
		if (StringUtils.isEmpty(user.getSearch_words())) {			//历史浏览记录为空
			user.setSearch_words(keyword);;
		} else {										//历史浏览记录不为空
			//数组转set
	        Set<String> set = Stream.of(user.getSearch_words().split(",")).collect(Collectors.toSet());
	        set.add(keyword);
	        //set转数组
	        String[] array= set.stream().toArray(String[]::new);
	        StringBuffer sb = new StringBuffer();
	        for (String string : array) {
	        	sb.append(string + ",");
			}
	        user.setSearch_words(sb.toString());;
		}
		//将修改后的历史记录更新到数据库
		statement = "com.wjj.dao.UserDao.updateUser";
		sqlSession.update(statement, user);
		
	  	JSONObject ret = new JSONObject();
	  	ret.put("retcode", "200");
	  	ret.put("newsList", jsonArray);
	    return ret.toJSONString();
	  }
	  
	//####################推荐新闻(普通用户/管理员)#######################
		/**
		 * 
		 * @param token		用户token
		 * @return
		 */
	  @RequestMapping(value="/newsRecommend", produces="application/json; charset=utf-8")
	  @ResponseBody
	  public String newsRecommend(String token){   
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
	  	
		//无效的token
		if (userToken == null) {
			System.out.println("invalid token");
			return "{\"retcode\": \"307\"}";
		}
		
		//用户权限错误
//			if (userToken.getLevel() != 2) {
//				System.out.println("user permission error");
//				return "{\"retcode\": \"304\"}";
//			}
		
		//获取用户搜索记录
		statement = "com.wjj.dao.UserDao.getUserById";
		User user = sqlSession.selectOne(statement, userToken.getId());
		System.out.println("user=" + user);
		
		//无搜索记录
		if (user.getSearch_words() == null) {
			System.out.println("no search history");
			return "{\"retcode\": \"404\"}";
		}
		
		String[] search_words = user.getSearch_words().split(",");	//搜索记录
		JSONArray jsonArray = new JSONArray();
		Set<Integer> set = new HashSet<Integer>();
		for (String s : search_words) {
			//根据搜索记录来推荐新闻
			statement = "com.wjj.dao.NewsDao.getNewsByKeyword";
			List<News> newsList = sqlSession.selectList(statement, "%" + s + "%");
			System.out.println("news=" + newsList);
			
			for (News news : newsList) {
				if (!set.add(news.getId())) {			//如果已存在，则不添加，跳过本次循环
					continue;
				}
				
			  	JSONObject j = new JSONObject();
				j.put("id", news.getId());
				j.put("title", news.getTitle());
				jsonArray.add(j);
			}
		}
		
	  	JSONObject ret = new JSONObject();
	  	ret.put("retcode", "200");
	  	ret.put("newsList", jsonArray);
	    return ret.toJSONString();
	  }
 
}