package com.oneul.web.dao;

import java.util.List;

import com.oneul.web.entity.CalendarEmotion;
import com.oneul.web.entity.GratitudeDiary;

public interface GratitudeDiaryDao {

	GratitudeDiary get(int id);
	
	List<GratitudeDiary> getList();
	List<GratitudeDiary> getList(int offset, int size);
	
	int insertDiary(GratitudeDiary gratitudeDiary);
	int updateDiary(GratitudeDiary gratitudeDiary);
	int delete(int id);
	
	int insertCalendar(CalendarEmotion calendarEmotion);
	int updateCalendar(CalendarEmotion calendarEmotion);

	int selectCalEmotionCnt(CalendarEmotion calendarEmotion);
	
	
}
