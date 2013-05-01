package com.example;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.postgresql.ds.PGPoolingDataSource;
import org.skife.jdbi.v2.DBI;

public class DatabaseUtil {
	
	public static DBI dbi;

	static {
		try {
			URI dbUri = new URI(System.getenv("DATABASE_URL"));
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];

			PGPoolingDataSource source = new PGPoolingDataSource();
			source.setServerName(dbUri.getHost());
			source.setDatabaseName(StringUtils.substringAfter(dbUri.getPath(), "/"));
			source.setUser(username);
			source.setPassword(password);
			source.setMaxConnections(50);
			
			dbi = new DBI(source);			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
}
