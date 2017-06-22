package servlet.counsellor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import util.DBConn;
import bean.scoreUpload;

public class UploadScoreServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				response.setContentType("text/html;charset=UTF-8");
		        request.setCharacterEncoding("UTF-8");
				String school_year=null;
				String term=null;
				String fileName=null;
				//BufferedInputStream in=null;
				InputStream in=null;
				
		        //if(school_year!=null && !"".equals(school_year) && term!=null && !"".equals(term)){
		        	try{    
		        		if(ServletFileUpload.isMultipartContent(request)){ 
		        			DiskFileItemFactory dff = new DiskFileItemFactory();//创建该对象
		        			ServletFileUpload sfu = new ServletFileUpload(dff);//创建该对象  
		        			//sfu.setFileSizeMax(5000000);//指定单个上传文件的最大尺寸  
		        			//sfu.setSizeMax(10000000);//指定一次上传多个文件的总尺寸  
		        			FileItemIterator fii = sfu.getItemIterator(request);
		        			while(fii.hasNext()){  
		        				FileItemStream fis = fii.next();//从集合中获得一个文件流 
		        				in = fis.openStream();
		        				
		        				String fieldName = new String(fis.getFieldName().getBytes(  
		                                "ISO-8859-1"), "utf-8");  
		        				if(fis.isFormField()){
		        					if(fieldName.equals("schoolYear")){
		        						school_year=Streams.asString(in, "utf-8");
		        					}else if(fieldName.equals("term")){
		        						term=Streams.asString(in,"utf-8");
		        					}
		        					
		        					continue;
		        				}

		        				fileName=fis.getName();
		        				
		        				if(school_year!=null && term!=null && fileName!=null && !"".equals(fileName) ){
		        					
		        					scoreUpload stu = new scoreUpload();
		        					String cScoreTableName = (String) request.getSession().getAttribute("class_id");
		        					
		        					/*Connection conn=DBConn.getConn();
		        			        conn.setAutoCommit(false);*/
		        					Map msg=stu.upload(in, cScoreTableName, school_year, term);
		        					Integer msgid=Integer.parseInt(msg.get("msgid").toString());
		        					if(msgid==0){
		        						request.setAttribute("message", ""+msg.get("msg")==null?"系统异常，请联系管理员":msg.get("msg").toString()+"");
		        					}else if(msgid==1){
		        						request.setAttribute("message", "成绩上传成功！");
		        					}
		        					/*conn.commit();
		        			        conn.setAutoCommit(true);
		        			        DBConn.close(conn, null, null);*/
		        				}else{
		        					request.setAttribute("message", "'请选择学年、学期、文件'");
		        					
		        				}
		        			}
		        			
		        			//response.getWriter().println("File upload successfully!!!");
		        		}
		        		
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		      //  }else{
		       // }
			        	
			    request.getRequestDispatcher("/counsellor/classScore.jsp?schoolYear="+school_year+"&term="+term).forward(request, response);
			    
	}
	
}
