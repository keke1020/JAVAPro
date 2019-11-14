package com.mp.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mp.dto.system;

public class sessionFilter implements Filter {
//private static final Logger logger = Logger.getLogger(MyFilter.class);
	protected FilterConfig filterConfig;

	public void destroy() {
		System.out.println("销毁过滤器方法");
//		logger.info("销毁过滤器方法");
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String isLogin = "";

//		Object user = request.getSession().getAttribute(system.usr);
//		System.out.println("过滤中");
//		String urlString = request.getRequestURI();
//		if (urlString.endsWith("index")) {
//		} else {
//			chain.doFilter(request, response);
//		}
//		if (user == null) {
//			response.sendRedirect("/Manpow/index");
//		} else {
//			chain.doFilter(request, response);
//		}

		String loginUrl = request.getContextPath() + "/index";

//		String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();

		try {
			isLogin = (String) session.getAttribute(system.usr);
//			if (isLogin != null) {
//				System.out.println("在SignonFilter中验证通过");
//				chain.doFilter(req, res);
//			} else {
//				response.sendRedirect("/Manpow/index");
//			}
			if (request.getRequestURI().endsWith("login") && isLogin == null) {
				String str = "<script language='javascript'>" + "window.top.location.href='" + loginUrl + "';</script>";
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(str);
				writer.flush();
				return;
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFilterConfig(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void init(FilterConfig config) throws ServletException {
		System.out.println("初始化过滤器的方法");
//		logger.info("初始化过滤器的方法");
		this.filterConfig = config;
	}
}
