<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<%@ page import="com.rms.util.*" %>
<%@ page import="com.rms.db.*" %>
<%@ page import="com.rms.calender.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <script src="Calender.js"></script>
    <title>Week Report</title>
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
  <h2>Week Report</h2>      

  
 <!--   <div id="datagrid1" class="mini-datagrid" style="width:800px;height:380px;" allowResize="true"
        url="../StatCrudServlet?method=search"  pageSize="20"  idField="id" multiSelect="true" 
 >  --> 
        <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
            
            <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
           <form action="../stat/statWindow_week.jsp" method="get" >
           Input Month: <input name="inputweek" id="inputweek" type="week" value="2017-W30"/>            
           <br />
           <br />
            <input type="submit"  value="Report" /> 
            
            </form>
        </div>
        
           <% 
             String mn = request.getParameter("inputweek");
             String a=mn+"";
		     myDate my=new myDate();
		     String res = my.compute(new String(a));
		     String[] resarr=res.split(",");
		     if(resarr.length ==2 ){
		     String begindate=resarr[0];
		     String enddate=resarr[1];
             Coon coon=new Coon();
    		 Connection conn = coon.getCoon();
     		 String sql = "call stat('"+begindate+"','"+enddate+"')";
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
     		if(mn!=null && mn.length()!=0){
     			out.println("Month From: "+begindate+" To: "+enddate+"\t   Total Price:"+str);
    		}	
		     }
              %>
        </div>
    </div>
    <script type="text/javascript">
 
    </script>
    
    </div>  
</body>
</html>
