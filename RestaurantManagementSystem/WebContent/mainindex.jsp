<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Restaurant Management System (RMS)</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
       
    <script src="scripts/boot.js" type="text/javascript"></script>
     <script src="scripts/miniui/locale/en_US.js" type="text/javascript"></script>

    <script src="scripts/core.js" type="text/javascript"></script>

    <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }

    .logo
    {
        font-family:"微软雅黑",	"Helvetica Neue",​Helvetica,​Arial,​sans-serif;
        font-size:28px;font-weight:bold;color:#444;        
        cursor:default;
        position:absolute;top:28px;left:15px;        
        line-height:28px;
    }    
    .topNav
    {
        position:absolute;right:8px;top:10px;        
        font-size:12px;
        line-height:25px;
    }
    .topNav a
    {
        text-decoration:none;
        color:#222;
        font-weight:normal;
        font-size:12px;
        line-height:25px;
        margin-left:3px;
        margin-right:3px;
    }
    .topNav a:hover
    {
        text-decoration:underline;
        color:Blue;
    }   
     .mini-layout-region-south img
    {
        vertical-align:top;
    }
    </style>
</head>
<body>
<div class="mini-layout" style="width:100%;height:100%;">
    <div title="north" region="north" class="app-header" bodyStyle="overflow:hidden;" height="80" showHeader="false" showSplit="false">
        <div class="logo">Restaurant Management System (RMS)</div>


        <div class="topNav"> 

            Welcome:${sessionScope.userinfo.username }
            <a href="LoginOutServlet">Log Off</a>
        </div>


    </div>
    <div showHeader="false" region="south" style="border:0;text-align:center;" height="25" showSplit="false">
        Copyright © Restaurant Management System (RMS) 
    </div>
    <div region="west" title="Function Menu" showHeader="true" bodyStyle="padding-left:1px;" showSplitIcon="true" width="230" minWidth="100" maxWidth="350">
        <ul id="demoTree" class="mini-tree" showTreeIcon="true" style="width:100%;height:100%;"
             enableHotTrack="true" onbeforeexpand="onBeforeExpand" >
            <li><a href="overview.html" target="main">System Information Overview</a></li>
      <li>
			    <span expanded="true">Order Manage</span>
			    <ul>    
         
			         
                            <li><a href="order/order.html" target="main"> Order Management  </a></li>
                      

			    </ul>
		    </li>  

<%-- <%  User user=(User)session.getAttribute("userinfo");   

if(user.getRole().equalsIgnoreCase("Manager")){
%> --%>
            <li>
			    <span expanded="true">System Manage</span>
			    <ul>    
         
			                <li><a href="user/user.jsp" target="main">User Management</a></li>
			                <li><a href="menu/menu.jsp"  target="main">Menu Management </a></li>
			                <li><a href="menucategory/menucategory.jsp"  target="main">Menu Category Management </a></li>
                            <li><a href="table/table.jsp" target="main">Table Management </a></li>
                            <li><a href="stat/stat.html" target="main">Report Management </a></li>
              <!--              <li>
			            <span expanded="true">Report Management </span>	
			            <ul>
                            <li><a href="stat/stat.html" target="main">Common Report </a></li>
                            <li><a href="stat/statWindow_week.jsp" target="main">Week Report</a></li>
                            <li><a href="stat/statWindow_month.jsp" target="main">Month Report</a></li>
                            <li><a href="stat/statWindow_year.jsp" target="main">Year Report</a></li>
                          
                        </ul>             

		            </li> -->

			    </ul>
		    </li>  
		    
<%-- <%} %>	 --%>	  
 <%  User user=(User)session.getAttribute("userinfo");   

if(user.getRole().equalsIgnoreCase("Manager")){
%> 
            <li>
			    <span expanded="true">Database Manage</span>
			    <ul>    
         
			                <li><a href="databasebackup/databasebackup.jsp" target="main">Backup Database Management</a></li>
			            
			    </ul>
		    </li>  
		    
<%} %>	 -  
        </ul>        
    </div>
    <div title="center" region="center" style="border:0;">
        <div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" 
            onactivechanged="onTabsActiveChanged" 
        >
            <div title="MainFrame">
                <iframe onload="onIFrameLoad()" src="overview.html" id="mainframe" frameborder="0" name="main" style="width:100%;height:100%;" border="0"></iframe>
            </div>
        </div>        
    </div>

    
</div>



<!-- End Piwik Code -->

</body>
</html>
<script type="text/javascript">
    mini.parse();
    function onBeforeExpand(e) {
        var tree = e.sender;
        var nowNode = e.node;
        var level = tree.getLevel(nowNode);

        var root = tree.getRootNode();        
        tree.cascadeChild(root, function (node) {
            if (tree.isExpandedNode(node)) {
                var level2 = tree.getLevel(node);
                if (node != nowNode && !tree.isAncestor(node, nowNode) && level == level2) {
                    tree.collapseNode(node, true);
                }
            }
        });

    }
       
</script>