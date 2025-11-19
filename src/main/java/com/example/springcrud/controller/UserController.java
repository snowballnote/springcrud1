package com.example.springcrud.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springcrud.dto.AddUserForm;
import com.example.springcrud.dto.User;
import com.example.springcrud.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	// 회원 탈퇴 폼으로 이동 (GET 요청)
	@GetMapping("/removeUser")
	public String removeUser() {
		return "removeUser";
	}
	
	// 회원 탈퇴 요청 처리 (POST 요청)
	@PostMapping("/removeUser")
	public String removeUser(HttpSession session, User u) {
		// 세션에서 로그인된 유저 정보를 가져옴 (UserNo를 얻기 위함)
	    Map<String, Object> loginUser = (Map<String, Object>) session.getAttribute("loginUser");
	   
	    if (loginUser == null) {
            System.out.println("로그인 세션 없음.");
            return "redirect:/";
        }
	    
//	    int userNo = (Integer) loginUser.get("userNo");
//	    u.setUserNo(userNo);
	    
	    // 서비스 호출 (비밀번호 검증 및 탈퇴 처리)
	    int row = userService.removeUser(u);
	    
	    if (row == 0) {
	        System.out.println("탈퇴 실패: 비밀번호 불일치 또는 DB 오류");
	        return "redirect:/userList";
	    }
		
		System.out.println(u.getUserNo() + "번 회원 탈퇴 성공");
		return "redirect:/userList";
	}
	
	// 회원 리스트
	@GetMapping("/userList")
	public String userList(Model model, @RequestParam(defaultValue = "1") int currentPage, 
													@RequestParam(defaultValue = "") String searchWord) {
		List<User> list = userService.getUserListByPage(currentPage, searchWord);
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("prePage", currentPage-1);
		model.addAttribute("nextPage", currentPage+1);
		model.addAttribute("searchWord", searchWord);
		return "userList";
	}
	
	// 로그인 성공시
	@GetMapping("/successLogin")
	public String successLogin() {
		return "successLogin";
	}
	
	// 회원가입 폼으로 이동 (GET 요청)
	@GetMapping("/addUser")
	public String addUser() {
		return "addUser";
	}
		
	// 회원가입 요청 처리 (POST 요청)
	@PostMapping("/addUser")
	public String addUser(AddUserForm addUserForm) {
		System.out.println(addUserForm.getUserId());
		System.out.println(addUserForm.getUserPw());
		System.out.println(addUserForm.getUserCity());
		
		userService.addUser(addUserForm);
		
		return "redirect:/login"; // @Controller 메서드 반환값 redirecct: 접두사 뒤에는 contextPath 생략!
	}
	
	// 로그아웃 폼으로 이동 (GET 요청)
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home"; // 리다이렉트
	}
	
	// 로그인 폼
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	// 로그인 요청 처리 (POST 요청)
	@PostMapping("/login")
	public String login(HttpSession session, User u) {
		System.out.println(u.getUserId());
		System.out.println(u.getUserPw());
		// service login메서드를 호출
		Map<String, Object> loginUser = userService.login(u);
		if(loginUser == null) { // 로그인 실패시 login.mustache 로 다시 포워딩
			System.out.println("로그인실패");
			return "login";
		}
		
		session.setAttribute("loginUser", loginUser);
		System.out.println("로그인성공");
		return "redirect:/successLogin"; // 리다이렉트
	}
}