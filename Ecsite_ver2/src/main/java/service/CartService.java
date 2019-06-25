package service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CartService {
	private JdbcTemplate j;
	private Model m;
	private String user;


	public CartService(JdbcTemplate jj,Model model,String id) {
		j=jj;
		m=model;
		user=id;
	}

	public void Cart() {
		//カートに入れた情報を表示
		List<Map<String, Object>> list;
		list = j.queryForList("SELECT g.GOODS_NAME,g.GOODS_PRICE,c.CART_DATE,g.GOODS_ID,c.COUNT "
				+ "FROM goods g LEFT JOIN cartinfo c ON g.GOODS_ID=c.GOODS_ID "
				+ " WHERE c.LOGIN_USER_ID=?",user);

			//カートの中身がない場合
			if(list==null) {
				m.addAttribute("empty","カートの中身がありません");
			}
			//中身がある場合
			else {
				m.addAttribute("cartlist",list);

				//現在
				//SimpleDateFormat now2 = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");

//				Date now = new Date();
//				Date cartday= (Date) list.get(0).get("CART_DATE");
//
//				long nowd=now.getTime();
//				long cartd=cartday.getTime();
//				//時間で計算
//				long day=(nowd-cartd)/(1000*60*60);
//
//				if(day>1) {
//					j.update("DELETE FROM cartinfo WHERE CART_DATE=?",list.get(0).get("CART_DATE"));
//				}


//				for(int i=0; i<list.size(); i++) {
//					if(now2.format(now.getTime().compareTo(list.get(i).get("CART_DATE"))) {
//
//					}
//				}
			}

	}
}
