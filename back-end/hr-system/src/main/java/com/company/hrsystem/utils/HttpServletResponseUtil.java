package com.company.hrsystem.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

public class HttpServletResponseUtil {

	public static void ServletResponse(HttpServletResponse response, Object obj) throws IOException {
		String toJson = new Gson().toJson(obj);
		PrintWriter out = response.getWriter();
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		out.print(toJson);
		out.flush();
	}
	
}
