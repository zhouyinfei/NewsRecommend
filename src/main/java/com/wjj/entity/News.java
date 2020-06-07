package com.wjj.entity;

public class News {
	
	private int id;
	private String title;
	private String type;
	private String label;
	private String keyword;
	private String content;
	private String src;
	
	public News() {
		super();
	}

	public News(int id, String title, String type, String label,
			String keyword, String content, String src) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.label = label;
		this.keyword = keyword;
		this.content = content;
		this.src = src;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", type=" + type
				+ ", label=" + label + ", keyword=" + keyword + ", content="
				+ content + ", src=" + src + "]";
	}
	
}
