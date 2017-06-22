package bean;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import util.DBConn;
import jxl.*;


public class scoreUpload {
//	  InputStream is = new  FileInputStream(sourcefile);      
//	  jxl.Workbook rwb = Workbook.getWorkbook(is); }  
//		catch (Exception e) {  e.printStackTrace(); }

	//public void upload(String lujing, String cScoreTableName, String schoolYear, String term) {
    public Map upload(InputStream in, String cScoreTableName, String schoolYear, String term) throws SQLException {
        Map<String, Object> msg=new HashMap<String, Object>();
        msg.put("msgid", 0);
        msg.put("msg","上传失败");
    	Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);

        InsertUpdateDelBean ib = new InsertUpdateDelBean();
        //String b1 = "student_id,school_grades,score,course_id,school_year,term";
        String sql = "insert into `" + cScoreTableName 
        		+ "` (student_id,school_grades,score,course_id,school_year,term) values ";
        int j;
        Connection conn=DBConn.getConn();
        
        for (j = 2; j < sheet.getRows(); j++) {//行
            Cell[] row = sheet.getRow(j);
            int i;
            if(row[1].getContents()==null || "".equals(row[1].getContents())){//学号
            	continue;
            }
            for (i = 0; i < row.length; i++) {//行
                if (j == 2) {//第三行
                    if (i > 3) {//i=4，第五列
                        String[] course = row[i].getContents().split("/");
                        String a = "'";
                        String b = "course_name,level,grades";
                        for (int m = 0; m < course.length; m++) {
                            if (m == course.length - 1) {
                                a += course[m] + "'";
                            } else {
                                a += course[m] + "','";
                            }
                        }
                        AllBean array = new AllBean();
                        ArrayList courseEx = array.getCourseOfName(course[0]);
                        if (courseEx.size() == 0) {
                        	//msg.put("msgid", 0);
                        	msg.put("msg", "未找到课程，请先添加课程");
                        	break;
                        }
                    }
                } else {//第四行以后
                    String a1 = "";
                    //String a2 = "";
                    
                    if (i > 3) {//第五列
                        Cell[] row1 = sheet.getRow(2);
                        String[] course = row1[i].getContents().split("/");
                        String course_name = course[0];
                        AllBean array = new AllBean();
                        String course_id = ((ArrayList) array.getCourseOfName(course_name).get(0)).get(0).toString();
                        
                        
                        	a1 += "'"+row[1].getContents() + "','" + row[3].getContents() + "','"
                        			+ row[i].getContents() + "','" + course_id + "','" + schoolYear + "','" + term + "'";
                        	
                        	Map paramMap=new HashMap();
                        	paramMap.put("tableName", cScoreTableName);
                        	paramMap.put("schoolYear", schoolYear);
                        	paramMap.put("term", term);
                        	paramMap.put("studentId", row[1].getContents());
                        	paramMap.put("courseId", course_id);
                        	List pastScoreList=array.queryScore4(paramMap,conn);
                        	if(pastScoreList!=null && pastScoreList.size()>0){
                        		msg.put("msg", "成绩已提交过，重新提交请先删除原来的成绩。");
                        		break;
                        	}else{
                        			sql  += "(" + a1 + "),";
                        	}
                    }
                }
            }
            if(i<row.length){
            	break;
            }
        }
        
        DBConn.close(conn, null, null);
        
        if(j>=sheet.getRows()){
        	sql=sql.substring(0, sql.lastIndexOf(","));
        	int c=ib.insertANDupdateANDdel(sql);
        	if(c!=-1){
        		msg.put("msgid",1 );
        	}
        }
        return msg;
    }
    
}