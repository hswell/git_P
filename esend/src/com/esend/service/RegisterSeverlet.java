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
public class RegisterSeverlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @return 
     * @see HttpServlet#HttpServlet()
     */
    public RegisterSeverlet() {
    	super();
		// TODO Auto-generated constructor stub
	}
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request, response);
     }  
	
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		request.setCharacterEncoding("utf-8");  
         int id=Integer.valueOf(request.getParameter("id"));  
         String name=request.getParameter("name");  
         String password=request.getParameter("password");
         int role=Integer.valueOf(request.getParameter("role"));
         User user=new User();
         user.setId(id);
         user.setUsername(name);
         user.setPassword(password);
         UserDAO userDAO=new UserDAO(); 
         userDAO.addUser(user);
         System.out.println("×¢²á³É¹¦");
         request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}
   
