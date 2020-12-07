package com.example.crs.model;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("booking_id")
	private String bookingId;

	@SerializedName("item_count")
	private String itemCount;

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

	public void setBookingId(String bookingId){
		this.bookingId = bookingId;
	}

	public String getBookingId(){
		return bookingId;
	}

	public void setItemCount(String itemCount){
		this.itemCount = itemCount;
	}

	public String getItemCount(){
		return itemCount;
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
			"ItemsItem{" + 
			"booking_id = '" + bookingId + '\'' + 
			",item_count = '" + itemCount + '\'' + 
			",item_price = '" + itemPrice + '\'' + 
			",item_name = '" + itemName + '\'' + 
			",id = '" + id + '\'' + 
			",item_description = '" + itemDescription + '\'' + 
			",item_calories = '" + itemCalories + '\'' + 
			"}";
		}
}