package com.oneul.web.entity;

import java.sql.Date;

public class Oneline {
	private Integer id;
	private String content;
	private Integer memberId;
	private Date regDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "Oneline24 [id=" + id + ", content=" + content + ", memberId=" + memberId + ", regDate=" + regDate + "]";
	}
	
	
	
	
}

