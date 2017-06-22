package servlet.counsellor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import bean.AllBean;

public class CourseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		AllBean ab=new AllBean();
		/*List list=ab.queryCourse(null);
		System.out.println(JSONObject.fromObject(list));*/
		
	}
	
}
