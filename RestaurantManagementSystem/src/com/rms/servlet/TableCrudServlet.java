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
 * Servlet implementation class UserCrudServlet
 */
@WebServlet("/TableCrudServlet")
public class TableCrudServlet extends HttpServlet {
	
	
	public DBTool dbtool=new DBTool();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TableCrudServlet() {
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
		 String methodName = request.getParameter("method");

		 if(methodName.equalsIgnoreCase("search"))
		 {
			 search (request,response);
		 }
		 else if(methodName.equalsIgnoreCase("remove"))
		 {
			 remove (request,response);
		 }
		 else if(methodName.equalsIgnoreCase("save"))
		 {
			 save (request,response);
		 }
		 else if(methodName.equalsIgnoreCase("get"))
		 {
			 get (request,response);
		 }
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		 //查询条件
	    String key = request.getParameter("key");
	    //分页
	    
	    int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
	    int pageSize = Integer.parseInt(request.getParameter("pageSize"));        
		
	    //字段排序
	    String sortField = request.getParameter("sortField");
	    String sortOrder = request.getParameter("sortOrder");
	    
	    HashMap result;
	    
	    
		try {
			result = dbtool.SearchTable(key, pageIndex, pageSize, sortField, sortOrder);
			String json = JSON.Encode(result);
		    response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}

	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		    String json = request.getParameter("data");
		    ArrayList rows = (ArrayList)JSON.Decode(json);

		    for(int i=0,l=rows.size(); i<l; i++){
		    	HashMap row = (HashMap)rows.get(i);
		  		  
				String id = row.get("id") != null ? row.get("id").toString() : "";
		        String state = row.get("_state") != null ? row.get("_state").toString() : "";
		        if(state.equals("added") || id.equals(""))	//新增：id为空，或_state为added
		        {
		            dbtool.insertTable(row);
		        }
		        else if (state.equals("removed") || state.equals("deleted"))
		        {
		            dbtool.deleteTable(id);
		        }
		        else  if(state.equals("modified") || state.equals(""))	//更新：_state为空，或modified
		        {
		            dbtool.updateTable(row);
		        }
		    }
		        
		
	}

	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		 String id = request.getParameter("id");
		
		 
		    HashMap table;
			try {
				 dbtool.deleteTable(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().write(e.toString());
			}
		   
		
	}

	protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		   String id = request.getParameter("id");
	
		
		 
		    HashMap table;
			try {
				table = dbtool.GetTable(id);
				String json = JSON.Encode(table);
				response.getWriter().write(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().write(e.toString());
			}
	}
	
	
}
