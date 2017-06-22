package servlet.counsellor;

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

public class ClassScoreServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		
		AllBean allBean = new AllBean();
		String class_id = (String) arg0.getSession().getAttribute("class_id");
		List list = allBean.getSchYear(class_id);
		PrintWriter pw=arg1.getWriter();
		HashMap map=new HashMap();
		map.put("list", list);
		pw.write(JSONObject.fromObject(map).toString());
	}

}
