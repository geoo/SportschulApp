package de.sportschulApp.server.databanker;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBankerConnection {



	private Connection con = null;
	private String dbUrl = "jdbc:mysql://127.0.0.1/Sportschule";
	private String pwd = "root";



	Statement stmt = null;
	private String username = "root";

	public DataBankerConnection(){

	}

	public void close() throws SQLException {
		con.close();
	}

	public void closeStatement() throws SQLException{
		stmt.close();
	}

	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(dbUrl, username, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public Statement getStatement() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(dbUrl, username, pwd);
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stmt;
	}

}
