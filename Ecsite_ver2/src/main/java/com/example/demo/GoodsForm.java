package com.example.demo;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

//sessionスコープを宣言
@Component
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)

public class GoodsForm implements Serializable{
	private static final long serialVersionUID=1L;

	private int priceorder;

	private String pricemin;

	private String pricemax;

	private String goodsid;

	private String category;

	private String gname;

	private String gprice;

	private int count;

	public int getPriceorder() {
		return priceorder;
	}
	public void setPriceorder(int i) {
		this.priceorder=i;
	}

	public String getPricemin() {
		return pricemin;
	}
	public void setPricemin(String pricemin) {
		this.pricemin=pricemin;
	}

	public String getPricemax() {
		return pricemax;
	}
	public void setPricemax(String pricemax) {
		this.pricemax=pricemax;
	}

	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid=goodsid;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category=category;
	}

	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname=gname;
	}
	public String getGprice() {
		return gprice;
	}
	public void setGprice(String gprice) {
		this.gprice=gprice;
	}
	public int getCount(){
		return count;
	}

	public void setCount(int c) {
		this.count=c;
	}



}
