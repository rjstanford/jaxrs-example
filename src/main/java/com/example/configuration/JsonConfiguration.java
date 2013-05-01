package com.example.configuration;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
@Produces("application/json")
public class JsonConfiguration implements ContextResolver<ObjectMapper> {

	private ObjectMapper objectMapper;

	public JsonConfiguration() throws Exception {
		this.objectMapper = new ObjectMapper()
			.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
			.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
			.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.setSerializationInclusion(Include.NON_NULL)
			;
	}

	public ObjectMapper getContext(Class<?> type) {
		return objectMapper;
	}
	
}
