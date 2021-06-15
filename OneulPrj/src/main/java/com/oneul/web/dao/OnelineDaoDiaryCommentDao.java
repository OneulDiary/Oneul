package com.oneul.web.dao;

import java.util.List;

import com.oneul.web.entity.Oneline;

public interface OnelineDaoDiaryCommentDao {
	Oneline get(int id);	
	List<Oneline> getList();//필터링, 정렬, 페이징, 집계	
	List<Oneline> getList(int offset, int size);
	List<Oneline> getList(int offset,int size, String field, String query);
	
	int insert (Oneline oneline);
	int delete (int id);
	int update(Oneline oneline);
	
	

}
