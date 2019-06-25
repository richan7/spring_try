package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NewidController {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(path = "/newid", method = RequestMethod.GET)
	public String index(Model model) {
		return "newid";
	}

	@RequestMapping(path = "/madenewid", method = RequestMethod.POST)
	public String index(Model model,  @Valid @ModelAttribute NewidForm nf,BindingResult result) {
		String[]data=null;

		if(result.hasErrors()) {

			// user idのエラー
			List<FieldError> userErr = result.getFieldErrors("newid");
			data = new String[userErr.size()];
			for (int i = 0; i < userErr.size(); i++) {
				data[i] = userErr.get(i).getDefaultMessage();
			}
			List<String> list_userid = Arrays.asList(data);
			model.addAttribute("err_userid", list_userid);

			// passwordエラー
			List<FieldError> pwErr = result.getFieldErrors("newpassword");
				data = new String[pwErr.size()];
			for (int i = 0; i < pwErr.size(); i++) {
				data[i] = pwErr.get(i).getDefaultMessage();
			}
			List<String> list_pw = Arrays.asList(data);
			model.addAttribute("err_password", list_pw);

			// 確認passwordエラー
			List<FieldError> pw2Err = result.getFieldErrors("checkpw");
				data = new String[pw2Err.size()];
			for (int i = 0; i < pw2Err.size(); i++) {
				data[i] = pw2Err.get(i).getDefaultMessage();
			}
			List<String> list_pw2 = Arrays.asList(data);
			model.addAttribute("err_password2", list_pw2);

			// emailエラー
			List<FieldError> emailErr = result.getFieldErrors("email");
				data = new String[emailErr.size()];
			for (int i = 0; i < emailErr.size(); i++) {
				data[i] = emailErr.get(i).getDefaultMessage();
			}
			List<String> list_email = Arrays.asList(data);
			model.addAttribute("err_email", list_email);

			return "newid";

		}
		//パスワードと確認用パスワードが合っていたらSQLのテーブルのIDの重複がないか確認
		else if(nf.getNewpassword()== nf.getCheckpw()){
			List<Map<String,Object>>list_id;
			list_id=jdbcTemplate.queryForList("SELECT LOGIN_USER_ID, PASSWORD FROM user WHERE LOGIN_USER_ID=?",nf.getNewid() );
			//もしIDの重複がなければ登録をする
			if(list_id.size()==0) {
				//ID登録日
				Date now = new Date();
				SimpleDateFormat registerNow = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
				//新規登録情報をテーブルにINSERTする
				jdbcTemplate.update("INSERT INTO user(LOGIN_USER_ID,PASSWORD,MAIL,DELETE_FLAG,CREATE_DATE )VALUES(?,?,?,?,?)",nf.getNewid(),nf.getNewpassword(),nf.getEmail(),0,registerNow.format(now.getTime()));
			}
			//もしIDの重複があれば新規登録画面に戻る
			else {
				return "newid";
			}
		}


		return "madenewid";

	}


}