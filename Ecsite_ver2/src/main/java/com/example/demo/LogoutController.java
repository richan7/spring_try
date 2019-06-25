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
public class LogoutController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	//sessionスコープををもつFormを宣言します
	@Autowired
	SessionForm sf;

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout() {

		return "logout";
	}

	@RequestMapping(path = "/index2", method = RequestMethod.POST)
	public String logout(Model model) {

//		jdbcTemplate.update("DELETE FROM cartinfo WHERE LOGIN_USER_ID=?",sf.getId());
		sf.clear();
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