package com.test.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.test.conn.MySQLConnUtil;
import com.test.utils.MyUtils;

@WebFilter(filterName="jdbcFilter",urlPatterns={"/*"})
public class JDBCFilter implements Filter {
	public JDBCFilter() {
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

		HttpServletRequest req = (HttpServletRequest) request;
		if (needJDBC(req)) {
			System.out.println("Open db Connection for : "
					+ req.getServletPath());
			Connection con = null;
			try {
				con = MySQLConnUtil.getDBConnection();
				con.setAutoCommit(false);
				MyUtils.storeConnection(request, con);
				chain.doFilter(request, response);
				con.commit();
				
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
				MySQLConnUtil.rollbackConnection(con);
			}
			finally{
				MySQLConnUtil.closeConnection(con);
			}
			
		}
		else {
			chain.doFilter(request, response);
		}
	}

	public boolean needJDBC(HttpServletRequest request) {
		System.out.println("JDBC Filter");
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String urlPattern = servletPath;
		if (pathInfo != null) {
			urlPattern = servletPath + "/";
		}

		Map<String, ? extends ServletRegistration> servletRegistrations = request
				.getServletContext().getServletRegistrations();
		Set set= servletRegistrations.keySet();
		for(Object o : set){
			System.out.println(o +" | " + servletRegistrations.get(o));
		}
		
		Collection<? extends ServletRegistration> values = servletRegistrations
				.values();
		for (ServletRegistration sr : values) {
			Collection<String> mappings = sr.getMappings();
			if (mappings.contains(urlPattern))
				return true;
		}

		return false;
	}

}
