package com.communitycoupons.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/")
public class CommunityCouponsRESTService {
	@POST
	@Path("/communityCouponsService")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
 
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "RESTService Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
	
	@GET
	@Path("/getCouponData")
	@Context
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCouponDataRESTService(InputStream incomingData, @Context ServletContext context) {
		String string = "";
		String result = "Community Coupons - REST Service Successfully started..";
		
		InputStream crunchifyInputStream;
		try {
			//String jsonFile = context.getRealPath("/WEB-INF/jsonPacket/CommunityCouponsJSON.txt");
			//String jsonFile = context.getRealPath("/WEB-INF/jsonPacket/CommunityCouponsJSON.txt");
			
			crunchifyInputStream = context.getResourceAsStream("/WEB-INF/classes/CommunityCouponsJSON.txt");
			//File file = new File(jsonFile);
			//crunchifyInputStream = new FileInputStream(file);
			//System.out.println("Context path: - " + file.getPath());
			//System.out.println("file exists - "+ file.exists());
		
		InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
		BufferedReader br = new BufferedReader(crunchifyReader);
		String line;
		while ((line = br.readLine()) != null) {
			string += line + "\n";
		}

		JSONObject jsonObject = new JSONObject(string);
		
		result = jsonObject.toString();
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Error!!!!";
		}

 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
}
