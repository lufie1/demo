package com.rms.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class SignOnFilter
 */
@WebFilter(filterName="/SignOnFilter",urlPatterns="/mainindex.jsp")
public class SignOnFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SignOnFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest hreq=(HttpServletRequest) request;
		HttpServletResponse hrsp=(HttpServletResponse) response;
        HttpSession session=hreq.getSession();
        if(session!=null&&session.getAttribute("userinfo")!=null)
        {
        	System.out.println("exist login session");
        	// pass the request along the filter chain
    		chain.doFilter(request, response);
        }else
        {
        	System.out.println("not exist login session");
        	hrsp.sendRedirect("index.html");  
        }
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
