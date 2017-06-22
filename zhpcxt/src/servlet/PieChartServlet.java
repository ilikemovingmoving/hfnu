package servlet;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String sumStr=request.getParameter("sumStr");
		String title=new String(StringUtils.trimToEmpty(request.getParameter("title")).getBytes("ISO-8859-1"),"UTF-8");
		String[] sum={};
		if(sumStr!=null && !"".equals(sumStr)){
			sum=sumStr.split(",");
		}
		//System.out.println("学生总数："+sum.length);
		Double[] lows=new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};//声明为double考虑算比例取小数
		for (int j = 0; j < sum.length; j++) {
			switch(Integer.parseInt(sum[j])){
				case 0:lows[0]++;break;
				case 1:lows[1]++;break;
				case 2:lows[2]++;break;
				case 3:lows[3]++;break;
				case 4:lows[4]++;break;
				case 5:lows[5]++;break;
				case 6:lows[6]++;break;
				case 7:lows[7]++;break;
				case 8:lows[8]++;break;
				case 9:lows[9]++;break;
				case 10:lows[10]++;break;
			}
		}
        DefaultPieDataset dataType = new DefaultPieDataset();  
        for (int i = 1; i < lows.length; i++) {
        	//System.out.println(i+"门"+lows[i]);
        	dataType.setValue(i+"门", lows[i]);  
		}
        try {  
            DefaultPieDataset data = dataType;  
            PiePlot plot = new PiePlot(data);  
            JFreeChart chart = new JFreeChart(  
                    title,              
                    JFreeChart.DEFAULT_TITLE_FONT,   
                    plot,                          
                    true                             
            );  
            
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            		//"{0}-{1}人-({2})",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
            		"{1}人-({2})",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
            chart.setBackgroundPaint(Color.LIGHT_GRAY);  
            chart.setBorderVisible(true);  
            Font kfont = new Font("宋体", Font.PLAIN, 12);     
            Font titleFont = new Font("宋体", Font.BOLD, 25);   
            // 图片标题  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
            // 底部  
            //chart.getLegend().setItemFont(kfont);  
            ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
                    chart, 500, 300, null);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
}
