package com.example.crs.model;

import com.google.gson.annotations.SerializedName;

public class MenuInfo {

	@SerializedName("item_price")
	private String itemPrice;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("id")
	private int id;

	@SerializedName("item_description")
	private String itemDescription;

	@SerializedName("item_calories")
	private String itemCalories;

	@SerializedName("item_count")
	private int itemCount=0;

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}



	public void setItemPrice(String itemPrice){
		this.itemPrice = itemPrice;
	}

	public String getItemPrice(){
		return itemPrice;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setItemDescription(String itemDescription){
		this.itemDescription = itemDescription;
	}

	public String getItemDescription(){
		return itemDescription;
	}

	public void setItemCalories(String itemCalories){
		this.itemCalories = itemCalories;
	}

	public String getItemCalories(){
		return itemCalories;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"item_price = '" + itemPrice + '\'' + 
			",item_name = '" + itemName + '\'' +
					",item_count = '" + itemCount + '\'' +
					",id = '" + id + '\'' +
			",item_description = '" + itemDescription + '\'' + 
			",item_calories = '" + itemCalories + '\'' + 
			"}";
		}
}