package com.oneul.web.service;

import java.util.List;

import com.oneul.web.entity.FutureDiary;

public interface FutureDiaryService {

	List<FutureDiary> getList(int page);

	FutureDiary get(int id);
	
	int delete(int id);

	int insert(FutureDiary futureDiary);

	int update(FutureDiary futureDiary);

}