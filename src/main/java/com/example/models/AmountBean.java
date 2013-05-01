package com.example.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.example.models.CounterDAO.WithAmount;

@XmlRootElement
public class AmountBean implements WithAmount {

	private int amount;
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public static class Mapper implements ResultSetMapper<AmountBean>  {
		public AmountBean map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			AmountBean bean = new AmountBean();
			bean.amount = r.getInt("curval");
			return bean;
		}
	}

}

