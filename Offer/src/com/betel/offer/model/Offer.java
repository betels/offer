/**
 * Offer.java
 * java  class with getters and setters of offers detail (offers fields)
 * version: Rev 1
 * date: 22/05/1015 
 * @author Betel Samson Tadesse
 * x14117649
 * 
 * 
 */
package com.betel.offer.model;


public class Offer {
	
	private int mealId;
	private String mealName;
	private java.sql.Timestamp startTime;
	private java.sql.Timestamp endTime;
	private Address address;
	private String imageName;
	private Integer userId;
	private Double newPrice;
	private Double oldPrice;
	private String description;
	
	
	public Integer getMealId() {
		return mealId;
	}
	public void setMealId(Integer mealId) {
		this.mealId = mealId;
	}
	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	
	public java.sql.Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}
	public java.sql.Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
	public Double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public void setMealId(int mealId) {
//		this.mealId = mealId;
//	}
	
	

}
