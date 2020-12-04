package com.example.crs.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseList{

	@SerializedName("data")
	private List<UserInfo> data;

	@SerializedName("status")
	private String status;

	public void setData(List<UserInfo> data){
		this.data = data;
	}

	public List<UserInfo> getData(){
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
			"ResponseList{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}