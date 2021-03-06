package com.oneul.web.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneul.web.entity.CalendarEmotion;
import com.oneul.web.entity.FreeDiary;
import com.oneul.web.entity.FreeDiaryView;
import com.oneul.web.entity.FutureDiaryView;
import com.oneul.web.entity.GratitudeDiaryView;
import com.oneul.web.entity.Member;
import com.oneul.web.entity.OnelineView;
import com.oneul.web.service.ApiDiaryService;
import com.oneul.web.service.CalendarEmotionService;
import com.oneul.web.service.MemberService;

@RestController("ApiDiaryController")
@RequestMapping("/api/diary")
public class DiaryController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ApiDiaryService apiDiaryService;
	
	@Autowired
	private CalendarEmotionService calendarService;
	

	@GetMapping("/free/{p}")
	public List<FreeDiaryView> freeDiaryList(@PathVariable(name ="p") Integer page, 
			@RequestParam( name = "q", defaultValue = "") String query, 
			HttpServletRequest request) {
		
		// 로그인된 멤버를 가져옴
		HttpSession session = request.getSession(true);//세션에 유저네임을 넣어놨다->해당유저네임을꺼내기
		String username = (String) session.getAttribute("username");
		Member member = memberService.get(username);
		
		
		// page로 pageOffset을 구함
		Integer pageOffset = (page-1) * 10;
		
		// list를 가져옴
		List<FreeDiaryView> list = apiDiaryService.getFreeList(member.getId(), query, pageOffset);//해당아이디 일기리스트

		

		return list;
	}
	
	@GetMapping("/oneline/{p}")
	public List<OnelineView> onelineList(@PathVariable(name ="p") Integer page, 
			@RequestParam( name = "q", defaultValue = "") String query, 
			HttpServletRequest request) {
		
		// 로그인된 멤버를 가져옴
		HttpSession session = request.getSession(true);//세션에 유저네임을 넣어놨다->해당유저네임을꺼내기
		String username = (String) session.getAttribute("username");
		Member member = memberService.get(username);
		
		
		// page로 pageOffset을 구함
		Integer pageOffset = (page-1) * 10;
		
		// list를 가져옴
		List<OnelineView> list = apiDiaryService.getOnelineList(member.getId(), query, pageOffset);//해당아이디 일기리스트

		

		return list;
	}

	
	@GetMapping("/future/{p}")
	public List<FutureDiaryView> futureDiaryList(@PathVariable(name ="p") Integer page, 
			@RequestParam( name = "q", defaultValue = "") String query, 
			HttpServletRequest request) {
		
		// 로그인된 멤버를 가져옴
		HttpSession session = request.getSession(true);//세션에 유저네임을 넣어놨다->해당유저네임을꺼내기
		String username = (String) session.getAttribute("username");
		Member member = memberService.get(username);
		
		
		// page로 pageOffset을 구함
		Integer pageOffset = (page-1) * 10;
		
		// list를 가져옴
		List<FutureDiaryView> list = apiDiaryService.getFutureList(member.getId(), query, pageOffset);//해당아이디 일기리스트

		

		return list;
	}


	@GetMapping("/gratitude/{p}")
	public List<GratitudeDiaryView> GratitudeDiaryList(@PathVariable(name ="p") Integer page, 
			@RequestParam( name = "q", defaultValue = "") String query, 
			HttpServletRequest request) {
		
		// 로그인된 멤버를 가져옴
		HttpSession session = request.getSession(true);//세션에 유저네임을 넣어놨다->해당유저네임을꺼내기
		String username = (String) session.getAttribute("username");
		Member member = memberService.get(username);
		
		
		// page로 pageOffset을 구함
		Integer pageOffset = (page-1) * 10;
		
		// list를 가져옴
		List<GratitudeDiaryView> list = apiDiaryService.getGratitudeList(member.getId(), query, pageOffset);//해당아이디 일기리스트

		

		return list;
	}


	@RequestMapping("listDiary")
	public List<CalendarEmotion> listDiary(HttpServletRequest request, 
			@RequestParam(name="startStr", required = false) String startStr,
						Model model) throws Exception{
		List<CalendarEmotion> resultList = null;
		HttpSession session = request.getSession(true);//세션에 유저네임을 넣어놨다->해당유저네임을꺼내기
		String username = (String) session.getAttribute("username");
		Member member = memberService.get(username);
		int id = member.getId();
		
		Date regDate=new SimpleDateFormat("yyyy-MM-dd").parse(startStr);//converting string into sql date.
		CalendarEmotion param = new CalendarEmotion();
		param.setMemberId(id);
		param.setRegDate(regDate);
		
		resultList = calendarService.getListDiary(param);
		
		return resultList;
	}






}
