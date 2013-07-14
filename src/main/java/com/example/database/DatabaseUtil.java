package com.example.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.apache.commons.lang.StringUtils;
import org.skife.jdbi.v2.DBI;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class DatabaseUtil {
	
	public static DBI dbi;

	static {
		try {
			final BoneCPDataSource ds = getDatasource();
			updateDDL(ds.getConnection());
			dbi = new DBI(ds);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	static void updateDDL(Connection connection) throws LiquibaseException, SQLException {
		Liquibase liquibase = null;
		try {
	        DatabaseConnection databaseConnection = new JdbcConnection(connection);
	        String packageName = new DatabaseUtil().getPackageName();
	       	liquibase = new Liquibase(packageName + "/db.changelog-master.xml", new ClassLoaderResourceAccessor() , databaseConnection);
	       	liquibase.update(null);
        } finally {
            if (liquibase != null) {
                liquibase.forceReleaseLocks();
            }
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
        }
	}
	
	static BoneCPDataSource getDatasource() throws URISyntaxException {
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
		return new BoneCPDataSource(dbConfig);
	}
	
	private String getPackageName() {
		return this.getClass().getPackage().getName().replaceAll("\\.", "/");
	}
}
