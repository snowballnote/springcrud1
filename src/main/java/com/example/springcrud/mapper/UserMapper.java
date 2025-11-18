package com.example.springcrud.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.springcrud.dto.User;
import com.example.springcrud.dto.UserInfo;

@Mapper
public interface UserMapper { // User Table + UserInfo Table
	// 목록 조회(페이징/검색)
	List<User> selectUserListByPage(Map<String, Object> m);
	
	Map<String, Object> selectUserByLogin(User user);
	
	int insertUser(User u);
	int insertUserInfo(UserInfo ui);
	
	// 탈퇴를 위한 비밀번호 검증 (UserNo와 UserPw를 인자로 받음)
	User selectUserByUserNoAndPw(User user); 

	// UserInfo 테이블 데이터 삭제
	int deleteUserInfoByUserNo(int userNo);

	// User 테이블 데이터 삭제
	int deleteUserByUserNo(int userNo);
}