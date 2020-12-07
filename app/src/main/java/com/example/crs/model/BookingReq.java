package com.example.crs.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BookingReq{

	@SerializedName("unique_id")
	private String uniqueId;

	@SerializedName("total_price")
	private String totalPrice;

	@SerializedName("total_count")
	private String totalCount;

	@SerializedName("booking_status")
	private String bookingStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@SerializedName("id")
	private String id;


	public String getBooking_timeslot() {
		return booking_timeslot;
	}

	public void setBooking_timeslot(String booking_timeslot) {
		this.booking_timeslot = booking_timeslot;
	}

	@SerializedName("booking_timeslot")
	private String booking_timeslot;

	@SerializedName("items")
	private List<MenuInfo> items;

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

	public void setItems(List<MenuInfo> items){
		this.items = items;
	}

	public List<MenuInfo> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"BookingReq{" + 
			"unique_id = '" + uniqueId + '\'' + 
			",total_price = '" + totalPrice + '\'' + 
			",total_count = '" + totalCount + '\'' + 
			",booking_status = '" + bookingStatus + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}