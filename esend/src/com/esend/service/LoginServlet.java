package com.esend.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.esend.Bead.User;
import com.esend.dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserDAO userDAO=new UserDAO(); 
		//User user=userDAO.login(username, password);
		boolean isHave=userDAO.login(username, password);
		
		if(isHave){
			request.setAttribute("usernmae", username);			
			request.getRequestDispatcher("main.jsp").forward(request, response);
			HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
			
		}else{
			request.getSession().setAttribute("MSG", "’À∫≈ªÚ√‹¬Î¥ÌŒÛ");		
			response.sendRedirect("login.jsp");
		}
		
		
	}
}

