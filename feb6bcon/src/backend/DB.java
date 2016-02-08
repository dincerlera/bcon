package backend;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DB {
	
	private static Connection con = null;
	private static Context initContext = null;
	private static DataSource ds = null;
	
	private DB () {
	}									//private constructor (deny "new")
	
	
	protected static DB getDB (){
		DB db = new DB();
		return db;						//single object
	}
	
	protected Connection connect () throws Exception {
		initContext = new InitialContext();
		ds   = (DataSource)initContext.lookup("java:/comp/env/jdbc/database");
		con = ds.getConnection();
		return con;
	}
	
	protected void disconnect () throws Exception {
		if(con != null) 
			con.close();
	}

	
	
	

}
