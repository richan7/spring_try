package com.example.demo;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;



//sessionスコープを宣言
@Component
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)

public class SessionForm implements Serializable{
	private static final long serialVersionUID=1L;

	private String id;

	private String password;

	private String email;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public void clear() {
		id= null;
		password= null;
		email=null;

	}




}
