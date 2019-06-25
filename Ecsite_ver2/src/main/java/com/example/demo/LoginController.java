package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginController {
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	//sessionスコープををもつFormを宣言します
	@Autowired
	SessionForm sf;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String index(Model model) {
/*		sessionはここには書いても意味がない
 * 		ログインがされる瞬間
 *		 List<Map<String,Object>>list;
		list=jdbcTemplate.queryForList("SELECT LOGIN_USER_ID FROM user WHERE LOGIN_USER_ID=?", lf.getId());
		sessionForm.setId((String) list.get(0).get("LOGIN_USER_ID"));
*/
		logger.info("index method");
		return "login";
	}

	@RequestMapping(path = "/index", method = RequestMethod.POST)
	public String index(Model model,  @Valid @ModelAttribute LoginForm lf,BindingResult result) {
		String[]data=null;

		if(result.hasErrors()) {
			// user idのエラー
			List<FieldError> userErr = result.getFieldErrors("id");
			data = new String[userErr.size()];
			for (int i = 0; i < userErr.size(); i++) {
				data[i] = userErr.get(i).getDefaultMessage();
			}
			List<String> list_user_id = Arrays.asList(data);
			model.addAttribute("err_user_id", list_user_id);

			// passwordエラー
			List<FieldError> passwordErr = result.getFieldErrors("password");
				data = new String[passwordErr.size()];
			for (int i = 0; i < passwordErr.size(); i++) {
				data[i] = passwordErr.get(i).getDefaultMessage();
			}
			List<String> list_password = Arrays.asList(data);

			model.addAttribute("err_password", list_password);

			return "login";

		}
		else {
			List<Map<String,Object>>list;
			list=jdbcTemplate.queryForList("SELECT LOGIN_USER_ID,PASSWORD "
					+ "FROM user "
					+ "WHERE LOGIN_USER_ID=? AND PASSWORD=?",lf.getId(),lf.getPassword());

			//格納された1行ずつのデーターがない場合、ユーザー登録されていない
			if(list.size()==0) {
				return "login";
			}
			//なんらかの重複されたユーザーがある場合
			if(list.size()>=2) {
				//ダブりのときに何かするのを書く
				return "login";
			}
			//sectionでidを保持する
			sf.setId((String) list.get(0).get("LOGIN_USER_ID"));

		}
		model.addAttribute("user" , sf.getId());

		//お勧め商品にのせたいから、SQLの商品マスタgoodsテーブルから読み込み
				//eto（干支）のよびだし
				List<Map<String,Object>>list_eto;
				list_eto=jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE "
						+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID"
						+ " WHERE g.CATEGORY_ID=1 AND g.GOODS_ID=10");
				model.addAttribute("eto",list_eto);
				//jewelry（アクセサリー）のよびだし
				List<Map<String,Object>>list_jewelry;
				list_jewelry=jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE "
						+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID"
						+ " WHERE g.CATEGORY_ID=3 AND g.GOODS_NAME='音符ブローチ'");
				model.addAttribute("jewelry",list_jewelry);


		return "index";
	}

}