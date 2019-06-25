package com.example.demo;

import java.io.Serializable;

import javax.validation.constraints.Size;


/*
//sessionスコープを宣言
@Component
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
*/
public class LoginForm implements Serializable{
	private static final long serialVersionUID=1L;


	@Size(min=4,max=10, message="未入力または文字数が正しくありません")
	private String id;

	@Size(min=8,max=20, message="未入力または文字数が正しくありません")
	private String password;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getPassword() {
		return password;
	}
	//(String ： htmlでつけたnameのパス名)
	public void setPassword(String password) {
		this.password=password;
	}



}
