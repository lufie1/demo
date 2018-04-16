package com.rms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/LoginOutServlet")
public class LoginOutServlet extends HttpServlet{
	   /** 
     * @category 退出登录的Servlet,注销 
     * @author Bird 
     */  
    private static final long serialVersionUID = 1L;  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        HttpSession session = request.getSession();//防止创建Session  
        session.invalidate();
        response.sendRedirect("index.html");  
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
  
    }  
}
