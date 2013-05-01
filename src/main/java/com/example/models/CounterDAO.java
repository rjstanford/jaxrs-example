package com.example.models;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface CounterDAO {

	public static interface WithAmount {
		public int amount = 0;
	}
	
	@SqlUpdate("update counter set curval = curval + 1")
	void increment();

	@SqlUpdate("update counter set curval = curval + :amount")
	void increment(@Bind("amount") int amount);
	
	@SqlUpdate("update counter set curval = :amount")
	void setFromObject(@BindBean AmountBean counter);

	@Mapper(AmountBean.Mapper.class)
	@SqlQuery("select curval from counter")
	AmountBean getToObject();
	
	@SqlUpdate("select am")
	
	@SqlQuery("select curval from counter where 1 = 1")
	Long retrieve();

	void close();
}
