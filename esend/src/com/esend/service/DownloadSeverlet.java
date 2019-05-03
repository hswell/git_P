package com.esend.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.esend.Bead.FIle;
import com.esend.dao.FIleDAO;

/** 
* @author BieHongLi 
* @version 创建时间：2017年3月4日 下午5:29:03 
* 注意：上传文件必须添加@MultipartConfig()可以设置上传文件的大小
*/
@WebServlet("/down")
@MultipartConfig
public class DownloadSeverlet extends HttpServlet{

    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	String fid = request.getParameter("fid"); //客户端传递的需要下载的文件名
        System.out.println("fid is "+fid);
        try {
        	  //获取文件描述信息
          
            //获取上传的文件
           FIleDAO fIleDAO=new FIleDAO();
           String root=fIleDAO.SerchFile(fid);
           System.out.println("root is"+root);
           
           String path = request.getServletContext().getRealPath("/upload")+"\\"+fid+root; //默认认为文件在当前项目的根目录
           System.out.println(path);
           FileInputStream fis = new FileInputStream(path);
           response.setCharacterEncoding("utf-8");
           response.setHeader("Content-Disposition", "attachment; filename="+fid+root);
           ServletOutputStream out = response.getOutputStream();
          byte[] bt = new byte[1024];
          int length = 0;
          while((length=fis.read(bt))!=-1){
               out.write(bt,0,length);
         }
           out.close();
        	
        
           request.setAttribute("info", "下载成功");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("info", "下载文件失败");
        }
        
        request.getRequestDispatcher("/down.jsp").forward(request, response);
    }
    
}