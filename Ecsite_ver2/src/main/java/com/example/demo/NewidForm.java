package com.example.demo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class NewidForm implements Serializable{
	private static final long serialVersionUID=1L;

	@NotEmpty(message="半角英数字の文字を入力してください")
	@Size(min=4,max=10, message="4から10文字で入力してください")
	private String newid;

	@NotEmpty(message="半角英数字の文字を入力してください")
	@Size(min=8,max=20, message="8から20文字で入力してください")
	private String newpassword;

	@Size(min=8,max=20, message="入力が正しくありません")
	private String checkpw;

	@Email()
	@NotEmpty(message="入力してください")
	private String email;

	public String getNewid() {
		return newid;
	}

	public void setNewid(String newid) {
		this.newid=newid;
	}

	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword=newpassword;
	}

	public String getCheckpw() {
		return newpassword;
	}
	public void setCheckpw(String checkpw) {
		this.checkpw=checkpw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email=email;
	}



}
