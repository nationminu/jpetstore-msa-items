package com.rock.jpetstore.domain;
 
 
public class Inventories {
  
	private String itemId; 
	private int qty; 
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId.trim();
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
  	
}
