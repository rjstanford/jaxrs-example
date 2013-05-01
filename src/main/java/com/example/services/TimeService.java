package com.example.services;

import java.util.Arrays;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import com.example.models.Time;
import com.example.models.TimezoneObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/time")
@Api(value = "/time", description = "Time Utilities")
@Produces(MediaType.APPLICATION_JSON)
public class TimeService {

	@GET
	@ApiOperation(value = "Get the current local time", notes = "Uses the server's timezone", responseClass = "com.example.models.Time")
	public Time get() {
		return new Time();
	}

	@GET
	@Path("/timezone/{timezone}")
	@ApiOperation(value = "Get the time in any timezone", notes = "Exepects timezone on the path - for exmaple enter CST", responseClass = "com.example.models.Time")
	@ApiErrors(value = { @ApiError(code = 400, reason = "Invalid timezone supplied"), @ApiError(code = 404, reason = "Timezone is required") })
	public Time get(@PathParam("timezone") String timezone) {
		return getTimeForTimezone(timezone);
	}

	@GET
	@Path("/getWithParameter")
	@ApiOperation(value = "Get the time in any timezone", notes = "Exepects timezone on the path - for exmaple enter CST", responseClass = "com.example.models.Time")
	@ApiErrors(value = { @ApiError(code = 400, reason = "Invalid timezone supplied"), @ApiError(code = 404, reason = "Timezone is required") })
	public Time getWithParameter(@QueryParam("timezone") String timezone) {
		return getTimeForTimezone(timezone);
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the time in any timezone", notes = "Expects an object like { timezone : 'CST' }", responseClass = "com.example.models.Time")
	@ApiErrors(value = { @ApiError(code = 400, reason = "Invalid timezone supplied"), @ApiError(code = 404, reason = "Timezone is required") })
	public Time post(@ApiParam(value = "Timezone object", required = true) TimezoneObject timezone) {
		return getTimeForTimezone(timezone == null ? null : timezone.getTimezone());
	}

	private Time getTimeForTimezone(String timezone) {
		if (StringUtils.isBlank(timezone)) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity("Timezone is required").build());
		}
		timezone = timezone.toUpperCase();
		if (!Arrays.asList(TimeZone.getAvailableIDs()).contains(timezone)) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Couldn't understand timezone: " + timezone).build());
		}
		return new Time(TimeZone.getTimeZone(timezone));
	}

}
