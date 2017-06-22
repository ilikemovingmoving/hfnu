package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.StudentService;
import util.DBConn;
import util.ReadExcel;
import bean.InsertUpdateDelBean;

import com.opensymphony.xwork2.ActionSupport;

public class StudentAction extends ActionSupport {

	private String fileFileName;
	private File file;
	private String fileContentType;
	private String studentIdList;
	private StudentService studentService=new StudentService();
	HttpServletRequest request;
	HttpSession session;
	HttpServletResponse response;
	
	public StudentAction() {
		request =ServletActionContext.getRequest();
		session =request.getSession();
		response= ServletActionContext.getResponse();
	}
	
	
	public String upload(){
		
		response.setContentType("text/html;charset=UTF-8");
        if(file==null){
        	return "query";
        }
        
        Object class_id=session.getAttribute("class_id");
		try{    
			request.setCharacterEncoding("UTF-8");
			boolean res=studentService.upload(file,fileFileName,class_id+"");
			if(res){
				request.setAttribute("message", "'上传成功'");
			}else{
				request.setAttribute("message", "'上传失败'");
			}
			
		}catch(Exception e){
			e.printStackTrace();
        }
		
		return SUCCESS;
	}

	public String del(){
		//String[] str=studentIdList.split(",");
		boolean res;
		try {
			res = studentService.del(studentIdList);
			HttpServletResponse response =  ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw=null;
			try {
				pw=response.getWriter();
				if(res){
					pw.write("{\"retCode\":1,\"retMsg\":\"操作成功\"}");
					
				}else{
					pw.write("{\"retCode\":0,\"retMsg\":\"操作失败\"}");
				}
			} catch (IOException e) {
				pw.write("{\"retCode\":0,\"retMsg\":\"操作失败\"}");
				e.printStackTrace();
			}finally{
				if(pw!=null){pw.close();}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return SUCCESS;
	}

	public String modify(){
		
		return SUCCESS;
	}
	
	public String query(){
		//HttpServletRequest request = ServletActionContext.getRequest();
		//request.setAttribute("message", request.getAttribute("message"));
		return SUCCESS;
	}
	
	public String querySituation(){
		/*HttpServletRequest request =ServletActionContext.getRequest();
		HttpSession session =request.getSession();
		HttpServletResponse response= ServletActionContext.getResponse();*/
		response.setContentType("text/html;charset=UTF-8");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("studentId", request.getParameter("student_id"));
		//map.put("classId", request.getParameter("class_id"));
		List list=studentService.querySituation(map);
		request.setAttribute("list", list);
		request.setAttribute("classId", request.getParameter("class_id"));
		return "situation";
	}
	
	public String address(){
		try {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		Map map=new LinkedHashMap();
		Map param=new LinkedHashMap();
		param.put("province", request.getParameter("province"));
		param.put("city", request.getParameter("city"));
		param.put("distrinct", request.getParameter("distrinct"));
		//param.put("town", request.getParameter("town"));
		param.put("class", request.getParameter("cls"));
		param.put("pro", request.getParameter("pro"));
		param.put("grade", request.getParameter("grade"));
		Map res=studentService.queryAddress(param);
		List list2611=(List)res.get("list");
		map.put("list", list2611);
		//List city2=
		//map.put("city", list271);
		PrintWriter pw=response.getWriter();
		pw.write(JSONObject.fromObject(map).toString());
		//pw.write(JSONArray.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String scoreAnalyze(){
		response.setContentType("text/html;charset=utf-8");
		Map param=new HashMap();
		param.put("grade", request.getParameter("grade"));
		param.put("pro", request.getParameter("pro"));
		param.put("class", request.getParameter("cls"));
		List list=studentService.scoreAnalyze(param);
		PrintWriter pw;
		try {
			request.setCharacterEncoding("utf-8");
			pw=response.getWriter();
			Map map=new HashMap();
			map.put("list", list);
			pw.write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String stuWarningbyTime(){
		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
			PrintWriter pw=response.getWriter();
			Map map=studentService.stuWarningbyTime();
			pw.write(JSONObject.fromObject(map).toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public String getStudentIdList() {
		return studentIdList;
	}


	public void setStudentIdList(String studentIdList) {
		this.studentIdList = studentIdList;
	}

}
