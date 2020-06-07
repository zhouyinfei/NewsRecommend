package com.wjj.entity;

import java.util.Date;

public class UserToken {

	private int id;
	private String token;
	private Date create_time;
	private int level;
	
	public UserToken() {
		super();
	}

	public UserToken(int id, String token, Date create_time, int level) {
		super();
		this.id = id;
		this.token = token;
		this.create_time = create_time;
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "UserToken [id=" + id + ", token=" + token + ", create_time="
				+ create_time + ", level=" + level + "]";
	}
	
}
