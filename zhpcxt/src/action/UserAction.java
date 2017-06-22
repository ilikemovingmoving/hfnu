package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private String admin;
	private UserService userService=new UserService();
	HttpServletRequest request;
	HttpSession session;
	HttpServletResponse response;
	
	public UserAction(){
		request =ServletActionContext.getRequest();
		session =request.getSession();
		response= ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String del(){
		List paramList=new ArrayList();
		paramList.add(admin);
		int result=userService.del(paramList);
		PrintWriter pw=null;
		try {
			pw=response.getWriter();
			if(result==1){
				pw.write("{\"code\":1,\"msg\":\"操作成功\"}");
			}else{
				pw.write("{\"code\":0,\"msg\":\"操作失败\"}");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			//pw.write("{\"code\":0,\"msg\":\"操作失败\"}");
		}
		return null;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
