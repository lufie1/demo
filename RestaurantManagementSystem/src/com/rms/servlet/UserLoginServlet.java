package com.rms.servlet;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rms.db.DBTool;
import com.rms.model.User;



/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 DBTool dbtool=new DBTool();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String result = dbtool.checkUser(username, password,request);

		if (result.equals("User name and password are correct")) {
			HttpSession session = request.getSession();
			User userInfomation = (User) request.getAttribute("userInfomation");
			session.setAttribute("userinfo", userInfomation);
			System.out.println("用户名和密码均正确");

		} else if (result.equals("The username is correct and the password is incorrect")) {

			System.out.println("用户名正确,密码不正确");

		} else if (result.equals("Without this user！")) {

			System.out.println("没有此用户");

		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toCharArray());
	}

}
