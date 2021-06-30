package com.oneul.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oneul.web.entity.FutureDiary;
import com.oneul.web.entity.FutureDiaryComment;
import com.oneul.web.service.FutureDiaryCommentService;
import com.oneul.web.service.FutureDiaryService;

@Controller
@RequestMapping("/diary/futurediary/")
public class FutureDiaryController {

	@Autowired
	private FutureDiaryService service;
	
	@Autowired
	private FutureDiaryCommentService commentService;
	
	@RequestMapping("list")
	public String list(Model model) {
		List<FutureDiary> list = service.getList(1);
		
		model.addAttribute("list", list);
		return("diary/futurediary/list");
	}
	
	@RequestMapping("detail")
	public String detail(Model model, int id) {
		FutureDiary futureDiary = service.get(id);
		
		model.addAttribute("futureDiary", futureDiary);
		
		List<FutureDiaryComment> list = commentService.getViewList(id);
		model.addAttribute("commentList", list);
		return("diary/futurediary/detail");
	}
	
	@PostMapping("commentReg")
	public String commentReg(int id, String content) {
		FutureDiaryComment comment = new FutureDiaryComment();
		comment.setContent(content);
		comment.setFutureDiaryId(id);
		comment.setMemberId(4);
		
		commentService.insert(comment);
		return("redirect:detail?id="+id);
	}
	
	@RequestMapping("commentDel")
	public String commentDel(int id, int futureDiaryId) {
		commentService.delete(id);
		
		return("redirect:detail?id="+futureDiaryId);
		
	}
	
	@PostMapping("reg")
	public String reg(@DateTimeFormat(pattern = "yyyy-MM-dd")Date bookingDate, 
					  String content,
					  MultipartFile file,
					  String pub,
					  String emotionId,
					  HttpServletRequest request) {
		int p = Integer.parseInt(pub);
		int emt = Integer.parseInt(emotionId);
		
		String fileName = file.getOriginalFilename();
		
		FutureDiary futureDiary = new FutureDiary();	
		futureDiary.setBookingDate(bookingDate);
		futureDiary.setContent(content);
		futureDiary.setMemberId(4);
		futureDiary.setPub(p);
		futureDiary.setEmotionId(emt);
		futureDiary.setImage(fileName);
		
		service.insert(futureDiary);

		System.out.println(futureDiary.getId());
		int id = futureDiary.getId();
		
		if(!fileName.equals("")) {
			ServletContext application = request.getServletContext();
			String path = "/upload/diary/futureDiary/"+"4"+"/"+id; //회원id + 일기id
			String realPath = application.getRealPath(path);
			
			File pathFile = new File(realPath);
			if(!pathFile.exists())
				pathFile.mkdirs();
			
			String filePath = realPath + File.separator +fileName; 
			
			System.out.println(filePath);
			File saveFile = new File(filePath);
			
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		
		return("redirect:list");
	}
	
	@GetMapping("reg")
	public String reg() {
		return("diary/futurediary/reg");
	}
	
	@GetMapping("edit")
	public String edit(int id,Model model) {
		FutureDiary futureDiary = service.get(id);
		
		model.addAttribute("futureDiary", futureDiary);
		return("diary/futurediary/edit");
	}
	
	@PostMapping("edit")
	public String edit(FutureDiary futureDiary,
						MultipartFile file, 
						HttpServletRequest request,
						int changed,
						String originalFile) {
		System.out.println(changed);
		System.out.println(originalFile);
		
		int id = futureDiary.getId();
	
		
		//파일 수정안됐을 경우 원래 파일로 저장
		if(changed ==0) {
			futureDiary.setImage(originalFile);
		}
		
		String fileName = file.getOriginalFilename();
		
		// 파일 수정됐을 경우
		//이전 파일 지우고 수정된 파일로 저장
		if(!fileName.equals("") && changed == 1) {
			
			ServletContext application = request.getServletContext();
			//이전 파일 삭제
	         String prevFilePath = "/upload/diary/futureDiary/"+"4"+"/"+id;
	         String prevFilerealPath = application.getRealPath(prevFilePath);
	         String deleteFilePath = prevFilerealPath + File.separator+originalFile;
	         System.out.println(deleteFilePath);
	         
	         File deleteFile = new File(deleteFilePath);
	          if(deleteFile.exists()) {	                  
	                  // 파일 삭제.
	             deleteFile.delete(); 
	             System.out.println("삭제완료");
	          }

			String path = "/upload/diary/futureDiary/"+"4"+"/"+id;
			String realPath = application.getRealPath(path);
			
			File pathFile = new File(realPath);
			if(!pathFile.exists())
				pathFile.mkdirs();
			
			String filePath = realPath + File.separator + fileName;
			
			//System.out.println(filePath);
			File saveFile = new File(filePath);
			
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			futureDiary.setImage(fileName);
		}
		
		//파일 삭제됐을 경우
		if(fileName.equals("")&& changed == 1) {
			ServletContext application = request.getServletContext();
	
	         String prevFilePath = "/upload/diary/futureDiary/"+"4"+"/"+id;
	         String prevFilerealPath = application.getRealPath(prevFilePath);
	         
	         File folder = new File(prevFilerealPath);
	         while(folder.exists()) {
	     		File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
	     				
	     		for (int j = 0; j < folder_list.length; j++) {
	     			folder_list[j].delete(); //파일 삭제 
	     			System.out.println("파일이 삭제되었습니다.");
	     					
	     		}
	     				
	     		if(folder_list.length == 0 && folder.isDirectory()){ 
	     			folder.delete(); //대상폴더 삭제
	     			System.out.println("폴더가 삭제되었습니다.");
	     		}
	                 }
	        
	         
			futureDiary.setImage(fileName);			
		}
		
		
		service.update(futureDiary);
		return "redirect:detail?id="+futureDiary.getId();
	}
	
	@RequestMapping("del")
	public String del(int id) {
		
		service.delete(id);
		
		return("redirect:list");
	}
	
	
		//js에서 삭제버튼 onclick -> 이미지클래스 src 지우고..
		//원본파일은 컨트롤러에서 조건처리(만약에 파일이 ''이면 원래파일삭제..원래파일은? 히든으로 전달할가..
	
	
	
}
