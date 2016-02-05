package org.springframework.cloud.servicebroker.postgresql.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.postgresql.exception.PostgresqlServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Utility class for manipulating a Mongo database.
 * 
 * @author sgreenberg@pivotal.io
 *
 */
@Service
public class PostgresqlAdminService {

    @Value("${EXTERNAL_URL}")
    private String jdbcUrl;

	public static final String ADMIN_DB = "admin";
	
	private Logger logger = LoggerFactory.getLogger(PostgresqlAdminService.class);
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
    public PostgresqlAdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }	
	
	public boolean databaseExists(String databaseName) throws PostgresqlServiceException {
	    try {
			List<String> databases = jdbcTemplate.queryForList("select datname from pg_database where datname LIKE  '"+databaseName+"'", String.class);
			return databases != null && databases.size() > 0;
		} catch (DataAccessException e) {
			throw handleException(e);
		}
	}
	
	public void deleteDatabase(String databaseName) throws PostgresqlServiceException {
		try{
		    jdbcTemplate.execute("DROP DATABASE IF EXISTS \""+databaseName+"\"");
		} catch (DataAccessException e) {
			throw handleException(e);
		}
	}
	
	public void createDatabase(String databaseName) throws PostgresqlServiceException {
		try {
		    jdbcTemplate.execute("CREATE DATABASE \""+databaseName+"\"  ENCODING 'UTF8'");
		    jdbcTemplate.execute("REVOKE all on database \"" + databaseName + "\" from public");

		} catch (DataAccessException e) {
			// try to clean up and fail
			try {
				deleteDatabase(databaseName);
			} catch (PostgresqlServiceException ignore) {}
			throw handleException(e);
		}
	}
	
	public void createUser(String database, String username, String password) throws PostgresqlServiceException {
		try {
		    jdbcTemplate.execute("CREATE USER \""+username+"\" WITH PASSWORD '"+password+"'");
		    jdbcTemplate.execute("GRANT ALL  ON DATABASE \""+database+"\" TO \""+username+"\"");
//		    jdbcTemplate.execute("FLUSH PRIVILEGES");
		} catch (DataAccessException e) {
			throw handleException(e);
		}
	}
	
	public void deleteUser(String database, String username) throws PostgresqlServiceException {
		try {
			jdbcTemplate.execute("DROP USER \""+username+"\"");
		} catch (DataAccessException e) {
			throw handleException(e);
		}
	}
	
	public String getConnectionString(String database, String username, String password) {
		return new StringBuilder()
				.append("postgres://")
				.append(username)
				.append(":")
				.append(password)
				.append("@")
				.append(getServerAddresses())
				.append("/")
				.append(database)
				.toString();
	}
	
	public String getServerAddresses()  {
		return jdbcUrl;
	}
	
	private PostgresqlServiceException handleException(Exception e) {
		logger.warn(e.getLocalizedMessage(), e);
		return new PostgresqlServiceException(e.getLocalizedMessage());
	}
	
}
