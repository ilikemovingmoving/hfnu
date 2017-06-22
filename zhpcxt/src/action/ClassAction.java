package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;







import service.ClassService;

import com.opensymphony.xwork2.ActionSupport;

public class ClassAction extends ActionSupport {

	ClassService classService=new ClassService();
	
	public String queryDistinctClass(){
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter pw;
		try {
			pw = response.getWriter();
			List classList = classService.queryDistinctClass();
			Map map=new HashMap();
			map.put("list", classList);
			pw.write(JSONObject.fromObject(map).toString());
			//pw.write("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String queryDistinctPro(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			List list = classService.queryDistinctPro();
			Map map=new HashMap();
			map.put("list", list);
			pw.write(JSONObject.fromObject(map).toString());
			//pw.write("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
