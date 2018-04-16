<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<html>
<head>
    <title>User Management</title>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type" />
   
       <script src="../scripts/boot.js" type="text/javascript"></script>
        <script src="../scripts/miniui/locale/en_US.js" type="text/javascript"></script>
    <style type="text/css">
    html,html body
    {
        font-family:宋体;
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
  <h1>Database Backup Management</h1>      


    <div style="width:800px;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    <%  User user=(User)session.getAttribute("userinfo");   

                        if(user!=null&&user.getRole().equalsIgnoreCase("Manager")){%> 
                        <a class="mini-button" iconCls="icon-add" onclick="add()">Add Backup</a>
                   
                            <a class="mini-button" iconCls="icon-find" onclick="recovery()">Recovery</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="remove()">Delete Backup</a>       <%} %>
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="mini-textbox" emptyText="" style="width:150px;" onenter="onKeyEnter"/>   
                        <a class="mini-button" onclick="search()">Search</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:800px;height:380px;" allowResize="true"
        url="../DatabaseBackupInfoCrudServlet?method=search"  pageSize="20"  idField="id" multiSelect="true" 
    >
        <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
             <div field="source_tablename" width="80" headerAlign="center" allowSort="true">Source Table Name</div>
            <div field="backup_tablename" width="80" headerAlign="center" allowSort="true">Backup Table Name</div>

            <div field="updatetm" width="50"  headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss" >Time</div>

    
                            
        </div>
    </div>
    
 
 
 
   
    <script type="text/javascript">

    

        mini.parse();

        var grid = mini.get("datagrid1");
        grid.load();
        grid.sortBy("updatetm", "desc");

        
        function add() {
            
            mini.open({
                url: bootPATH+"../databasebackup/databasebackupWindow.jsp",
                title: "Add Table Backup", width: 350, height: 450,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
       
        
        
        
    function recovery() {
	
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("Confirm recovery this record?")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.id);
                    }
                    var id = ids.join(',');
                    var source_tablename=rows[0].source_tablename;
                    var backup_tablename=rows[0].backup_tablename;
                    grid.loading("Running，Please Wait......");
                    $.ajax({
                        url: bootPATH+"../DatabaseBackupInfoCrudServlet?method=recovery&source_tablename=" +source_tablename+"&backup_tablename="+backup_tablename,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            } else {
                alert("Please Choose One Record!");
            }
        }
        function remove() {
            
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("Confirm delete this record?")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.id);
                    }
                    var id = ids.join(',');
                    grid.loading("Running，Please Wait......");
                    $.ajax({
                        url: bootPATH+"../DatabaseBackupInfoCrudServlet?method=remove&id=" +id,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            } else {
                alert("Please Choose One Record!");
            }
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
        /////////////////////////////////////////////////

    
    </script>

   
        
    </div>  
</body>
</html>
