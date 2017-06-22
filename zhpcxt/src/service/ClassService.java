package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import bean.SelectBean;

public class ClassService {

	SelectBean sb=new SelectBean();
	
	
	public List queryDistinctClass(){
		String sql="select distinct(class) from class ";
		String[] args={"class"};
		
		return sb.select(sql, args);
	}


	public List queryDistinctPro() {
		String sql="select distinct(pro) from student ";
		String[] args={"pro"};
		
		return sb.select(sql, args);
	}
	
	public List queryDistinctGrade(){
		String sql="select distinct(grade) from class ";
		String[] args={"grade"};
		return sb.select(sql, args);
	}
	
	/*public List queryClassName(String classId,Connection conn){
		String sql="select concat(pro,class) pro_class from class ";
		List paramList=new ArrayList();
		paramList.add(classId);
		return sb.query(sql, paramList,conn);
	}


	public List queryDistinctClass(Connection conn) {
		String sql="select distinct(class_id) from class ";
		String[] args={"class"};
		
		return sb.select(sql, args,conn);
	}*/
	
	public List queryClass(Connection conn){
		String sql="select distinct(concat(pro,',',class)) pro_class from class ";
		return sb.query(sql, null, conn);
	}


	public List queryDistinctGrade(Connection conn) {
		String sql="select distinct(grade) from class ";
		String[] args={"grade"};
		return sb.select(sql, args,conn);
	}
}
