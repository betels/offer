/**
 * DataManager.java
 * java  class for manipulation of data
 * version: Rev 1
 * date: 22/05/1015 
 * @author Betel Samson Tadesse
 * x14117649
 * 
 * 
 */
package com.betel.offer.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.betel.offer.model.Address;
import com.betel.offer.model.Coordinate;
import com.betel.offer.model.Offer;
import com.betel.offer.model.User;
import com.mysql.jdbc.PreparedStatement;

public class DataManager {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	
//	static final String DB_URL = "jdbc:mysql://127.10.19.130:3306/offer?zeroDateTimeBehavior=convertToNull";//"jdbc:mysql://localhost/offer?zeroDateTimeBehavior=convertToNull";
//	static final String USER = "adminsVc4QuM";
//	static final String PASS = "AYU54QSXSFds";

	static final String DB_URL = "jdbc:mysql://localhost/offerdb?zeroDateTimeBehavior=convertToNull";
	static final String USER = "root";
	static final String PASS = "";
	

/**
 * 
 * @param offer
 * @param user
 * @return
 * insert offer to offer table
 */
	public Map<String, Object> addOffer(Offer offer, User user) {
		Map<String, Object> message = new HashMap<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			Address address = offer.getAddress();
			
			String query = "INSERT INTO offer(MEAL_NAME,START_TIME,END_TIME,ADDRESS,IMAGE_NAME,USER_ID,NEW_PRICE,OLD_PRICE,DESCRIPTION) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, offer.getMealName());
			pstmt.setTimestamp(2, offer.getStartTime());
			pstmt.setTimestamp(3, offer.getEndTime());
			pstmt.setString(4, address.getAddress());
			pstmt.setString(5, offer.getImageName());
			pstmt.setInt(6, user.getUserId());
			pstmt.setDouble(7,offer.getNewPrice());
			pstmt.setDouble(8, offer.getOldPrice());
			pstmt.setString(9, offer.getDescription());
			
			int affectedRows  = pstmt.executeUpdate();
			int mealId =0;

	        if (affectedRows  == 0) {
	            throw new SQLException("Creation failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	mealId = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	        
	        /*
	         * insert into address table
	         */
			String queryAddress = "INSERT INTO address(FORMATTED_ADDESS,POSTAL_CODE,COUNTREY,PHONE_NUMBER,LATTITUDE,LONGTIUDE,MEAL_ID) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pstmtAddress = (PreparedStatement) conn.prepareStatement(queryAddress,Statement.RETURN_GENERATED_KEYS);
			pstmtAddress.setString(1, address.getAddress());
			pstmtAddress.setString(2, address.getPostalCode());
			pstmtAddress.setString(3, address.getCountry());
			pstmtAddress.setString(4, address.getPhone());
			pstmtAddress.setDouble(5, address.getLatitude());
			pstmtAddress.setDouble(6, address.getLongitude());
			pstmtAddress.setInt(7, mealId);
			int i = pstmtAddress.executeUpdate();

			if (i == 0) {
				message.put("success", false);
			} else {
				message.put("success", true);
			}
			//conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

/**
 * 
 * @param offer
 * @return
 * update offer
 */
	public Map<String, Object> updateOffer(Offer offer) {
		Map<String, Object> message = new HashMap<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			Address address = offer.getAddress();
			
			String query = "UPDATE offer SET MEAL_NAME=?,START_TIME=?,END_TIME=?,ADDRESS=?,IMAGE_NAME=?,USER_ID=?,NEW_PRICE=?,OLD_PRICE=?,DESCRIPTION=? WHERE MEAL_ID=?";
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);

			pstmt.setString(1, offer.getMealName());
			pstmt.setTimestamp(2, offer.getStartTime());
			pstmt.setTimestamp(3, offer.getEndTime());
			pstmt.setString(4, address.getAddress());			
			pstmt.setString(5, offer.getImageName());
			pstmt.setInt(6, offer.getUserId());
			pstmt.setInt(7, offer.getMealId());
			pstmt.setDouble(8,offer.getNewPrice());
			pstmt.setDouble(9, offer.getOldPrice());
			pstmt.setString(10, offer.getDescription());
			int i = pstmt.executeUpdate();

			if (i == 0) {
				message.put("success", false);
			} else {
				message.put("success", true);
			}
			conn.commit();
		} catch (Exception e) {

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * delete offer with specified id
	 */
	public Map<String, Object> deleteOffer(int id) {
		Map<String, Object> message = new HashMap<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			String query = "DELETE FROM address WHERE MEAL_ID=?";
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			
			query = "DELETE FROM offer WHERE MEAL_ID=?";
			pstmt = (PreparedStatement) conn
					.prepareStatement(query);
			pstmt.setInt(1, id);
		    i = pstmt.executeUpdate();
			
		    if (i == 0) {
				message.put("success", false);
			} else {
				message.put("success", true);
			}
			conn.commit();
		} catch (Exception e) {

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}
	
/**
 * 
 * @param userId
 * @return
 * to find offers to a specific user by providing userId
 */

	public List<Offer> getOffersByUserId(int userId) {
		List<Offer> offers = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT o.*,addr.* FROM offer o join address addr on(o.meal_id=addr.meal_id) where o.USER_ID=" + userId;
			
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int mealId = resultSet.getInt("MEAL_ID");
				String mealName = resultSet.getString("MEAL_NAME");
				Timestamp startTime = resultSet.getTimestamp("Start_time");
				Timestamp endTime = resultSet.getTimestamp("end_time");
				String imageName = resultSet.getString("IMAGE_NAME");
				Double newPrice = resultSet.getDouble("NEW_PRICE");
				Double oldPrice = resultSet.getDouble("OLD_PRICE");
				String description = resultSet.getString("DESCRIPTION");

				Offer offer = new Offer();
				offer.setMealId(mealId);
				offer.setMealName(mealName);
				offer.setStartTime(startTime);
				offer.setEndTime(endTime);
				offer.setImageName(imageName);
				offer.setUserId(userId);
				offer.setNewPrice(newPrice);
				offer.setOldPrice(oldPrice);
				offer.setDescription(description);

				int addressId = resultSet.getInt("ADDRESS_ID");
				String postalCode = resultSet.getString("POSTAL_CODE");
				String formattedAddress = resultSet.getString("FORMATTED_ADDESS");
				String country = resultSet.getString("COUNTREY");
				String phone = resultSet.getString("PHONE_NUMBER");
				double lat = resultSet.getDouble("LATTITUDE");
				double lon = resultSet.getDouble("LONGTIUDE");
				
				Address address = new Address();
				address.setAddressId(addressId);
				address.setPostalCode(postalCode);
				address.setAddress(formattedAddress);
				address.setCountry(country);
				address.setPhone(phone);
				address.setPostalCode(postalCode);
				address.setLatitude(lat);
				address.setLongitude(lon);
				address.setMealId(mealId);
				
				offer.setAddress(address);	
				
				offers.add(offer);

			}
			
			resultSet.close();
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offers;
	}
/**
 * 
 * @return
 * to get all list of offers
 */
	public List<Offer> getOffers() {
		List<Offer> offers = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT o.*,addr.* FROM offer o join address addr on(o.meal_id=addr.meal_id)";
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int mealId = resultSet.getInt("MEAL_ID");
				String mealName = resultSet.getString("MEAL_NAME");
				Timestamp startTime = resultSet.getTimestamp("Start_time");
				Timestamp endTime = resultSet.getTimestamp("end_time");
				String imageName = resultSet.getString("IMAGE_NAME");
				int userId = resultSet.getInt("USER_ID");
				Double newPrice = resultSet.getDouble("NEW_PRICE");
				Double oldPrice = resultSet.getDouble("OLD_PRICE");
				String description = resultSet.getString("DESCRIPTION");


				Offer offer = new Offer();
				offer.setMealId(mealId);
				offer.setMealName(mealName);
				offer.setStartTime(startTime);
				offer.setEndTime(endTime);
				offer.setImageName(imageName);
				offer.setUserId(userId);
				offer.setNewPrice(newPrice);
				offer.setOldPrice(oldPrice);
				offer.setDescription(description);

				int addressId = resultSet.getInt("ADDRESS_ID");
				String postalCode = resultSet.getString("POSTAL_CODE");
				String formattedAddress = resultSet.getString("FORMATTED_ADDESS");
				String country = resultSet.getString("COUNTREY");
				String phone = resultSet.getString("PHONE_NUMBER");
				double lat = resultSet.getDouble("LATTITUDE");
				double lon = resultSet.getDouble("LONGTIUDE");
				
				Address address = new Address();
				address.setAddressId(addressId);
				address.setPostalCode(postalCode);
				address.setAddress(formattedAddress);
				address.setCountry(country);
				address.setPhone(phone);
				address.setPostalCode(postalCode);
				address.setLatitude(lat);
				address.setLongitude(lon);
				address.setMealId(mealId);
				
				offer.setAddress(address);	
				
				offers.add(offer);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offers;
	}
/**
 * 
 * @return
 * 
 * to get all list of users
 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM USER";
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int userId = resultSet.getInt("USER_ID");
				String firstName = resultSet.getString("FIRST_NAME");
				String lastName = resultSet.getString("LAST_NAME");
				String eMail = resultSet.getString("E_MAIL");
				String password = resultSet.getString("PASSWORD");

				User user = new User();
				user.setUserId(userId);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.seteMail(eMail);
				user.setPassword(password);

				users.add(user);

			}

		} catch (Exception e) {

		}
		return users;
	}
/**
 * 
 * @param user
 * @return
 * to add user to user database
 */
	public Map<String, Object> addUser(User user) {
		Map<String, Object> message = new HashMap<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String query = "INSERT INTO user(USER_ID, FIRST_NAME,LAST_NAME,E_MAIL,PASSWORD) VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.geteMail());
			pstmt.setString(5, user.getPassword());

			int i = pstmt.executeUpdate();

			if (i == 0) {
				message.put("success", false);
			} else {
				message.put("success", true);
			}
			conn.commit();
		} catch (Exception e) {

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;

	}
/**
 * 
 * @param user
 * @return
 * to update user
 */
	public Map<String, Object> updateUser(User user) {
		Map<String, Object> message = new HashMap<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String pass = (user.getPassword() != null && user.getPassword()
					.length() > 0) ? ", PASSWORD=? " : "";
			String query = "UPDATE user SET FIRST_NAME=?,LAST_NAME=?,E_MAIL=?"
					+ pass + " where USER_ID=?";
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);

			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.geteMail());
			if (user.getPassword() != null && user.getPassword().length() > 0) {
				pstmt.setString(4, user.getPassword());
				pstmt.setInt(5, user.getUserId());
			} else {
				pstmt.setInt(4, user.getUserId());
			}

			int i = pstmt.executeUpdate();
			if (i == 0) {
				message.put("success", false);
			} else {
				message.put("success", true);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}
/**
 * 
 * @param id
 * @return
 * to delete user 
 */
	public Map<String, Object> deleteUser(int id) {
		Map<String, Object> message = new HashMap<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String query = "DELETE FROM user WHERE USER_ID=?";
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			if (i == 0) {
				message.put("success", false);
			} else {
				message.put("success", true);
			}
			conn.commit();
		} catch (Exception e) {

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}
/**
 * 
 * @param id
 * @return
 * to find a user
 */
	public User getUser(Integer id) {
		List<User> users = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM user";
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int userId = resultSet.getInt("USER_ID");
				String firstName = resultSet.getString("FIRST_NAME");
				String lastName = resultSet.getString("LAST_NAME");
				String eMail = resultSet.getString("E_MAIL");
				String password = resultSet.getString("PASSWORD");

				User user = new User();
				user.setUserId(userId);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.seteMail(eMail);
				user.setPassword(password);

				users.add(user);

			}

		} catch (Exception e) {

		}
		return users.get(0);
	}
/**
 * 
 * @param id
 * @return
 * to get an offer
 */
	public Offer getOffer(Integer id) {
		List<Offer> offers = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT o.*, addr.* FROM offer o join address addr on(o.meal_id=addr.meal_id) where o.MEAL_ID=" + id;
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int mealId = resultSet.getInt("MEAL_ID");
				String mealName = resultSet.getString("MEAL_NAME");
				Timestamp startTime = resultSet.getTimestamp("Start_time");
				Timestamp endTime = resultSet.getTimestamp("end_time");
				String imageName = resultSet.getString("IMAGE_NAME");
				int userId = resultSet.getInt("USER_ID");
				Double newPrice = resultSet.getDouble("NEW_PRICE");
				Double oldPrice = resultSet.getDouble("OLD_PRICE");
				String description = resultSet.getString("DESCRIPTION");



				Offer offer = new Offer();
				offer.setMealId(mealId);
				offer.setMealName(mealName);
				offer.setStartTime(startTime);
				offer.setEndTime(endTime);
				offer.setImageName(imageName);
				offer.setUserId(userId);
				offer.setNewPrice(newPrice);
				offer.setOldPrice(oldPrice);
				offer.setDescription(description);

				int addressId = resultSet.getInt("ADDRESS_ID");
				String postalCode = resultSet.getString("POSTAL_CODE");
				String formattedAddress = resultSet.getString("FORMATTED_ADDESS");
				String country = resultSet.getString("COUNTREY");
				String phone = resultSet.getString("PHONE_NUMBER");
				double lat = resultSet.getDouble("LATTITUDE");
				double lon = resultSet.getDouble("LONGTIUDE");
				
				Address address = new Address();
				address.setAddressId(addressId);
				address.setPostalCode(postalCode);
				address.setAddress(formattedAddress);
				address.setCountry(country);
				address.setPhone(phone);
				address.setPostalCode(postalCode);
				address.setLatitude(lat);
				address.setLongitude(lon);
				address.setMealId(mealId);
				
				offer.setAddress(address);	
				
				offers.add(offer);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return offers.get(0);
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 * 
	 * to get a user by email
	 */

	public User getUserByEmail(String email) {
		List<User> users = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM user WHERE E_MAIL='" + email + "'";
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int userId = resultSet.getInt("USER_ID");
				String firstName = resultSet.getString("FIRST_NAME");
				String lastName = resultSet.getString("LAST_NAME");
				String eMail = resultSet.getString("E_MAIL");
				String password = resultSet.getString("PASSWORD");

				User user = new User();
				user.setUserId(userId);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.seteMail(eMail);
				user.setPassword(password);

				users.add(user);

			}

		} catch (Exception e) {

		}
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param coordinate
	 * @return
	 * to get the closest offers 
	 * @ reference 
	 * Scribd.com, (2015). Geo Distance Search with MySQL. [online] Available at: http://www.scribd.com/doc/2569355/Geo-Distance-Search-with-MySQL [Accessed 22 May 2015].
	 */
	public  List<Offer> getNearestOffers(Coordinate coordinate) {
		List<Offer> offers = new ArrayList<>();
		double latitude = coordinate.getLatitude();
		double longitude = coordinate.getLongitude();
		double distance = 0.621371192237334;//in miles = 1Killo meter radius
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String query = "SELECT o.*,a.*, 3956 * 2 * ASIN(SQRT(POWER(SIN((" + latitude + " - abs(a.LATTITUDE)) * pi()/180/2),2) + COS(" + latitude + " * pi()/180 )*COS(abs(a.LATTITUDE)*pi()/180)*POWER(SIN((" + longitude + " - a.LONGTIUDE)*pi()/180/2), 2))) as distance FROM address a join offer o on (a.meal_id = o.meal_id) where o.END_TIME > CURTIME() having distance < " + distance + " ORDER BY distance, o.END_TIME limit 10";
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int mealId = resultSet.getInt("MEAL_ID");
				String mealName = resultSet.getString("MEAL_NAME");
				Timestamp startTime = resultSet.getTimestamp("Start_time");
				Timestamp endTime = resultSet.getTimestamp("end_time");
				String imageName = resultSet.getString("IMAGE_NAME");
				int userId = resultSet.getInt("USER_ID");
				Double newPrice = resultSet.getDouble("NEW_PRICE");
				Double oldPrice = resultSet.getDouble("OLD_PRICE");
				String description = resultSet.getString("DESCRIPTION");


				
				Offer offer = new Offer();
				offer.setMealId(mealId);
				offer.setMealName(mealName);
				offer.setStartTime(startTime);
				offer.setEndTime(endTime);
				offer.setImageName(imageName);
				offer.setUserId(userId);
				offer.setNewPrice(newPrice);
				offer.setOldPrice(oldPrice);
				offer.setDescription(description);
				
				int addressId = resultSet.getInt("ADDRESS_ID");
				String postalCode = resultSet.getString("POSTAL_CODE");
				String formattedAddress = resultSet.getString("FORMATTED_ADDESS");
				String country = resultSet.getString("COUNTREY");
				String phone = resultSet.getString("PHONE_NUMBER");
				double lat = resultSet.getDouble("LATTITUDE");
				double lon = resultSet.getDouble("LONGTIUDE");
				
				Address address = new Address();
				address.setAddressId(addressId);
				address.setPostalCode(postalCode);
				address.setAddress(formattedAddress);
				address.setCountry(country);
				address.setPhone(phone);
				address.setPostalCode(postalCode);
				address.setLatitude(lat);
				address.setLongitude(lon);
				address.setMealId(mealId);
				
				offer.setAddress(address);
				
				offers.add(offer);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offers;
	}

}
