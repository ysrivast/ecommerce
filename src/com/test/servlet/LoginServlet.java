package com.test.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.conn.MySQLConnUtil;
import com.test.model.UserAccount;
import com.test.utils.DBUtils;
import com.test.utils.MyUtils;

@WebFilter(urlPatterns = { "/login" })
public class LoginServlet extends HomeServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/WEB_INF/view/loginView.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String remembetMeStr = req.getParameter("rememberMe");
		boolean remember = "Y".equalsIgnoreCase(remembetMeStr);

		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;

		if (userName == null || password == null || userName.length() == 0
				|| password.length() == 0) {

			hasError = true;
			errorString = "Require Username and password!!!";
		}

		else {
			Connection con = null;

			try {
				con = MySQLConnUtil.getDBConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user = DBUtils.findUser(con, userName, password);
				if (user == null) {
					hasError = true;
					errorString = "User Name or password invalid";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
			
			if(hasError){
				user = new UserAccount();
				user.setUserName(userName);
				user.setPassword(password);
				req.setAttribute("errorString", errorString);
				req.setAttribute("user", user);
				RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/view/loginView.jsp");
				dispatcher.forward(req, resp);
			}
			else{
				HttpSession session= req.getSession();
				MyUtils.storeLoginedUser(session, user);
				if(remember){
					MyUtils.storeUserCookie(resp, user);
				}
				else{
					MyUtils.deleteUserCookie(resp);
				}
			}
				
			resp.sendRedirect(req.getContextPath()+ "/UserInfo");
		}

	}
}
