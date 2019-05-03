package com.esend.dao;

import com.esend.Bead.User;
import com.esend.Utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
 

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UserDAO extends User {
	//数据库连接对象
		public  boolean  login(String username,String password) {
		   User u=null;
		   Connection connection =null;
		   PreparedStatement pstmt=null;
		   
	 
		  //赋值
		  try {
			connection=DBUtil.getCon();
			  //静态sql语句
			String sql1 = "SELECT username,password FROM user WHERE username=? AND password=?";
		
			pstmt = (PreparedStatement) connection.prepareStatement(sql1);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){				
				// System.out.println(rs.getString("username")+" "+rs.getString("password"));//return true;
				u=new User();
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				System.out.println("登录成功！");
				return true;
			
			}else{
				System.out.println("用户名或者密码错误！");
				return false;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {	
		   DBUtil.close(pstmt, connection);
		}
		 return false;
	 
	}
		public void addUser(User user) {
			Connection connection = null;
			PreparedStatement psmt = null;
			try {
				 connection =DBUtil.getCon();
				 
				 String sql = "insert into user (username,password) values(?,?)";
				 psmt = (PreparedStatement) connection.prepareStatement(sql);
				 
				 //运用实体对象进行参数赋值
				 psmt.setString(1, user.getUsername());
				 psmt.setString(2,user.getPassword());
				 psmt.executeUpdate();		 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				 DBUtil.close(psmt, connection);
			}
		}	
}



