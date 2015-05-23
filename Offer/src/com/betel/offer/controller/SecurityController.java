/**
 * SecurityController.java
 * java Servlet class to read(select) and write(insert,create) business users. if user is registered and provide correct
 * username and email allows business users to to login.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.betel.offer.data.DataManager;
import com.betel.offer.model.LoginDetailsDto;
import com.betel.offer.model.Offer;
import com.betel.offer.model.User;

@WebServlet("/login")
public class SecurityController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4797439017489379367L;

	@Override
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> map = new HashMap<>();
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		ObjectMapper mapper = new ObjectMapper();
		LoginDetailsDto login = mapper.readValue(sb.toString(), LoginDetailsDto.class);
		
		DataManager dataManager = new DataManager();
		User user = dataManager.getUserByEmail(login.geteMail());
		if (user != null && login.getPassword().equals(user.getPassword())) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			Cookie cookie = new Cookie("userid", String.valueOf(user.getUserId()));
			cookie.setMaxAge(36000);
			
			response.addCookie(cookie);
			
			map.put("success", true);
			map.put("fName", user.getFirstName());
			map.put("lName", user.getLastName());
			map.put("userId", user.getUserId());

		} else {
			map.put("success", false);
		}

		mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		session.setAttribute("user", null);
		
		Cookie cookie = new Cookie("userid", null);
		cookie.setMaxAge(0);
		
		response.addCookie(cookie);
		
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);

	}
	
	

}
