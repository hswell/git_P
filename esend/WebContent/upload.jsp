<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传页面</title>
</head>
<body  background="E:\web\git_P\esend\WebContent\WEB-INF\lib\me1.jpg">>
<script type="text/javascript">
    alert("${info}");
</script>

<!-- 上传文件是上传到服务器上，而保存到数据库是文件名 -->
<!-- 上传文件是以文件转换为二进制流的形式上传的 -->
<!-- enctype="multipart/form-data"需要设置在form里面，否则无法提交文件 -->
<form action="upload" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td></td>
            <td><h1>文件上传</h1></td>
        </tr>
      
        <tr>
            <td>上传文件:</td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="上传文件"/></td>
        </tr>
          <tr>    
                <td><input type="button" onclick="window.location.href='main.jsp'" value="主界面"></td>  
            </tr>  
    </table>
</form>
</body>
</html>