<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<html>
<head>
    <title>Table Management</title>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type" />
   
       <script src="../scripts/boot.js" type="text/javascript"></script>
        <script src="../scripts/miniui/locale/en_US.js" type="text/javascript"></script>
    <style type="text/css">
    html,html body
    {
        font-family:å®ä½;
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
  <h1>Table Management</h1>      


    <div style="width:800px;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                         <%  User user=(User)session.getAttribute("userinfo");   

                        if(user!=null&&user.getRole().equalsIgnoreCase("Manager")){ %> 
                        <a class="mini-button" iconCls="icon-add" onclick="add()">Add</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="remove()">Delete</a>    <%} %>    
                         <a class="mini-button" iconCls="icon-edit" onclick="edit()">Edit</a>
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
        url="../TableCrudServlet?method=search"  pageSize="20"  idField="id" multiSelect="true" 
    >
        <div property="columns">
            <div type="indexcolumn" width="20" ></div> 
            <div type="checkcolumn"  width="20" ></div> 
            <div field="table_num" width="40" headerAlign="center" allowSort="true">table_num</div>
            <div field="location" width="40" headerAlign="center" allowSort="true">location</div>
            <div field="status" width="50" headerAlign="center" allowSort="true">status</div>
            <div field="size" width="50" headerAlign="center" allowSort="true">size</div>    
                            
        </div>
    </div>
    
 
 
 
   
    <script type="text/javascript">

    

        mini.parse();

        var grid = mini.get("datagrid1");
        grid.load();
        grid.sortBy("table_num", "desc");

        
        function add() {
            
            mini.open({
                url: bootPATH+"../table/tableWindow.html",
                title: "Add Table", width: 300, height: 350,
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
        function edit() {
         
            var row = grid.getSelected();
            if (row) {
                mini.open({
                	url: bootPATH+"../table/tableWindow.html",
                    title: "Edit Table", width: 300, height: 350,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.id };
                        iframe.contentWindow.SetData(data);
                        
                    },
                    ondestroy: function (action) {
                        grid.reload();
                        
                    }
                });
                
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
                    grid.loading("Runningï¼Please Wait......");
                    $.ajax({
                        url: bootPATH+"../TableCrudServlet?method=remove&id=" +id,
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
    function onPriceRenderer(e) {
          return "$"+e.value;
            }
grid.on("drawcell", function (e) {
        var record = e.record,
    column = e.column,
    field = e.field,
    value = e.value;

        if (field == "status") {
        	   if (e.value == "Free") {
                   e.cellHtml = "<span class='icon-ok'>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspFree</span>"
               } else {
                   e.cellHtml = "<span class='icon-tip'>&nbsp&nbsp&nbsp&nbsp&nbsp&nbspOccupied</span>"
               }
            e.cellStyle = "background:#ecedef";
        }

    

    });

        
    
    </script>

   
        
    </div>  
</body>
</html>
