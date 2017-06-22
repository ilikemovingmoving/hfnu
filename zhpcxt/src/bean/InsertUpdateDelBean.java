package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import util.DBConn;

public class InsertUpdateDelBean {
	
	Connection conn = null;
	Statement st = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public int insertANDupdateANDdel(String sql){
		int temp = 0;
		conn = DBConn.getConn();
		
		try {
			st = conn.createStatement();
			temp = st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
			temp = -1;
		} finally{
			DBConn.close(conn,st,rs);
		}
		
		return temp;
	}
	
	public int insertANDupdateANDdel(String sql,Connection conn_){
		int temp = 0;
		//conn = DBConn.getConn();
		
		try {
			st = conn_.createStatement();
			temp = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			temp = -1;
		} finally{
			DBConn.close(null,st,rs);
		}
		
		return temp;
	}
	public int insertANDupdateANDdel(String sql,List paramList,Connection _conn){
		//conn=DBConn.getConn();
		int temp=0;
		try {
			pstmt=_conn.prepareStatement(sql);
			int size=paramList.size();
			for (int i=0;i<size;i++) {
				pstmt.setObject(i+1, paramList.get(i));
			}
			temp=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.close(null, pstmt, rs);
		}
		return temp;
	}
	
	public int insertANDupdateANDdel(String sql,List paramList){
		conn=DBConn.getConn();
		int temp=0;
		try {
			pstmt=conn.prepareStatement(sql);
			int size=paramList.size();
			for (int i=0;i<size;i++) {
				pstmt.setObject(i+1, paramList.get(i));
			}
			temp=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConn.close(conn, pstmt, rs);
		}
		return temp;
		
	}

}
