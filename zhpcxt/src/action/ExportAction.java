package action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import service.ScoreService;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import bean.AllBean;

import com.opensymphony.xwork2.ActionSupport;

public class ExportAction extends ActionSupport {
	private String grade;
	private String pro;
	private String cls;
	HttpServletRequest request;
	HttpSession session;
	HttpServletResponse response;
	private ScoreService scoreService=new ScoreService();
	
	public ExportAction() {
		request =ServletActionContext.getRequest();
		session =request.getSession();
		response= ServletActionContext.getResponse();
		//response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String stuWarning(){
		//String title="";
//		AllBean ab=new AllBean();
//		ArrayList list=null;
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("grade", grade);
		param.put("pro", pro);
		param.put("class", cls);
		param.put("noQuality", 4);
		//List dataList=ab.queryStuWarning2(param);
		List dataList=scoreService.queryStuWarning4(param);
		try {
		OutputStream out = response.getOutputStream();// 取得输出流 response.reset();// 清空输出流  
        response.setContentType("application/msexcel");// 定义输出类型  
        String filename=(grade!=null&&!"".equals(grade)?(grade+"级"):"")
        		+pro+(cls!=null&&!"".equals(cls)?(cls+"班"):"")+"学业预警学生名单";
        String[] title={"学号","姓名","性别","班级","未通过课程数"};
		response.setHeader("Content-Disposition","attachment;filename="+new String(filename.getBytes("UTF-8"), "iso-8859-1")+".xls");
		this.doStuWarning(filename,title, dataList, out);  
		out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	public int doStuWarning(String bigTitle,String[] Title, List<List<Object>> list,OutputStream out){  
        int res = 0;  
          try {      
           WritableWorkbook workbook = Workbook.createWorkbook(out);  
           WritableSheet sheet = workbook.createSheet("Sheet1", 0);  
           jxl.SheetSettings sheetset = sheet.getSettings();  
           sheetset.setProtected(false);  
           WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);  
           WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);  
           WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);  
           //wcf_center.set
           wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
           wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
           wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
           wcf_center.setWrap(false); // 文字是否换行  
           WritableCellFormat wcf_content = new WritableCellFormat(NormalFont,NumberFormats.INTEGER);  
           wcf_content.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条  
           wcf_content.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
           wcf_content.setAlignment(Alignment.CENTRE); // 文字水平对齐  
           wcf_content.setWrap(false); // 文字是否换行     
             
             
           sheet.getSettings().setDefaultColumnWidth(30); // 设置列的默认宽度  
           
  
           /** ***************以下是EXCEL开头大标题********************* */  
           int row=0;
           sheet.mergeCells(0, row, Title.length, 1);  
           sheet.addCell(new Label(0, 0, bigTitle, wcf_center));  
           /** ***************以下是EXCEL第一行列标题********************* */  
           row+=2;
           CellView cellView=new CellView();
           cellView.setAutosize(true);
           sheet.setColumnView(0, cellView);
           //sheet.setRowView(row,row+1, false);
           int cols=0;
           sheet.addCell(new Label(cols++, row,"序号",wcf_center));  
           for (int i = 0; i < Title.length; i++) {  
            sheet.addCell(new Label(cols+i, row,Title[i],wcf_center));  
           }     
           /** ***************以下是EXCEL正文数据********************* */  
           row++;
           for (int i = 0; i < list.size(); i++) {  
               List<Object> objList = list.get(i);  
               //int cols=0;
               cols=0;
               sheet.addCell(new jxl.write.Number(cols, row,new Integer(i+1),wcf_content));
               cols++;
               for (int j = 0; j < Title.length; j++) {  
                   Object obj = objList.get(j);  
                   if(obj instanceof Integer) {  
                       sheet.addCell(new jxl.write.Number(cols+j, row,new Integer(objList.get(j).toString()).intValue(),wcf_content));  
                   }else if(obj instanceof Double ){  
                       sheet.addCell(new jxl.write.Number(cols+j, row,new Double(objList.get(j).toString()).doubleValue(),wcf_content));  
                   }else if(obj instanceof Float){  
                       sheet.addCell(new jxl.write.Number(cols+j, row,new Double(objList.get(j).toString()).floatValue(),wcf_content));  
                   }else{  
                       sheet.addCell(new Label(cols+j, row,new String(objList.get(j).toString()),wcf_content));  
                   }  
               }  
               
               row++;
           }  
           workbook.write();  
           workbook.close();     
  
          } catch (Exception e) {  
              res = -1;  
              e.printStackTrace();  
          }  
        return res;  
    }

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}  
}
