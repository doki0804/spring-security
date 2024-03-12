package com.example.springsecurity.security.utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonResultUtil {
	
	public static String getJsonResult(String status, List<?> list){
		
		String errorCd = "000";
		String message = "성공";
		
		if(status == "false") {
			errorCd = "400";
			message = "실패";
		}
		
    	JsonObject jo = new JsonObject();
		jo.addProperty("status", status);
		jo.addProperty("errorCd", errorCd);
		jo.addProperty("message", message);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(list);
		@SuppressWarnings("deprecation")
		JsonElement resultData = new JsonParser().parse(jsonString);
		
		jo.add("resultData", resultData);
    	
        return jo.toString();
	}

	public static String getJsonResult(String status, String errorCd, String message, String data){
		JsonObject jo = new JsonObject();
		jo.addProperty("status", status);
		jo.addProperty("errorCd", errorCd);
		jo.addProperty("message", message);
		jo.addProperty("resultData", data);
		return jo.toString();
	}

	public static String getJsonResult(String status, String errorCd, String message){
		JsonObject jo = new JsonObject();
		jo.addProperty("status", status);
		jo.addProperty("errorCd", errorCd);
		jo.addProperty("message", message);
		return jo.toString();
	}

}
