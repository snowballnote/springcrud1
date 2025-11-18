package com.example.springcrud.dto;

import lombok.Data;

@Data
public class AddUserForm {
	private String userId;
	private String userPw;
	private String userCity;
}
