package com.wjj.entity;
 
public class User {
	private int id;
	private String username;
	private String password;
	private String phonenum;
	private String email;
	private int level;
	private String history_news;
	private String search_words;
 
	public User() {
		super();
	}

	public User(int id, String username, String password, String phonenum,
			String email, int level, String history_news, String search_words) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phonenum = phonenum;
		this.email = email;
		this.level = level;
		this.history_news = history_news;
		this.search_words = search_words;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getHistory_news() {
		return history_news;
	}

	public void setHistory_news(String history_news) {
		this.history_news = history_news;
	}

	public String getSearch_words() {
		return search_words;
	}

	public void setSearch_words(String search_words) {
		this.search_words = search_words;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", phonenum=" + phonenum + ", email=" + email
				+ ", level=" + level + ", history_news=" + history_news
				+ ", search_words=" + search_words + "]";
	}
}
