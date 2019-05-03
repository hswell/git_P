package com.esend.dao;


	import com.esend.Bead.FIle;
import com.esend.Bead.User;
	import com.esend.Utils.DBUtil;

	import java.sql.ResultSet;
	import java.sql.SQLException;
	 

	import com.mysql.jdbc.Connection;
	import com.mysql.jdbc.PreparedStatement;

	public class FIleDAO extends FIle {
		//数据库连接对象
			public   String SerchFile(String fid) {
			   FIle f1=null;
			   Connection connection =null;
			   PreparedStatement pstmt=null;
			   
		 
			  //赋值
			  try {
				connection=DBUtil.getCon();
				  //静态sql语句
				String sql1 = "SELECT root FROM file WHERE fid=? ";
			
				pstmt = (PreparedStatement) connection.prepareStatement(sql1);
				pstmt.setString(1, fid);
				ResultSet rs=pstmt.executeQuery();
				String reString=null;
				while (rs.next()) {
			        reString=rs.getString("root");
			      }
				return reString;
				  
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {	
			   DBUtil.close(pstmt, connection);
			}
			 return null;
		 
		}
			public void  addFile( FIle fIle) {
 
				Connection connection = null;
				PreparedStatement psmt = null;
				try {
					 connection =DBUtil.getCon();
					 
					 String sql = "insert into file (fid,fname,root) values(?,?,?)";
					 psmt = (PreparedStatement) connection.prepareStatement(sql);
					 
				
					//运用实体对象进行参数赋值
					 psmt.setString(1, fIle.getFid());
					 psmt.setString(2,fIle.getFname());
					 psmt.setString(3, fIle.getRoot());
					 psmt.executeUpdate();		 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					 DBUtil.close(psmt, connection);
				}
			}	
	}





