package com.test.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletRequest;

import com.test.model.UserAccount;

public class DBUtils {

	public static UserAccount findUser(Connection con, String userName,
			String password) throws SQLException {
		String sql= "select USER_NAME, PASSWORD, GENDER from USER_PRODUCT where USER_NAME=? and PASSWORD=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			String gender= rs.getString("GENDER");
			UserAccount user = new UserAccount();
			user.setGender(gender);
			user.setPassword(password);
			user.setUserName(userName);
			return user;
		}
		
		return null;
	}

	public static UserAccount findUser(Connection con, String userName) throws SQLException {
		String sql = "select USER_NAME, PASSWORD, GENDER from USER_PRODUCT where USER_NAME=? ";
		PreparedStatement pstmt= con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			String password= rs.getString("USER_NAME");
			String gender= rs.getString("GENDER");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setGender(gender);
			user.setPassword(password);
			return user;
		}
		
		return null;
	}
	
	
    
}
