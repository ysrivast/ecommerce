package com.test.conn;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class MySQLConnUtil {
	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String dbUrl = "jdbc:mysql://localhost:3306/test";
	private static final String dbUser = "root";
	private static final String dbPassword = "root";

	public static Connection getDBConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(driverName);
		Connection con = (Connection) DriverManager.getConnection(dbUrl,
				dbUser, dbPassword);
		return con;

	}
	
	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollbackConnection(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
