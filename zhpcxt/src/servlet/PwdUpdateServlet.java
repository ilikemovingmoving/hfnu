package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.InsertUpdateDelBean;

public class PwdUpdateServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String newpwd = request.getParameter("newpwd");
		HttpSession session = request.getSession();
		
		ArrayList adminlogin = (ArrayList)session.getAttribute("adminlogin");
		String table = "";
		String status = (String)adminlogin.get(3);
		
//		if(status.equals("����Ա")){
//			table = "admin";
//		}else if(status.equals("����Ա")){
//			table = "evaluating";
//		}else{
//			table = "student";
//		}
		
		String sql = "update user set pwd='"+newpwd+"' where user_id="+adminlogin.get(0);
		InsertUpdateDelBean ib = new InsertUpdateDelBean();
		int responseText = ib.insertANDupdateANDdel(sql);
		
		PrintWriter out = response.getWriter();
		out.print(responseText);
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
