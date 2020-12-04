package com.example.crs.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo(){
        return phoneNo;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public void setUniqueId(String uniqueId){
        this.uniqueId = uniqueId;
    }

    public String getUniqueId(){
        return uniqueId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString(){
        return
                "Response{" +
                        "phone_no = '" + phoneNo + '\'' +
                        ",password = '" + password + '\'' +
                        ",role = '" + role + '\'' +
                        ",unique_id = '" + uniqueId + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }
}
