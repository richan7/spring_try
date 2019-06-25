package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.CartService;

@Controller
public class GoodsController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	//sessionスコープををもつFormを宣言します
	@Autowired
	SessionForm sf;
	@Autowired
	GoodsForm sessiongf;
//	@Autowired
//	GoodsService gservice;

	@RequestMapping(path = "/goods", method = RequestMethod.GET)
	public String goods(Model model, @RequestParam String category,@ModelAttribute("goodsFrom") GoodsForm gf) {
		//ログインした場合ユーザー名表示
		model.addAttribute("user", sf.getId());
		//sectionで選択されたcategory（ナンバー）を保持する
		sessiongf.setCategory(category);

		//カテゴリをh2に表示
		List<Map<String, Object>> list_category;
		list_category=jdbcTemplate.queryForList("SELECT CATEGORY_NAME FROM goods_category WHERE CATEGORY_ID=?",sessiongf.getCategory());
		model.addAttribute("goods_category", list_category);
//		List<Map<String, Object>>list_category;
//		list_category =gservice.categoryname();
//		model.addAttribute("goods_category", list_category);

		//SQLの商品マスタgoodsテーブルから読み込み
				List<Map<String, Object>> list_goods;
				list_goods = jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE,g.GOODS_ID "
						+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID"
						+ " WHERE g.CATEGORY_ID=? ORDER BY g.GOODS_PRICE", category);
				model.addAttribute("goods", list_goods);

//		//サービスクラスから呼び出し
//		GoodsService gs= new GoodsService(model,sessiongf.getCategory());
//		//カテゴリをh2に表示
//
//		model.addAttribute("goods_category", gService.Cgoods());
//		//商品一覧を表示
//		gs.Ggoods();


		//画像
/*		List<Map<String, Object>> list_photo;
		list_photo = jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE,g.GOODS_ID "
				+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID"
				+ " WHERE g.CATEGORY_ID=?", category);
		for(int i =0; i<list_photo.size(); i++) {
			map=list_category.get(i);
		}
*/

		return "goods";

	}

	@RequestMapping(path = "/goods", method = RequestMethod.POST)
	public String lgoods(Model model,@ModelAttribute GoodsForm gf) {
		//ログインした場合ユーザー名表示
		model.addAttribute("user", sf.getId());
		//カテゴリをh2に表示
		List<Map<String, Object>> list_category;
		list_category=jdbcTemplate.queryForList("SELECT CATEGORY_NAME FROM goods_category WHERE CATEGORY_ID=?",sessiongf.getCategory());
		model.addAttribute("goods_category", list_category);
		//カテゴリー名呼び出してhtmlのh2に表示
		//GoodsServiceのメソッドをつかってる
//		GoodsService gs= new GoodsService(jdbcTemplate,model,sessiongf.getCategory());
//		gs.Cgoods();

		//昇順降順
		String sql="SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE,g.GOODS_ID "
				+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID "
				+ " WHERE g.CATEGORY_ID=? ORDER BY g.GOODS_PRICE ";
		int priceorder = gf.getPriceorder();
			//安い順（昇順）
			if(priceorder==1) {
			}
			//高い順（降順）
			else if(priceorder==2){
				// sql=sql+"DESC" 文字列連結
				sql+="DESC";
			}
			//カテゴリごとの安い順、高い順に表示
			List<Map<String, Object>> list;
			list = jdbcTemplate.queryForList(sql,sessiongf.getCategory());
			model.addAttribute("goods",list);

//		//昇順降順のserviceがうまく機能していない。
//		GoodsService pgs= new GoodsService(gf.getPriceorder());
//		pgs.Price();

			return "goods";

	}

	@RequestMapping(path = "/goodscommit", method = RequestMethod.GET)
	public String price(Model model, @Valid @ModelAttribute("goodsFrom") GoodsForm gf) {
		//ログインした場合ユーザー名表示
		model.addAttribute("user", sf.getId());
//		//サービスクラスから呼び出し
//		GoodsService gs= new GoodsService(jdbcTemplate,model,sessiongf.getCategory());
//		//カテゴリをh2に表示
//		gs.Cgoods();

		//カテゴリをh2に表示
		List<Map<String, Object>> list_category;
		list_category=jdbcTemplate.queryForList("SELECT CATEGORY_NAME FROM goods_category WHERE CATEGORY_ID=?",sessiongf.getCategory());
		model.addAttribute("goods_category", list_category);
		//価格で絞り込んだときの表示
		List<Map<String, Object>> list;
		list = jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE,g.GOODS_ID "
				+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID "
				+ "WHERE g.GOODS_PRICE>=? AND g.GOODS_PRICE<=? AND g.CATEGORY_ID=? ORDER BY g.GOODS_PRICE", gf.getPricemin(),gf.getPricemax(),sessiongf.getCategory());
		model.addAttribute("goods", list);

		return "goods";

	}

	//カートに入れたい情報
	@RequestMapping(path = "/cart", method = RequestMethod.POST)
	public String goods(Model model, @ModelAttribute GoodsForm gf,BindingResult result) {
		//ログインした場合ユーザー名表示
		model.addAttribute("user", sf.getId());
		//数量をhiddenに値を持たせる
		model.addAttribute("count", gf.getCount());

		//商品を選んだときの処理を書く (カートのhtmlに情報を渡したい)
		//選んだデーターをデーターテーブルにいれる
		if(sf.getId()==null) {
			model.addAttribute("message","ログインしてください");
		}
		else {
			//カートにいれた日
			Date now = new Date();
			SimpleDateFormat registerNow = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");

			//カートで選んだ商品ををテーブルにINSERTする
			jdbcTemplate.update("INSERT INTO cartinfo(LOGIN_USER_ID,COUNT,GOODS_ID,CART_DATE)VALUES(?,?,?,?)",sf.getId(),gf.getCount(),gf.getGoodsid(),registerNow.format(now.getTime()));

			//カートの中身------カートに入れた情報を表示 CartServiceのメソッドを呼び出す
			CartService cs= new CartService(jdbcTemplate,model,sf.getId());
			cs.Cart();

		}

		return "cart";

	}

}
