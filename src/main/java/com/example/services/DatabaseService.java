package com.example.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.util.LongMapper;

import com.example.database.DatabaseUtil;
import com.example.models.AmountBean;
import com.example.models.CounterDAO;
import com.sun.jersey.spi.resource.Singleton;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/database")
@Singleton
@Api(value = "/database", description = "Oracle Tutorial")
@Produces("text/plain")
public class DatabaseService {

	@GET
	@Path("/dao")
	@ApiOperation(value = "Get it using the dao")
	public String getDao() {
		CounterDAO dao = DatabaseUtil.dbi.onDemand(CounterDAO.class);
		dao.increment();
		return "Got it: " + dao.retrieve();
	}

	@GET
	@Path("/txn")
	@ApiOperation(value = "Get it with a transaction")
	public String getTransaction() {
		Long l = DatabaseUtil.dbi.inTransaction(new TransactionCallback<Long>() {
			public Long inTransaction(Handle h, TransactionStatus status) {
				h.execute("update counter set curval = curval + 1");
				return h.createQuery("select curval from counter").map(LongMapper.FIRST).first();
			}
		});
		return "Got it in a transaction: " + l;
	}	
	
	@GET
	@Path("/handle")
	@ApiOperation(value = "Get it with a handle callback")
	public String getHandle() {
		AmountBean amountBean = DatabaseUtil.dbi.withHandle(new HandleCallback<AmountBean>() {
			public AmountBean withHandle(Handle h) {
				h.execute("update counter set curval = curval + 1");
				return h.createQuery("select curval as amount from counter").map(AmountBean.class).first();
			}
		});
		return "Got it with a handle: " + amountBean.getAmount();
	}

	@PUT
	@Consumes("application/json")
	@ApiOperation(value = "Set counter with an object", notes = "Expects an object like { amount : '123' }")
	public synchronized String setStatus(@ApiParam(value = "New schedule data", required = true) AmountBean amountBean) {
		CounterDAO dao = DatabaseUtil.dbi.onDemand(CounterDAO.class);
		dao.setFromObject(amountBean);
		return "Got it: " + dao.retrieve();
	}

	@GET
	@Path("/daoObject")
	@ApiOperation(value = "Get it using the dao into an object")
	public String getDaoObject() {
		CounterDAO dao = DatabaseUtil.dbi.onDemand(CounterDAO.class);
		return "Got it: " + dao.getToObject().getAmount();
	}

	public static class Result {
		public boolean success;
	}

}
