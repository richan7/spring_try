package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.GoodsForm;

@Service
public class GoodsService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	GoodsForm sessiongf;

//	private String c;
//	private int price;

//	public GoodsService(JdbcTemplate jj,Model model,String ca){
//		m=model;
//		c=ca;
//	}
//
//	public GoodsService(int p){
//		price=p;
//	}
//

	public List<Map<String, Object>> categoryname(){
		//カテゴリー名呼び出してhtmlのh2に表示
		return jdbcTemplate.queryForList("SELECT CATEGORY_NAME FROM goods_category WHERE CATEGORY_ID=?",sessiongf.getCategory());

	}
//
//	public void Ggoods() {
//		//SQLの商品マスタgoodsテーブルから読み込み
//		List<Map<String, Object>> list_goods;
//		list_goods = jdbcTemplate.queryForList("SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE,g.GOODS_ID "
//				+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID"
//				+ " WHERE g.CATEGORY_ID=? ORDER BY g.GOODS_PRICE", c);
//		m.addAttribute("goods", list_goods);
//	}
//
//	public void Price() {
//		//昇順降順
//		String sql="SELECT g.GOODS_PHOTO,c.CATEGORY_NAME,g.GOODS_NAME,g.GOODS_PRICE,g.GOODS_ID "
//				+ "FROM goods g LEFT JOIN goods_category c ON g.CATEGORY_ID=c.CATEGORY_ID "
//				+ " WHERE g.CATEGORY_ID=2 ORDER BY g.GOODS_PRICE ";
//
//				//安い順（昇順）
//				if(price==1) {
//				}
//				//高い順（降順）
//				else if(price==2){
//					// sql=sql+"DESC" 文字列連結
//					sql+="DESC";
//				}
//				//カテゴリごとの安い順、高い順に表示
//				List<Map<String, Object>> list;
//				list = j.queryForList(sql);
//				m.addAttribute("goods",list);
//
//	}

}
