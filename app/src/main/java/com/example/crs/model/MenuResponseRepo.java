package com.example.crs.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuResponseRepo{

	@SerializedName("data")
	private List<MenuInfo> data;

	@SerializedName("status")
	private String status;

	public void setData(List<MenuInfo> data){
		this.data = data;
	}

	public List<MenuInfo> getData(){
		return data;
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
			"MenuResponseRepo{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}