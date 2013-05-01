package com.example.configuration;

import javax.servlet.http.HttpServlet;

import com.wordnik.swagger.jaxrs.JaxrsApiReader;

public class SwaggerBootstrap extends HttpServlet {

	static {
		JaxrsApiReader.setFormatString("");
	}

	private static final long serialVersionUID = 1L;
}