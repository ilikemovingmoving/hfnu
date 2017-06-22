package util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.Student;


public class ReadExcel {
    
	private int contentRowStart;
	//private int contentRowEnd;
	private int contentColStart;
	private int contentColEnd;
	
	/**
	 * 
	 * @param a 起始行
	 * @param b 起始列
	 * @param c 结束列
	 */
	public ReadExcel(int a,int b,int c){
		this.contentRowStart=a;
		this.contentColStart=b;
		this.contentColEnd=c;
	}
	
	public List<Object> readExcel(InputStream is,String fileName) throws IOException {
		if (fileName == null || Common.EMPTY.equals(fileName)) {
			return null;
		} else {
			String postfix = Util.getPostfix(fileName);
			if (!Common.EMPTY.equals(postfix)) {
				if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
					return readXls(is,fileName);
				} else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
					return readXlsx(is,fileName);
				}
			} else {
				//System.out.println(path + Common.NOT_EXCEL_FILE);
			}
		}
		return null;
	}

    
    public List<Object> readXlsx(InputStream is,String fileName) throws IOException {
        //System.out.println(Common.PROCESSING + fileName);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<Object> list = new ArrayList<Object>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = contentRowStart; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null && xssfRow.getLastCellNum()==contentColEnd) {
                	List<Object> rowList=new ArrayList<Object>();
        			//for (int i = 1; i < hssfRow.getLastCellNum(); i++) {
        			for (int i = contentColStart; i < contentColEnd; i++) {
        				rowList.add(getValue2(xssfRow.getCell(i)));
    				}
        			list.add(rowList);
                }
            }
        }
        return list;
    }


    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<Object> readXls(InputStream is,String fileName) throws IOException {
    	//System.out.println(Common.PROCESSING + path);
    	//InputStream is = new FileInputStream(path);
    	HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
    	List<Object> list = new ArrayList<Object>();
    	// Read the Sheet
    	for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
    		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
    		if (hssfSheet == null) {
    			continue;
    		}
    		// Read the Row
    		for (int rowNum = contentRowStart; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
    			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
    			//Map<String, Object> item=new HashMap<String, Object>();
    			List<Object> rowList=new ArrayList<Object>();
    			//for (int i = 1; i < hssfRow.getLastCellNum(); i++) {
    			for (int i = contentColStart; i < contentColEnd; i++) {
    				rowList.add(getValue2(hssfRow.getCell(i)));
				}
    			list.add(rowList);
    		}
    	}
    	return list;
    }

    @SuppressWarnings("static-access")
    private String getValue2(XSSFCell xssfRow) {
    	if(xssfRow!=null){
    		//xssfRow.setCellType(xssfRow.CELL_TYPE_STRING);
    		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
    			return String.valueOf(xssfRow.getBooleanCellValue());
    		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
    			DecimalFormat df=new DecimalFormat("0");
    			String s=df.format(xssfRow.getNumericCellValue());
    			return s;
    		} else {
    			return String.valueOf(xssfRow.getStringCellValue());
    		}
    		
    	}else{
    		return "";
    	}
    }

    @SuppressWarnings("static-access")
    private String getValue2(HSSFCell hssfCell) {
    	//hssfCell.setCellType(hssfCell.CELL_TYPE_STRING);
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
        	DecimalFormat df=new DecimalFormat("0");
        	String s=df.format(hssfCell.getNumericCellValue());
            return s;
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
