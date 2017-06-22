package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBConn;

public class SelectBean {
	
	Connection conn = null;
	Statement st = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	

	public ArrayList select(String sql,String[] args){
		ArrayList al = new ArrayList();
		conn = DBConn.getConn();
		try {
			st = conn.createStatement();
			//System.out.println("sql="+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				ArrayList alRow = new ArrayList();
				for(int i = 0;i < args.length;i++){
					alRow.add(rs.getString(args[i]));
				}
				al.add(alRow);
				//System.out.println(al);
			}
		} catch (SQLException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		} finally{
			DBConn.close(conn,st,rs);
		}
		return al;
	}
	
	public ArrayList select2(String sql,String[] args,List paramList){
		ArrayList al = new ArrayList();
		conn = DBConn.getConn();
		try {
			pstmt=conn.prepareStatement(sql);
			int size=0;
			if(paramList!=null && (size=paramList.size())!=0){
				for (int i=0;i<size;i++) {
					pstmt.setObject(i+1, paramList.get(i));
				}
			}
			rs=pstmt.executeQuery();
			while(rs.next()){
				ArrayList alRow = new ArrayList();
				for(int i = 0;i < args.length;i++){
					alRow.add(rs.getString(args[i]));
				}
				al.add(alRow);
				//System.out.println(al);
			}
		} catch (SQLException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		} finally{
			DBConn.close(conn,st,rs);
		}
		return al;
	}

	

	public ArrayList selectRow(String[] args,String sql){
		ArrayList al = new ArrayList();
		conn = DBConn.getConn();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
			for(int i = 0;i < args.length;i++){
				al.add(rs.getString(args[i]));
			}
		} catch (SQLException e) {
			// TODO �Զ���� catch ��
		} finally{
			DBConn.close(conn,st,rs);
		}
		return al;
	}
	
	

	public ArrayList selectRow(String sql,String[] args){
		ArrayList al = new ArrayList();
		conn = DBConn.getConn();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				for(int i = 0;i < args.length;i++){
					al.add(rs.getString(args[i]));
				}
			}
		} catch (SQLException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		} finally{
			DBConn.close(conn,st,rs);
		}
		return al;
	}
	
	public ArrayList selectPage(String sql,String[] args,int showRow,int itemSum){
		ArrayList al = new ArrayList();
		conn = DBConn.getConn();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			for(int i = 0;i < itemSum;i++){
				rs.next();
			}
			for(int n = 0;n < showRow;n++){
				rs.next();
				ArrayList alRow = new ArrayList();
				for(int i = 0;i < args.length;i++){
					alRow.add(rs.getString(args[i]));
				}
				al.add(alRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBConn.close(conn,st,rs);
		}
		return al;
	}
	
	public List query(String sql,List paramList){
		conn=DBConn.getConn();
		List list=new ArrayList();
		try {
			pstmt=conn.prepareStatement(sql);
			int size=0;
			if(paramList!=null && (size=paramList.size())!=0){
				for (int i=0;i<size;i++) {
					pstmt.setObject(i+1, paramList.get(i));
				}
			}
			rs=pstmt.executeQuery();
			ResultSetMetaData resultSetMetaData=pstmt.getMetaData();
			int colSize=pstmt.getMetaData().getColumnCount();
			while(rs.next()){
				Map<String, Object> map=new HashMap<String, Object>();
				for(int j=0;j<colSize;j++){
					String colName=resultSetMetaData.getColumnName(j+1);
					map.put(colName, rs.getObject(colName));
				}
				/*for (String col : cols) {
					map.put(col, rs.getObject(col));
				}*/
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConn.close(conn,st,rs);
		}
		
		return list;
	}
	
	public List query(String sql,List paramList,Connection conn_){
		//conn=DBConn.getConn();
		List list=new ArrayList();
		try {
			pstmt=conn_.prepareStatement(sql);
			int size=0;
			if(paramList!=null && (size=paramList.size())!=0){
				for (int i=0;i<size;i++) {
					pstmt.setObject(i+1, paramList.get(i));
				}
			}
			rs=pstmt.executeQuery();
			ResultSetMetaData resultSetMetaData=pstmt.getMetaData();
			int colSize=pstmt.getMetaData().getColumnCount();
			while(rs.next()){
				Map<String, Object> map=new HashMap<String, Object>();
				for(int j=0;j<colSize;j++){
					String colName=resultSetMetaData.getColumnName(j+1);
					map.put(colName, rs.getObject(colName));
				}
				/*for (String col : cols) {
					map.put(col, rs.getObject(col));
				}*/
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConn.close(null,st,rs);
		}
		
		return list;
	}
	
	public Integer queryCount(String sql,List paramList){
		Integer count=0;
		conn = DBConn.getConn();
		try {
			pstmt=conn.prepareStatement(sql);
			int size=paramList.size();
			if(paramList!=null && (size=paramList.size())!=0){
				for (int i=0;i<size;i++) {
					pstmt.setObject(i+1, paramList.get(i));
				}
			}
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConn.close(conn, pstmt, rs);
		}
		return count;
	}
	public Integer queryCount(String sql,List paramList,Connection _conn){
		Integer count=0;
		try {
			pstmt=_conn.prepareStatement(sql);
			int size=0;
			if(paramList!=null && (size=paramList.size())!=0){
				for (int i=0;i<size;i++) {
					pstmt.setObject(i+1, paramList.get(i));
				}
			}
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConn.close(null, pstmt, rs);
		}
		return count;
	}
	
	public List select(String sql, String[] args, Connection _conn) {
		ArrayList al = new ArrayList();
		//conn = DBConn.getConn();
		try {
			st = _conn.createStatement();
			//System.out.println("sql="+sql);
			rs = st.executeQuery(sql);
			while(rs.next()){
				ArrayList alRow = new ArrayList();
				for(int i = 0;i < args.length;i++){
					alRow.add(rs.getString(args[i]));
				}
				al.add(alRow);
				//System.out.println(al);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBConn.close(null,st,rs);
		}
		return al;
	}
	
	public static void main(String[] args) {
		SelectBean sb=new SelectBean();
		List list=sb.query(" select * from `2013003` ", null);
		for (Object object : list) {
			Map map=(Map) object;
			System.out.println(map.get("school_year"));
		}
		
	}

	
}
