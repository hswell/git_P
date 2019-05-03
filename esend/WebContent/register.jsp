
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>注册界面</title>  
    <style type="text/css">
 	body{
 		
 		background-position:center;
        background-repeat:no-repeat;
        background="E:\web\git_P\esend\WebContent\WEB-INF\lib\me1.jpg"
 	}
 	
 </style>
  </head>      
  <body>  
  <div style="text-align:center;margin-top:120px">  
    <h1 >请注册</h1>  
    <form action="RegisterServlet" method="post">  
        <table style="margin-left:40%">  
            <caption>用户注册</caption>  
            <tr>  
                <td>ID:</td>  
                <td><input name="id" type="text" size="20"></td>  
            </tr>  
            <tr>  
                <td>登录名:</td>  
                <td><input name="name" type="text" size="20"></td>  
            </tr>  
            <tr>  
                <td>密码: </td>  
                <td><input name="password" type="password" size="20"></td>  
            </tr>    
        </table>   
        <input type="submit" value="注册">  
        <input type="reset" value="重置">   
    </form>  
    <br>  
    <a href="login.jsp">登录</a>  
    </div>  
  </body>  
</html>
