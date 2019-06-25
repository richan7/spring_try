package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.CartService;

@Controller
public class CartController {
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	//sessionスコープををもつFormを宣言します
	@Autowired
	SessionForm sf;

	@Autowired
	GoodsForm sessiongf;

	@RequestMapping(path ="/cart", method = RequestMethod.GET)
	public String cart(Model model) {
		model.addAttribute("user" , sf.getId());

		//カートの中身------カートに入れた情報を表示 CartServiceのメソッドを呼び出す
		CartService cs= new CartService(jdbcTemplate,model,sf.getId());
		cs.Cart();

		//時間がたったらカートの中身が消える処理を書く


		return "cart";
	}

	@RequestMapping(path = "/cart2", method = RequestMethod.POST)
	public String count(Model model, @ModelAttribute CartForm cf) {
		model.addAttribute("user" , sf.getId());

		//削除ボタンを押したとき指定した商品のカートの中身が消える
		jdbcTemplate.update("DELETE FROM cartinfo WHERE GOODS_ID=?",cf.getGoodsid());
		//カートの中身
		CartService cs= new CartService(jdbcTemplate,model,sf.getId());
		cs.Cart();

		return "cart";
	}

//	@RequestMapping(path = "/cartcount", method = RequestMethod.POST)
//	public String cartcount(Model model, @ModelAttribute CartForm cf) {
//		model.addAttribute("user" , sf.getId());
//		//数量を変更したい
//		jdbcTemplate.update("UPDATE cartinfo SET COUNT=? WHERE GOODS_ID=?",cf.getCount(),cf.getGoodsid());
//		//カートの中身
//		CartService cs= new CartService(jdbcTemplate,model,sf.getId());
//		cs.Cart();
//
//		return "cart";
//	}

	@RequestMapping(path = "/buy", method = RequestMethod.POST)
	public String buy(Model model, @ModelAttribute CartForm cf) {
		model.addAttribute("user" , sf.getId());

		//カートの内容
		List<Map<String, Object>> list;
		list = jdbcTemplate.queryForList("SELECT GOODS_ID,COUNT FROM cartinfo WHERE LOGIN_USER_ID=?",sf.getId());
		//ゲストの場合は購入できない。
		if(sf.getId()==null) {
			model.addAttribute("message","ログインしてください");
			return "cart";
		}
		//ユーザーがログインしていてカートの中身がある場合
		else {
			//商品買った日
			Date now = new Date();
			SimpleDateFormat registerNow = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
			logger.info("sessionid" +sf.getId());

			for(int i=0; i<list.size(); i++) {
				//商品を購入した場合、買った情報をbuyinfoにINSERTする
				jdbcTemplate.update("INSERT INTO buyinfo (LOGIN_USER_ID,COUNT,GOODS_ID,BUY_DATE) VALUE(?,?,?,?)"
						,sf.getId(),list.get(i).get("COUNT"),list.get(i).get("GOODS_ID"),registerNow.format(now.getTime()));
				//購入した瞬間にカートの中身が削除される
				jdbcTemplate.update("DELETE FROM cartinfo WHERE GOODS_ID=?",list.get(i).get("GOODS_ID"));

			}
			return "bought";
		}

	}

}