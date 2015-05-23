/**
 * Address.java
 * java  class with getters and setters for addresses fields
 * version: Rev 1
 * date: 22/05/1015 
 * @author Betel Samson Tadesse
 * x14117649
 * 
 * 
 */
package com.betel.offer.model;

public class Address {
	
	private Integer addressId;
	private String address;//Formatted address stored as street_name in address table
	private String postalCode;
	private String country;
	private String phone;
	private double latitude;
	private double longitude;
	private Integer mealId;
	
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Integer getMealId() {
		return mealId;
	}
	public void setMealId(Integer mealId) {
		this.mealId = mealId;
	}
	
	
}
