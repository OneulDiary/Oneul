package com.oneul.web.dao;

import java.util.List;

import com.oneul.web.entity.FreeDiaryView;
import com.oneul.web.entity.FutureDiaryView;
import com.oneul.web.entity.GratitudeDiaryView;

public interface ApiDiaryDao {
	
	
	List<FreeDiaryView> getFreeList(Integer memberId, String query, Integer pageOffset);
	List<FutureDiaryView> getFutureList(Integer memberId, String query, Integer pageOffset);
	List<GratitudeDiaryView> getGratitudeList(Integer memberId, String query, Integer pageOffset);
	

}
