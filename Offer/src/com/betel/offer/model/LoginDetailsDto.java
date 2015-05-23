/**
 * LoginDetailsDto.java
 * java  class with getters and setters of users login detail email and password
 * version: Rev 1
 * date: 22/05/1015 
 * @author Betel Samson Tadesse
 * x14117649
 * 
 * 
 */

package com.betel.offer.model;

public class LoginDetailsDto {

	private String eMail;
	private String password;

	
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
