package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=gb2312");
		request.setCharacterEncoding("gb2312");

		String mark = request.getParameter("mark");
		String responseText = "";
		
		HttpSession session = request.getSession();
		
		if(mark != null && mark.equals("users")){
			session.removeAttribute("userslogin");
		}
		if(mark != null && mark.equals("admin")){
			session.removeAttribute("adminlogin");
		}
		responseText = mark;
		//response.setHeader("refresh","0;URL=index.jsp");
		PrintWriter out = response.getWriter();
		out.print(responseText);
		out.close();
	}


}
