package com.example.crs.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VIewBookingRes{

	@SerializedName("menu")
	private List<MenuItem> menu;

	@SerializedName("status")
	private String status;

	public void setMenu(List<MenuItem> menu){
		this.menu = menu;
	}

	public List<MenuItem> getMenu(){
		return menu;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"VIewBookingRes{" + 
			"menu = '" + menu + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}