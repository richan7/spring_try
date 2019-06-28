package com.example.demo;

import java.io.Serializable;

//sessionスコープを宣言
//@Component
//@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)

public class CartForm implements Serializable{
	private static final long serialVersionUID=1L;

	private int count;

	private int goodsid;

	private int cartid;

	public int getCount(){
		return count;
	}

	public void setCount(int c) {
		this.count=c;
	}

	public int getGoodsid(){
		return goodsid;
	}

	public void setGoodsid(int gi) {
		this.goodsid=gi;
	}

	public int getCartid(){
		return cartid;
	}

	public void setCartid(int ci) {
		this.cartid=ci;
	}


}
