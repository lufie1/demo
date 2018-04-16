<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<%@ page import="com.rms.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Order Item Management</title>
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
         <input id="order_id" name="order_id" class="mini-hidden" />
         <input name="user_id" class="mini-hidden"  value="${sessionScope.userinfo.id }" />
        <div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
                
                 <tr>
                    <td style="width:100px;">MENU:</td>
                    <td style="width:150px;">    
                        
                              <input id="menu_id" name="menu_id" class="mini-lookup" style="width:200px;" 
            textField="name" valueField="id" popupWidth="auto" 
            popup="#gridPanel" grid="#datagrid1" multiSelect="false" value="" text=""
            />
                    </td>
                </tr>
         
                <tr>
                    <td style="width:100px;">NUM:</td>
                    <td style="width:150px;">    
                        <input name="num" class="mini-textbox"  required="true"    emptyText="" value=""/>
                    </td>
                </tr>
                  <tr>
                    <td style="width:100px;">User Name:</td>
                    <td style="width:150px;">    
                        <input name="username" class="mini-textbox"  required="true"    emptyText="" value=" ${sessionScope.userinfo.username }"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">Date Time:</td>
                    <td style="width:150px;">    
                        <input name="date" class="mini-textbox" required="true"  emptyText="" value="<%=DateUtil.getCurrentDateTime()%>"/>
                    </td>
                </tr>
               
              
            
            </table>
        </div>
       
        <div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">Submit</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">Cancle</a>       
        </div>        
    </form>
    
      <div id="gridPanel" class="mini-panel" title="header" iconCls="icon-add" style="width:550px;height:250px;" 
        showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0" 
    >
        <div property="toolbar" style="padding:5px;padding-left:8px;text-align:center;">   
            <div style="float:left;padding-bottom:2px;">
                <span>Name：</span>                
                <input id="keyText" class="mini-textbox" style="width:100px;" onenter="onSearchClick"/>
                <span>Abbreviation：</span>                
                <input id="keyText2" class="mini-textbox" style="width:80px;" onenter="onSearchClick"/>
                <a class="mini-button" onclick="onSearchClick">Search</a>
                <a class="mini-button" onclick="onClearClick">Clear</a>
            </div>
            <div style="float:right;padding-bottom:2px;">
                <a class="mini-button" onclick="onCloseClick">Close</a>
            </div>
            <div style="clear:both;"></div>
        </div>
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
            borderStyle="border:0" showPageSize="false" showPageIndex="false"
            url="../MenuCrudServlet?method=search" 
        >
            <div property="columns">
                <div type="checkcolumn" ></div>
           <div field="name" width="80" headerAlign="center" allowSort="true">Name</div>
           <div field="price" width="50"  headerAlign="center" allowSort="true" renderer="onPriceRenderer">Price</div>
            <div field="stock" width="50" headerAlign="center" allowSort="true">Stock</div>
            <div field="code" width="50" headerAlign="center" allowSort="true">Code</div>
            <div field="category_id" width="50" headerAlign="center" allowSort="true">Category Id</div>
            <div field="abbreviation" width="50" headerAlign="center" allowSort="true">Abbreviation</div>             
            </div>
        </div>  
    </div>
    <script type="text/javascript">
    
       var order_id="";
        mini.parse();
        var grid = mini.get("datagrid1");
        var keyText = mini.get("keyText");

        grid.load();

        function onSearchClick(e) {
            grid.load({
                key: keyText.value
            });
        }
        function onCloseClick(e) {
            var lookup2 = mini.get("menu_id");
            lookup2.hidePopup();
        }
        function onClearClick(e) {
            var lookup2 = mini.get("menu_id");
            lookup2.deselectAll();
        }
        var form = new mini.Form("form1");

        function SaveData() {
            var o = form.getData();            

            form.validate();
            if (form.isValid() == false) return;

            var json = mini.encode([o]);
            $.ajax({
                url: bootPATH+"../OrderDetailCrudServlet?method=save",
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
        	
        	mini.get("order_id").setValue(data.orderid);
        	//document.getElementById("order_id").setValue(data.orderid);
        	//alert(data.orderid);
        	
            if (data.action == "edit") {
           
                data = mini.clone(data);

                $.ajax({
                    url: bootPATH+"../OrderDetailCrudServlet?method=get&id=" + data.id,
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
       
    function onPriceRenderer(e) {
          return "$"+e.value;
            }


    </script>
</body>
</html>
