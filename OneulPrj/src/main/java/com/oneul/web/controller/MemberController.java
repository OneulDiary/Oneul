package com.oneul.web.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oneul.web.entity.Member;
import com.oneul.web.service.MemberService;

@Controller("MemberController")
@RequestMapping("/member/")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	//회원가입페이지 조회
	@RequestMapping("signup") 
	public String signup(@RequestParam(name = "result", defaultValue = "0") int result
						, Model model) {
		if(result == 1)
			model.addAttribute("checkmsg","중복된 아이디입니다.");
			
		return "member/signup";
	}
	
	//아이디 중복체크버튼
	@ResponseBody
	@PostMapping("checkid")
	public int check_id(String username) {
		Member member = new Member();
		member.setUserId(username);
		
		int result = service.check(member);
		
		return result;
	}
	
	//회원정보 등록
	@PostMapping("reg") 
	public String reg(String username,
						String password,
						String name,
						Date birthday,
						String email,
						Model model) {
		
		Member member2 = new Member();
		member2.setUserId(username);
		int result = service.check(member2);
		
		if(result == 1) {
			return "redirect:signup?result="+result;			
		}
		else {
			Member member = new Member();
			member.setUserId(username);
			String noopPassword = "{noop}"+password;
			member.setPassword(noopPassword);
			member.setName(name);
			member.setBirthday(birthday);
			member.setEmail(email);
			
			service.insert(member);
			
			return "redirect:login";
		}
	}
	
	//아이디찾기페이지 조회
	@RequestMapping("findid")
	public String findid() {
		return "member/findid";		
	}
	
	//아이디 찾기 실행
	@PostMapping("findid")
	public String findid(String name, Date birthday, String email,HttpServletRequest request) {
		Member member = new Member();
		member.setName(name);
		member.setBirthday(birthday);
		member.setEmail(email);
		
		//아이디 찾기 서비스
		String username = service.findid(member);
		member.setUserId(username);
		
		//아이디 session에 담기
		HttpSession session = request.getSession(true);
		session.setAttribute("member", member);
		
		return "redirect:foundid";
	}
	
	//아이디 찾은 후 페이지 조회
	@RequestMapping("foundid")
	public String foundid(Model model) {
		
		return "member/foundid";		
	}
	
	//비밀번호찾기 페이지 조회
	@RequestMapping("findpwd")
	public String findpwd() {
		return "member/findpwd";		
	}
	
	//비밀번호 찾기 수행
	@PostMapping("findpwd")
	public String findpwd(String username, String name, Date birthday, String email, Model model) {
		
		//회원정보체크
		Member member = new Member();
		member.setUserId(username);
		member.setName(name);
		member.setBirthday(birthday);
		member.setEmail(email);
		
		int result = service.check(member);
		
		if(result==1) { //회원정보 있으면
			//임시비밀번호생성
			String pwd = service.makePwd(10);
			String realpwd = "{noop}"+pwd;
			//임시비밀번호로 변경
			member.setPassword(realpwd);
			service.updatebyname(member);
			
			//비밀번호 이메일 발송
			String title = "Oneul 임시비밀번호";
			String body = name+"님의 임시비밀번호는 "+pwd+" 입니다.";
			service.sendEmail(email, title, body);
			
			return "redirect:login";
			
		}else { //회원정보 없으면
			model.addAttribute("msg","존재하지않는 회원정보입니다");
			return "member/findpwd";
		}

		
	}
	
	//로그인페이지 조회
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		String errMsg;
		
		if(request.getAttribute("loginFailMsg")!=null) {
			errMsg = (String) request.getAttribute("loginFailMsg");
			model.addAttribute("errMsg", errMsg);
		}
		
		return "member/login";
	}
	
	//회원정보수정 페이지 조회
	@RequestMapping("edit") 
	public String edit(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		
		Member member = new Member();
		member = service.get(username);
		
		//{noop} 없애기
		String password_ = member.getPassword();
		String password = password_.substring(6);
		member.setPassword(password);
		
		model.addAttribute(member);
		
		return "member/edit";		
	}
	
	@PostMapping("edit")
	public String edit(HttpServletRequest request,String username, String password) {
		
		//변경전 회원아이디
		HttpSession session = request.getSession(true); 
		String originUsername = (String) session.getAttribute("username");
		
		//회원 식별 아이디
		Member member2 = new Member();
		member2 = service.get(originUsername);
		int id = member2.getId();
		
		//변경된 회원정보 업데이트
		Member member = new Member();
		member.setId(id);
		member.setUserId(username);
		String noopPassword = "{noop}"+password;
		member.setPassword(noopPassword);
		
		service.updatebyid(member);
		
		return "redirect:/doLogout";
	}
	
	@RequestMapping("logintest")
	public String logintest() {
		return "member/logintest";		
	}
		
	@RequestMapping("mypagetest")
	public String mypage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		
		Member member2 = service.get(username);
		String fileName = member2.getImage();
		
		
		Member member = new Member();
		member.setUserId(username);
		member.setImage(fileName);
		model.addAttribute("member", member2);
		
		return "member/mypagetest";
	}
	
	//프로필사진 업로드
	@PostMapping("upload") 
	public String upload(MultipartFile file, HttpServletRequest request, Model model, Principal principal) {
		//로그인 아이디 획득
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		
		//식별변호 획득
		Member member2 = service.get(username);
		int id = member2.getId();
		
		String fileName = file.getOriginalFilename();//파일이름
		
		ServletContext application = request.getServletContext();
		String path = "/upload/profile/"+id;
		String realPath = application.getRealPath(path);
		
		File pathFile = new File(realPath);
		if(!pathFile.exists()) //upload할 경로 존재x하면
			pathFile.mkdirs(); //경로 생성
		
		String filePath = realPath + File.separator + fileName;
		
		File saveFile = new File(filePath); //저장할 파일
		
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
		Member member = new Member();
		member.setId(id);
		member.setUserId(username);
		member.setImage(fileName);
		service.updatebyname(member);
		
		
		model.addAttribute("member", member2);
		
		return "redirect:mypagetest";
	}
	
	@GetMapping("basic")
	public String basic(HttpServletRequest request) {
		//로그인 아이디 획득
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		
		Member member = new Member();
		member.setUserId(username);
		member.setImage("basic.jpg");
		
		service.updatebyname(member);
		
		return "redirect:mypagetest";		
	}
	
	@GetMapping("out")
	public String out(HttpServletRequest request) {
		//로그인 아이디 획득
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		
		//식별변호 획득
		Member member = service.get(username);
		int id = member.getId();
		service.delete(id);
		
		
		return "redirect:login";
	}
	
	

}


