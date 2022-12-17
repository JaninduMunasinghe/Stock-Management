package com.xadmin.ordermanagement.bean;

public class Order {
	
	private int id;
	private String pname;
	private String supplier;
	private String price;
	private String quantity;
	private String date;
	
	//default constructor
	public Order(String pname, String supplier, String price, String quantity, String date) {
		super();
		this.pname = pname;
		this.supplier = supplier;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
	}


	public Order(int id, String pname, String supplier, String price, String quantity, String date) {
		super();
		this.id = id;
		this.pname = pname;
		this.supplier = supplier;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
	}
	
	
	//getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
