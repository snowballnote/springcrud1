package com.example.springcrud.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springcrud.dto.AddUserForm;
import com.example.springcrud.dto.User;
import com.example.springcrud.dto.UserInfo;
import com.example.springcrud.mapper.UserMapper;

@Service
@Transactional
public class UserService {
	@Autowired 
	UserMapper userMapper;
	
	@Transactional // 트랜젝션은 유지
	public int removeUser(User u) {
	    // UserInfo 삭제 (외래 키 제약 조건 때문에 User 테이블보다 먼저 삭제)
	    userMapper.deleteUserInfoByUserNo(u.getUserNo());
	    
	    // User 삭제
	    int row = userMapper.deleteUserByUserNo(u.getUserNo());
	    
	    return row;
	}
	
	public List<User> getUserListByPage(int currentPage, String searchWord) {
		int rowPerPage = 10;
		int beginRow = (currentPage - 1) * rowPerPage;
		Map<String, Object> map = new HashMap<>();
		map.put("rowPerPage", rowPerPage);
		map.put("beginRow", beginRow);
		map.put("searchWord", searchWord);
		return userMapper.selectUserListByPage(map);
	}
	
	public Map<String, Object> login(User u) {
		return userMapper.selectUserByLogin(u);
	}
	
	public void addUser(AddUserForm auf) {
		User u = new User();
		u.setUserId(auf.getUserId());
		u.setUserPw(auf.getUserPw());
		// 1) db 작업
		int row = userMapper.insertUser(u); // 실행전 u.userN=0; 샐행후 u.userNo = auto_increment
		
		if(row == 1) {
			UserInfo ui = new UserInfo();
			ui.setUserNo(u.getUserNo());
			ui.setUserCity(auf.getUserCity());
			// 2) db 작업
			userMapper.insertUserInfo(ui);
		}
	}
}