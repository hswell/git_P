package com.esend.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
 
 
public class DBUtil {
   //注册驱动的工具类
	
	private static String url = null;
	
	private static String user = null;
	
	private static String  password = null;
	
	private static String  dv = null;
	
	static{
		
	   Properties  prop = new Properties();
	   
	   
	   InputStream in = DBUtil.class.getResourceAsStream("/a.properties");
	   
	   try {
		prop.load(in);
		
		url  = prop.getProperty("url");
		user = prop.getProperty("user");
		
		password = prop.getProperty("1998");
		
		dv = prop.getProperty("driver");
		
		/*System.out.println("url:"+url);*/
		
		
		//注册驱动
		  try {
			Class.forName(dv);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
	}
	
	
 
	//获取连接对象
	public static  Connection getCon() throws SQLException{
		 Connection conn = null;
		 
		 conn = (Connection) DriverManager.getConnection(url, user, password);
		 
		 return  conn;
	}
	
	//关闭的方法
	public static void close(Statement statement,Connection conn){
		   if(statement !=null){
			   try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   
		   if(conn !=null){
			   try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	}
		
	//关闭的方法
		public static void close(PreparedStatement preparedStatement,Connection conn,ResultSet resultSet){
			   if(preparedStatement !=null){
				   try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
			   
			   if(conn !=null){
				   try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
			   
			   if(resultSet!=null){
				   try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
	}
}
