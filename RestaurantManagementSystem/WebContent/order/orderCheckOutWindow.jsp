<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rms.model.*" %>
<%@ page import="com.rms.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Order CheckOut Managerment</title>
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
                    <td style="width:100px;">Table NUM:</td>
                    <td style="width:210px;">    
                        <input name="table_num" class="mini-textbox"  required="true"    emptyText="" value=""/>
                    </td>
                </tr>
                  <tr>
                    <td style="width:100px;">User Name:</td>
                    <td style="width:210px;">    
                        <input name="name" class="mini-textbox"  required="true"    emptyText="" value=""/>
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">Date Time:</td>
                    <td style="width:210px;">    
                     <!--    <input name="date" class="mini-textbox" required="true"  emptyText="" value=""  dateFormat="yyyy-MM-dd HH:mm:ss" /> -->
                        <input name="date" id="date" class="mini-datepicker" style="width:180px;" onvaluechanged="onValueChanged" nullValue="null"
                       format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                    </td>
                </tr>
               
                <tr>
                    <td style="width:100px;">Order Status:</td>
                    <td style="width:210px;">    
                        
                          <input name="order_status" class="mini-combobox" valueField="title" textField="title" 
                            url="../GetCatagoryServlet?type=OrderStatus"
                             emptyText=""
                         />
                    </td>
                </tr>
             <tr>
                    <td style="width:100px;">Total Amount:</td>
                    <td style="width:210px;">    
                         <input name="total_amount"  id="total_amount" class="mini-textbox" required="true"  emptyText="" value=""/>
                    </td>
                </tr>
                        <tr>
                    <td style="width:100px;">Pay Amount:</td>
                    <td style="width:210px;">    
                         <input name="pay_amount"  id="pay_amount" class="mini-textbox" required="true"  emptyText="" value=""/>
                    </td>
                </tr>
               <tr>
                    <td style="width:100px;">Change:</td>
                    <td style="width:210px;">    
                         <input name="change"  id="change" class="mini-textbox" required="true"  emptyText="" value=""/>
                    </td>
                </tr>
                 <tr>
                   <tr>
                    <td style="width:100px;"></td>
                    <td style="width:210px;">    
                    
                         <input name="user_id" class="mini-hidden" />
                    </td>
                    </tr>
                     <tr>
                   <tr>
                    <td style="width:100px;"></td>
                    <td style="width:210px;">    
                         <input name="dining_table_id" class="mini-hidden" />
                    </td>
                </tr>
            </table>
        </div>
       
        <div style="text-align:center;padding:10px;">               
           <a class="mini-button" onclick="onChange" style="width:80px;margin-right:20px;">Cal change</a>       
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">Checkout</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">Cancle</a>       
        </div>        
    </form>
    <script type="text/javascript">
        mini.parse();

        var form = new mini.Form("form1");

        function onChange()
        {
        	var total_amount=mini.get("total_amount");
        	var total_amount_num=parseFloat(total_amount.getValue());
        	var pay_amount=mini.get("pay_amount");
        	var pay_amount_num=parseFloat(pay_amount.getValue());
        	var change=mini.get("change");
        	
        	change.setValue(pay_amount_num-total_amount_num);
        }
        
        
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
