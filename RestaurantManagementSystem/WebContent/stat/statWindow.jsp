<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<%@ page import="com.rms.util.*" %>
<%@ page import="com.rms.db.*" %>
<html>
<head>
    <title>Restaurant Report Management</title>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type" />
   <%@ page  language="java" import="java.sql.*"  %>
       <script src="../scripts/boot.js" type="text/javascript"></script>
        <script src="../scripts/miniui/locale/en_US.js" type="text/javascript"></script>
    <style type="text/css">
    html,html body
    {
        font-family:Microsoft YaHei;
        font-size:13px;
    }
    h2
    {
        font-family: "Trebuchet MS",Arial,sans-serif;
    }
    p
    {
        line-height:22px;
    }
    </style>
</head>
<body>
  <h1>Restaurant Report Management</h1>      

  
 <!--   <div id="datagrid1" class="mini-datagrid" style="width:800px;height:380px;" allowResize="true"
        url="../StatCrudServlet?method=search"  pageSize="20"  idField="id" multiSelect="true" 
 >  --> 
        <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
           <% 
              String begindate = request.getParameter("begindate");
          
              String enddate = request.getParameter("enddate");
              Coon coon=new Coon();
    		 Connection conn = coon.getCoon();
     		String sql = "call stat('"+begindate+"','"+enddate+"')";
     //åå»ºä¸ä¸ªCallableStatement å¯¹è±¡æ¥è°ç¨æ°æ®åºå­å¨è¿ç¨
     //CallableStatement comm = conn.divpareCall(sql);
     Statement stmt = conn.createStatement();
       
     ResultSet res1 = stmt.executeQuery(sql);
     String str="";
     while(res1.next()){
      	str = res1.getString("res");
     }
     if(str==null||str.length()==0)
     {
    	 str="0";
     }
     out.println("from:"+begindate+" to "+enddate+" ,total price:"+str);
     
              %>
        </div>
    </div>
    
 
 
 
   
    <script type="text/javascript">

    
    
    </script>

   
        
    </div>  
</body>
</html>
