package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Validate;
import bean.InsertUpdateDelBean;

public class IntellectualgradeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public IntellectualgradeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=gb2312");
		request.setCharacterEncoding("gb2312");

		InsertUpdateDelBean ib = new InsertUpdateDelBean();
		String args[] = {"grade"};
		String row = "";
		String val = "";
		Validate v = new Validate();
		HttpSession session = request.getSession();
		ArrayList adminlogin = (ArrayList)session.getAttribute("adminlogin");
		String term = request.getParameter("term");
		String name = "";
		String grade = "";
		if(term != null && term.equals("1")){
			name = "˼����������뷨�ɻ���,��ѧӢ��1,��������,���������,���Դ���,�ߵ���ѧ��";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3")+","+request.getParameter("input4")+","+request.getParameter("input5")+","+request.getParameter("input6");
		}else if(term != null && term.equals("2")){
			name = "��ѧ����,���˼�������ԭ��,�й�����ʷ��Ҫ,��ѧӢ��2,��ʦ����,��ɢ��ѧ,�ߵ���ѧ��,��ͨ����,�߼����Գ������";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3")+","+request.getParameter("input4")+","+request.getParameter("input5")+","+request.getParameter("input6")+","+request.getParameter("input7")+","+request.getParameter("input8")+","+request.getParameter("input9");
		}else if(term != null && term.equals("3")){
			name = "ë��˼�롢��Сƽ���ۺ͡�����������Ҫ˼�����,��ѧӢ��3,��·ԭ��,ģ����Ӽ���,�㷨�����ݽṹ,�������";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3")+","+request.getParameter("input4")+","+request.getParameter("input5")+","+request.getParameter("input6");
		}else if(term != null && term.equals("4")){
			name = "ë��˼�롢��Сƽ���ۺ͡�����������Ҫ˼�����,��ѧӢ��4,�����߼�,��������ԭ��,����ԭ��,����ϵͳ";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3")+","+request.getParameter("input4")+","+request.getParameter("input5")+","+request.getParameter("input6");
		}else if(term != null && term.equals("5")){
			name = "����ѧ,�ִ���������,���ݿ�ϵͳԭ��,�����ͨ��������";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3")+","+request.getParameter("input4");
		}else if(term != null && term.equals("6")){
			name = "�������ѧ��,����ѧ,������ϰ,�������ʵϰ";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3")+","+request.getParameter("input4");
		}else if(term != null && term.equals("7")){
			name = "����������,����ʵϰ,�����ְҵ����";
			grade = request.getParameter("input1")+","+request.getParameter("input2")+","+request.getParameter("input3");
		}else if(term != null && term.equals("8")){
			name = "��������ۺ�ʵϰ,��ҵ���";
			grade = request.getParameter("input1")+","+request.getParameter("input2");
		}
		String sql = "insert into intellectualgrade(student,term,name,grade,dates) values("+adminlogin.get(0)+",'"+term+"','"+name+"','"+grade+"','"+v.getSystemDate().substring(0,10)+"')";
		int responseText = ib.insertANDupdateANDdel(sql);
		if(responseText != -1){
			request.setAttribute("message", "'�ɼ������ύ�ɹ���'");
		}else{
			request.setAttribute("message", "'�˳ɼ����������ύ��'");
		}
		request.getRequestDispatcher("/admin/intellectualgrade.jsp").forward(request, response);	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
