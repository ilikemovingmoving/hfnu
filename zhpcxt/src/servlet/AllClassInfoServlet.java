package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import net.sf.json.JSONObject;
import bean.AllBean;
import bean.SelectBean;

public class AllClassInfoServlet extends HttpServlet {

	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {

		AllBean allBean=new AllBean();
		List list=allBean.getBjofGrade();
		PrintWriter pw=arg1.getWriter();
		HashMap map=new HashMap();
		map.put("gradeList", list);
		pw.write(JSONObject.fromObject(map).toString());
		
		
	}
}
