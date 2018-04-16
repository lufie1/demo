package com.rms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rms.db.DBTool;
import com.rms.util.JSON;

/**
 * Servlet implementation class GetCatagoryServlet
 */
@WebServlet("/GetMenuServlet")
public class GetMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DBTool dbtool=new DBTool();    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		String type = request.getParameter("type");
		 //根据方法名称确定调用哪一个方法
		
		ArrayList<HashMap<String, String>>  result;
		    
		    
			try {
				result = dbtool.GetFreeTable();
				String json = JSON.Encode(result);
			    response.getWriter().write(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
