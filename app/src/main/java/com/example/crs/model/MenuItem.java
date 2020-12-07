package com.example.crs.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuItem{

	@SerializedName("unique_id")
	private String uniqueId;

	@SerializedName("total_price")
	private String totalPrice;

	@SerializedName("booking_timeslot")
	private String bookingTimeslot;

	@SerializedName("total_count")
	private String totalCount;

	@SerializedName("booking_status")
	private String bookingStatus;

	@SerializedName("id")
	private int id;

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setUniqueId(String uniqueId){
		this.uniqueId = uniqueId;
	}

	public String getUniqueId(){
		return uniqueId;
	}

	public void setTotalPrice(String totalPrice){
		this.totalPrice = totalPrice;
	}

	public String getTotalPrice(){
		return totalPrice;
	}

	public void setBookingTimeslot(String bookingTimeslot){
		this.bookingTimeslot = bookingTimeslot;
	}

	public String getBookingTimeslot(){
		return bookingTimeslot;
	}

	public void setTotalCount(String totalCount){
		this.totalCount = totalCount;
	}

	public String getTotalCount(){
		return totalCount;
	}

	public void setBookingStatus(String bookingStatus){
		this.bookingStatus = bookingStatus;
	}

	public String getBookingStatus(){
		return bookingStatus;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"MenuItem{" + 
			"unique_id = '" + uniqueId + '\'' + 
			",total_price = '" + totalPrice + '\'' + 
			",booking_timeslot = '" + bookingTimeslot + '\'' + 
			",total_count = '" + totalCount + '\'' + 
			",booking_status = '" + bookingStatus + '\'' + 
			",id = '" + id + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}