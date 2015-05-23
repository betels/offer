/**
 * OfferControllerMobile.java
 * java Servlet class for mobile view to read(select) and write(insert,create) offers 
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.betel.offer.model.Coordinate;
import com.betel.offer.model.Offer;
import com.betel.offer.model.User;

@WebServlet("/publicOffers")
public class OfferControllerMobile extends HttpServlet {

	@Override
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
		Coordinate coordinate = mapper.readValue(sb.toString(),	Coordinate.class);

		DataManager dataManager = new DataManager();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Offer> offers = dataManager.getNearestOffers(coordinate);
		map.put("results", offers);

		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		String mealIdString = request.getParameter("id");
		Integer mealId = mealIdString != null ? Integer.parseInt(mealIdString): 0;

		DataManager dataManager = new DataManager();
		Offer offer = dataManager.getOffer(mealId);
		Map<String, Object> map = new HashMap<>();
		map.put("result", offer);

		ObjectMapper mapper = new ObjectMapper();
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		//mapper.setDateFormat(df);
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
