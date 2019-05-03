package com.esend.service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esend.Bead.User;
import com.esend.dao.UserDAO;

@WebServlet("/RegisterSeverlet")
public class RegisterSeverlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		request.setCharacterEncoding("utf-8"); 
		
         
         String name=request.getParameter("username");  
         String password=request.getParameter("password");
         User user=new User();
         user.setUsername(name);
         user.setPassword(password);
         UserDAO userDAO=new UserDAO(); 
         userDAO.addUser(user);
         System.out.println("×¢²á³É¹¦");
         request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}
   
