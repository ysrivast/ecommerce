package com.test.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.test.conn.MySQLConnUtil;
import com.test.model.UserAccount;
import com.test.utils.DBUtils;
import com.test.utils.MyUtils;

@WebFilter(filterName="cookieFilter" ,urlPatterns={"/*"})
public class CookieFilter implements Filter {
	public CookieFilter(){
		
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("In Cookie filter");
		HttpServletRequest req= (HttpServletRequest) request;
		HttpSession session= req.getSession();
		UserAccount userInSession= MyUtils.getLoginedUser(session);
		if(userInSession!=null){
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
			chain.doFilter(request, response);
			return;
		}
		
		Connection con=null;
		try {
			con= MySQLConnUtil.getDBConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String checked = (String) session.getAttribute("COOKIE_CHECKED");
		UserAccount user =null;
		if(checked==null && con!=null){
			String userName= MyUtils.getUserNameInCookie(req);
			try {
				user= DBUtils.findUser(con, userName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			MyUtils.storeLoginedUser(session, user);
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
		}
		
		chain.doFilter(request, response);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
