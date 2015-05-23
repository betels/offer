/**
 * ImageController.java
 * java Servlet class to upload(post) images. uploaded images must be JPG,PNG,JPEG...
 * version:
 * date: 22/05/1015 
 * @author Betel Samson Tadesse
 * x14117649
 * 
 * 
 */
package com.betel.offer.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;

@WebServlet("/image")
public class ImageController extends HttpServlet {

	private static final Properties prop = new Properties();
	
	@Override
	public void init() throws ServletException {
		String filename = "config.properties";
		InputStream input = ImageController.class.getClassLoader().getResourceAsStream(filename);
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.init();
	}

	private static final long serialVersionUID = 4353075746524908291L;
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		Map<String, Object> map = new HashMap<String, Object>();

		
		try {
			DiskFileItemFactory discFactory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(discFactory);
			servletFileUpload.setFileSizeMax(1024 * 1024 * 40);

			// Only for debugging purpose
			System.out.println("================================================");
			System.out.println("cur dir: " + System.getProperty("user.dir"));
			System.out.println("image dir: " + prop.getProperty("image_dir"));
			System.out.println("================================================");
			
			/**
			 * @ reference :JSP/Servlet?, H. (2015). How to upload files to server using JSP/Servlet?. [online] Stackoverflow.com. Available at: http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet .
			 */
			
			List<FileItem> items = servletFileUpload.parseRequest(request);
	
			for (FileItem item : items) {
				String fullFileName = item.getName();
				String fileName = fullFileName.substring(0,fullFileName.indexOf("."));
				String fileExt = fullFileName.substring(fullFileName.indexOf(".") + 1);
				if(fileExt != null && (fileExt.equalsIgnoreCase("jpeg") || fileExt.equalsIgnoreCase("png") || fileExt.equalsIgnoreCase("jpg"))){
					String uniqueName = fileName + "_" + UUID.randomUUID() + "." + fileExt;
					File dir = new File(prop.getProperty("image_dir") + "/" + uniqueName);
					item.write(dir);
					map.put("fileName", uniqueName);
					map.put("sucess", true);					
				}else{
					map.put("errormessage", "File type not supported. Must be image file. jpeg, png, or jpg");
					map.put("sucess", false);						
				}
			}
			
		} catch (Exception e) {
			map.put("sucess", false);
			map.put("errormessage", "File Upload Failed");
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		PrintWriter out = response.getWriter();
		out.println(json);
	}

}
