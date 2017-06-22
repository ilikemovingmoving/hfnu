package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SelectBean;

public class AdminLoginServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String status = request.getParameter("status");
//		String table = "";
//		System.out.print("status="+status);
//		if(status.equals("辅导员")){
//			table = "counsellor";
//		}else if(status.equals("书记")){
//			table = "admin";
//		}else{
//			table = "student";
//		}
		
//		Integer classId = AdminLoginServlet.getClassId("admin");
		
		String sql = "select * from user where user_id='"+user_id+"' and pwd='"+pwd+"' and status = '" + status + "'";

		String args[] = {"user_id","name","pwd","status"};
		SelectBean sb = new SelectBean();		
		ArrayList al = sb.selectRow(args, sql);
		//System.out.print("sql="+sql);

        String responseText = "";
		
        HttpSession session = request.getSession();
		if(al == null || al.size() == 0){
			responseText = "1";
//			request.setAttribute("message", responseText);
		}else if(status.equals("书记")){
			session.setAttribute("adminlogin", al);
			responseText = "2";
//			request.setAttribute("adminName", name);
		}else if(status.equals("辅导员")){
			session.setAttribute("adminlogin", al);
			responseText = "3";
		}else if(status.equals("学生")){
			session.setAttribute("adminlogin", al);
			responseText = "4";
		}
//		response.sendRedirect("/twtweb/users/index.jsp?message="+responseText);
//		request.getRequestDispatcher("/users/index.jsp").forward(request, response);
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
	
//	public static Map<String, Integer> classMap = new HashMap<String,Integer>();
//	
//	//POST
//	public void setClassId(int classId,String username){
//		classMap.remove(username);
//		classMap.put(username, classId);
//	}
//
//	//GET
//	public static Integer getClassId(String username){
//		return classMap.get(username);
//	}
//	
//	//POST
//	public static void clearClassId(String username){
//		classMap.remove(username);
//	}
//
}
