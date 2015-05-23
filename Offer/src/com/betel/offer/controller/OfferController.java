/**
 * OfferController.java
 * java Servlet class to read(select), write(insert,create) or delete offers or offer.
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.betel.offer.data.DataManager;
import com.betel.offer.model.Offer;
import com.betel.offer.model.User;

/**
 * Servlet implementation class Offer
 */
@WebServlet("/Offer")
public class OfferController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OfferController() {
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
		List<Offer> offers = null;
		Offer offer = null;
		
		String mealIdString = request.getParameter("id");
		Integer mealId = mealIdString != null ? Integer.parseInt(mealIdString) : 0;
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		
		if(mealId == 0 && currentUser ==null){// For admin level access - not implemented yet
			offers = dataManager.getOffers();
			map.put("results", offers);
		}else if(mealId == 0 && currentUser !=null){
			offers =  dataManager.getOffersByUserId(currentUser.getUserId());
			map.put("results", offers);
		}else{
			offer =  dataManager.getOffer(mealId);
			map.put("result", offer);
		}

		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		mapper.setDateFormat(df);
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);

	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		String mealId = request.getParameter("mealId");
		int id = Integer.parseInt(mealId);

		DataManager dataManager = new DataManager();
		Map<String, Object> map = dataManager.deleteOffer(id);

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
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");// user was set in session when logged in
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
	
		ObjectMapper mapper = new ObjectMapper();
		Offer offer = mapper.readValue(sb.toString(), Offer.class);
	
		DataManager dataManager = new DataManager();
		Map<String, Object> map = null;

		if (offer.getMealId() == 0) {
			map = dataManager.addOffer(offer, currentUser);
		} else {
			map = dataManager.updateOffer(offer);
		}
		
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}

}
