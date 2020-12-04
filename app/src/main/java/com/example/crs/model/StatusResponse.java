package com.example.crs.model;

import com.google.gson.annotations.SerializedName;

public class StatusResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public int getId(){
		return id;
	}

	public String getStatus(){
		return status;
	}
}