package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.esend.Utils.DBUtil;
import com.esend.dao.FIleDAO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class test1 {
public static void main(String[] args) throws SQLException {
	Connection	connection=DBUtil.getCon();
	  //æ≤Ã¨sql”Ôæ‰
	String sql1 = "SELECT root FROM file WHERE fid=? ";

	PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sql1);
	pstmt.setString(1, "1189");
	ResultSet rs=pstmt.executeQuery();
	String xxString=null;
	while (rs.next()) {
        xxString=rs.getString("root");
      }
	System.out.println("restring"+xxString);
}
}
