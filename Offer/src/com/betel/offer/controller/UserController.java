/**
 * UserController.java
 * java Servlet class to read(select), write(insert,create) or delete users or user.
 * version:
 * date: 22/05/1015 
 * @author Betel Samson Tadesse
 * x14117649
 * 
 * 
 */
package com.betel.offer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.betel.offer.data.DataManager;
import com.betel.offer.model.Offer;
import com.betel.offer.model.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/User")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/offerdb";

	static final String USER = "root";
	static final String PASS = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		Map<String, Object> map = new HashMap<>();
		DataManager dataManager = new DataManager();
		List<User> users = null;
		User user = null;

		String userId = request.getParameter("id");
		Integer id = userId != null ? Integer.parseInt(userId) : 0;

		if (id == 0) {
			users = dataManager.getUsers();
			map.put("results", users);
		} else {
			user = dataManager.getUser(id);
			map.put("result", user);
		}

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		int id = Integer.parseInt(userId);

		DataManager dataManager = new DataManager();
		Map<String, Object> map = dataManager.deleteUser(id);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}

		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(sb.toString(), User.class);

		DataManager dataManager = new DataManager();
		Map<String, Object> map = null;

		if (user.getUserId() == 0) {
			map = dataManager.addUser(user);
		} else {
			map = dataManager.updateUser(user);
		}

		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}

}
