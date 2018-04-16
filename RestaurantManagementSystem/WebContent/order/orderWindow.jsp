<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<%@ page import="com.rms.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>User Managerment</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    
    <script src="../scripts/boot.js" type="text/javascript"></script>
    

    <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
    </style>
</head>
<body>    
     
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
                 <tr>
                    <td style="width:100px;">Table NUM(Free):</td>
                    <td style="width:150px;">    
                        
                          <input name="dining_table_id" class="mini-combobox" valueField="dining_table_id" textField="table_num" 
                            url="../GetFreeTableServlet"
                             emptyText="" 
                         />
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">UserName:</td>
                    <td style="width:150px;">    
                        <input name="name" class="mini-textbox"  required="true"    emptyText="" value="${sessionScope.userinfo.username }"/>
                    </td>
                </tr>
                  <tr>
                    <td style="width:100px;">UserID:</td>
                    <td style="width:150px;">    
                        <input name="user_id" class="mini-textbox"  required="true"    emptyText="" value="${sessionScope.userinfo.id }"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">Date Time:</td>
                    <td style="width:150px;">    
                        <input name="date" class="mini-textbox" required="true"  emptyText="" value="<%=DateUtil.getCurrentDateTime()%>" dateFormat="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
               
                <tr>
                    <td style="width:100px;">Order Status:</td>
                    <td style="width:150px;">    
                        
                          <input name="order_status" class="mini-combobox" valueField="title" textField="title" 
                            url="../GetCatagoryServlet?type=OrderStatus"
                             value="Not Pay"
                         />
                    </td>
                </tr>
            
            </table>
        </div>
       
        <div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">Submit</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">Cancle</a>       
        </div>        
    </form>
    <script type="text/javascript">
        mini.parse();

        var form = new mini.Form("form1");

        function SaveData() {
            var o = form.getData();            

            form.validate();
            if (form.isValid() == false) return;

            var json = mini.encode([o]);
            $.ajax({
                url: bootPATH+"../OrderCrudServlet?method=save",
		        type: 'post',
                data: { data: json },
                cache: false,
                success: function (text) {
                    CloseWindow("save");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
            });
        }
        function SetData(data) {
            if (data.action == "edit") {
           
                data = mini.clone(data);

                $.ajax({
                    url: bootPATH+"../OrderCrudServlet?method=get&id=" + data.id,
                    cache: false,
                    success: function (text) {
                        var o = mini.decode(text);
                        form.setData(o);
                        form.setChanged(false);
                    }
                });
            }
        }

        function GetData() {
            var o = form.getData();
            return o;
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("Data change ,save it?")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        //////////////////////////////////
       



    </script>
</body>
</html>
