package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MypageController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	//sessionスコープををもつFormを宣言します
	@Autowired
	SessionForm sf;

	@RequestMapping(path ="/mypage", method = RequestMethod.GET)
	public String mypate(Model model) {

		String myinfo=sf.getId();
		//ユーザー情報があれば
		if(myinfo!=null) {
			//ユーザー名表示
			model.addAttribute("user" , sf.getId());
			//ユーザーがログインしていたらメールアドレス表示
			List<Map<String,Object>>list;
			list= jdbcTemplate.queryForList("SELECT LOGIN_USER_ID,PASSWORD,MAIL, CREATE_DATE FROM user WHERE LOGIN_USER_ID=? " ,sf.getId());
			model.addAttribute("mail" , list.get(0).get("MAIL"));

			//買った情報を表示
			List<Map<String, Object>> list_buy;
			list_buy = jdbcTemplate.queryForList("SELECT g.GOODS_NAME,g.GOODS_PRICE,b.COUNT,b.BUY_DATE "
					+ "FROM goods g LEFT JOIN buyinfo b ON g.GOODS_ID=b.GOODS_ID "
					+ " WHERE b.LOGIN_USER_ID=? ORDER BY b.BUY_DATE DESC",sf.getId());
			model.addAttribute("buylist",list_buy);

			return "mypage";

		}
		//ユーザー情報がなければ空のデータを表示
		else {
			return "mypage";
		}

	}

	@RequestMapping(path ="/newid", method = RequestMethod.POST)
	public String userdelete(Model model) {

		return "newid";
	}


}