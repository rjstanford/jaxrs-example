package com.example;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.skife.jdbi.v2.DBI;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class DatabaseUtil {
	
	public static DBI dbi;

	static {
		try {
			final URI dbUri = new URI(System.getenv("DATABASE_URL"));
			final String username = dbUri.getUserInfo().split(":")[0];
			final String password = dbUri.getUserInfo().split(":")[1];
			final BoneCPConfig dbConfig = new BoneCPConfig();
			dbConfig.setJdbcUrl("jdbc:postgresql://" + dbUri.getHost() + "/" + StringUtils.substringAfter(dbUri.getPath(), "/"));
			dbConfig.setUsername(username);
			dbConfig.setPassword(password);
			dbConfig.setMinConnectionsPerPartition(5);
			dbConfig.setMaxConnectionsPerPartition(50);
			dbConfig.setConnectionTimeoutInMs(15000);
			dbConfig.setPartitionCount(1);
			dbConfig.setDefaultReadOnly(false);
			dbConfig.setDefaultTransactionIsolation("READ_COMMITTED");
			final BoneCPDataSource ds = new BoneCPDataSource(dbConfig);
			dbi = new DBI(ds);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
}
