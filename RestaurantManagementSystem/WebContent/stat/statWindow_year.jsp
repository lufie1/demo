<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<%@ page import="com.rms.util.*" %>
<%@ page import="com.rms.db.*" %>
<html>
<head>
    <title>Year Report</title>
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
  <h2>Year Report</h2>      

  
 <!--   <div id="datagrid1" class="mini-datagrid" style="width:800px;height:380px;" allowResize="true"
        url="../StatCrudServlet?method=search"  pageSize="20"  idField="id" multiSelect="true" 
 >  --> 
        <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
            
            <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
            <div id="tan">
            
            </div>
           
           <form action="../stat/statWindow_year.jsp" method="get" >
           Input Year: <select name="aa" id="year">
            	<option value="2016">2016</option>
                   </select>
                    <script>
            	for( var i = 2017;i<2020;i++){
            		document.getElementById("year").innerHTML += '<option value="'+i+'">'+i+'</option>';
            	}
            </script>
           <br />
           <br />
            <input type="submit" value="Report" /> 
            </form>
        </div>
        
           <% 
              String yr = request.getParameter("aa");
		      String begindate = yr+"-01-01";
		      String enddate = yr+"-12-31";
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
     if(yr!=null && yr.length()!=0){
         out.println("Year:"+yr+"\n   Total Price:"+str);
     }
              %>
        </div>
    </div>
    
 
 
 
   
    <script type="text/javascript">

    
    
    </script>

   
        
    </div>  
</body>
</html>
