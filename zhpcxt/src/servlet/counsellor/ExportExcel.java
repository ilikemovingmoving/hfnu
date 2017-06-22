package servlet.counsellor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import bean.AllBean;
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

public class ExportExcel extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] courses=request.getParameterValues("course");
		AllBean ab=new AllBean();
		String courseIdList="";
		ArrayList list=null;
		if(courses!=null && courses.length>0){
			courseIdList=StringUtils.join(courses,",");
			list=ab.queryCourse(courseIdList);
		}else{
			return;
		}
		StringBuffer titleStr=new StringBuffer("序号,学号,姓名,获得学分");
		for (Object o : list) {
			List row=(List)o;
			String course=row.get(1)+"/"+row.get(2)+"/"+row.get(3);
			titleStr.append(","+course);
		}
		String[] title=(titleStr.toString()).split(",");
        OutputStream out = response.getOutputStream();// 取得输出流 response.reset();// 清空输出流  
        response.setContentType("application/msexcel");// 定义输出类型  
        String filename="成绩上传模板";
        filename=new String(filename.getBytes("UTF-8"), "iso-8859-1");
        response.setHeader("Content-Disposition","attachment;filename="+filename+".xls");  
        List dataList=new ArrayList();
        List rowList=new ArrayList(); 
        rowList.add(1);rowList.add("1111111111");rowList.add("张三");rowList.add(27.5);
        for (int i = 0; i < (title.length-4); i++) {
			rowList.add(100.0);
		}
        dataList.add(rowList);
        this.export_excel(title, dataList, out);  
	}
	
	public int export_excel(String[] Title, List<List<Object>> list,OutputStream out){  
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
           sheet.mergeCells(0, row, Title.length-1, 1);  
           sheet.addCell(new Label(0, 0, "成绩", wcf_center));  
           /** ***************以下是EXCEL第一行列标题********************* */  
           row+=2;
           CellView cellView=new CellView();
           cellView.setAutosize(true);
           sheet.setColumnView(0, cellView);
           //sheet.setRowView(row,row+1, false);
           for (int i = 0; i < Title.length; i++) {  
            sheet.addCell(new Label(i, row,Title[i],wcf_center));  
           }     
           /** ***************以下是EXCEL正文数据********************* */  
           row++;
           for (int i = 0; i < list.size(); i++) {  
               List<Object> objList = list.get(i);  
               for (int j = 0; j < Title.length; j++) {  
                   Object obj = objList.get(j);  
                   if (obj instanceof Integer) {  
                       sheet.addCell(new jxl.write.Number(j, row,new Integer(objList.get(j).toString()).intValue(),wcf_content));  
                   }else if(obj instanceof Double ){  
                       sheet.addCell(new jxl.write.Number(j, row,new Double(objList.get(j).toString()).doubleValue(),wcf_content));  
                   }else if(obj instanceof Float){  
                       sheet.addCell(new jxl.write.Number(j, row,new Double(objList.get(j).toString()).floatValue(),wcf_content));  
                   }else{  
                       sheet.addCell(new Label(j, row,new String(objList.get(j).toString()),wcf_content));  
                   }  
               }  
           }  
           workbook.write();  
           workbook.close();     
  
          } catch (Exception e) {  
              res = -1;  
              e.printStackTrace();  
          }  
        return res;  
    }  
}
