<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%out.println("用户名："+session.getAttribute("username")); %>
<body background="E:\web\git_P\esend\WebContent\WEB-INF\lib\me1.jpg">
  <div style="text-align:center;margin-top:120px">  
   
    <h1 style=“text-align:center;”>接收发送文件</h1>  
        <table style="margin-left:40%">  
            <tr>  
                
                <td><input type="button" onclick="window.location.href='upload.jsp'" value="发送文件"></td>  
            </tr>  
            <tr>  
               
                <td><input type="button" onclick="window.location.href='down.jsp'" value="接收文件"></td>  
            </tr>  
        </table>   
  
    <br>  
    
    </div> 
</body>
</html>