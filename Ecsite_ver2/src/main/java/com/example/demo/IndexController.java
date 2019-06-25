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
public class IndexController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	//sessionスコープををもつFormを宣言します
	@Autowired
	SessionForm sf;

	@RequestMapping(path ="/index", method = RequestMethod.GET)
	public String index(Model model) {
		//ユーザーログインしたら表示される
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
		//季節のよびだし
		List<Map<String,Object>>list_season;
		list_season=jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE "
				+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID"
				+ " WHERE g.CATEGORY_ID=2 AND g.GOODS_ID=15");
		model.addAttribute("season",list_season);


		return "index";
	}



}