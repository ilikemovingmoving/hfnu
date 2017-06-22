package servlet.counsellor;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import bean.rateUpload;
import bean.scoreUpload;

public class UploadRatingServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
		String school_year=null;
		//String term=null;
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
        					}
        					
        					continue;
        				}

        				fileName=fis.getName();
        				
        				if(school_year!=null && fileName!=null && !"".equals(fileName) ){
        					
        					rateUpload rate = new rateUpload();
        		            String cScoreTableName = (String) request.getSession().getAttribute("class_id");
        		            rate.upload(in, cScoreTableName, school_year);
        		            request.setAttribute("message", "'上传成功！'");
        		        }else{
        					request.setAttribute("message", "'请选择学年'");
        					
        				}
        			}
        			
        			//response.getWriter().println("File upload successfully!!!");
        		}
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}
      //  }else{
       // }
	        	
        	response.sendRedirect(request.getContextPath() + "/counsellor/ratingScore.jsp");
	    

	}
}
