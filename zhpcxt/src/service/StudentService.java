package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.DBConn;
import util.ReadExcel;
import bean.AllBean;
import bean.InsertUpdateDelBean;
import bean.SelectBean;

public class StudentService {

	//SelectBean selectBean=new SelectBean();
	
	public boolean upload(File file,String fileFileName,String class_id){
		InputStream is=null;
		Connection conn=DBConn.getConn();
		try {
			is=new FileInputStream(file);	
			ReadExcel readExcel = new  ReadExcel(2,1,17);
			List<Object> list=readExcel.readExcel(is,fileFileName);
			if(list!=null && list.size()>0){
				//String cols=" `name`,`student_id`,`sex`, `address`,`pro`,`IDCard`,`bank_card`,`parent_name`,`parent_phone`,`phone`,`room_id`,`learning_status`,`reward`,`breach`,`punish`,`remark`,class_id";
				String cols=" `name`,`student_id`,`sex`,`locate`, `province`,`city`,`distrinct`,`town`,`pro`,`IDCard`,`bank_card`,`parent_name`,`parent_phone`,`phone`,`room_id`,class_id";
				String cols2=" `learning_status`,`reward`,`breach`,`punish`,`remark`,`student_id` ";
				String sql=" insert into student("+cols+") values ";
				String sql2=" insert into situation("+cols2+") values ";
				List<Object> paramList=new ArrayList<Object>();
				List<Object> paramList2=new ArrayList<Object>();
				for (Object row : list) {//每条记录
					List rowList=(List)row;
					sql += "(";			sql2+="(";
					//更新student表
					int rowLen=rowList.size();
					for(int j=0;j<rowLen;j++){
						//int i=0;//代表第几个字段
						//int len=cols.split(",").length-1;
						//for (i=0;i<len;i++) {//某条记录的每个字段
						if(j<=10){//Excel表格每行前11个为学生基本信息，后面的为学生在校表现
							sql+="?,";
							paramList.add(rowList.get(j));
							String locate=(String) rowList.get(j);
							if(j==3){//locate在cols的位置
								sql+="?,?,?,?,";
								String province="";
								String city="";
								String distrinct="";
								String town="";
								int indexOf=0;
								if(locate.indexOf("省")!=-1){
									province=locate.substring(0,locate.indexOf("省"))+"省";
									indexOf=locate.indexOf("省");
									if(locate.indexOf("市")!=-1){
										city=locate.substring(indexOf+1,locate.indexOf("市"))+"市";
										indexOf=locate.indexOf("市");
										int distrinctIndexOf=0;
										if((distrinctIndexOf=locate.indexOf("区"))!=-1 && locate.indexOf("区")>indexOf
												|| (distrinctIndexOf=locate.indexOf("县"))!=-1 && locate.indexOf("县")>indexOf){
											//区或县名+“区”或“县”
											distrinct=locate.substring(indexOf+1,distrinctIndexOf)+locate.substring(distrinctIndexOf,distrinctIndexOf+1);
											indexOf=distrinctIndexOf;
											//System.out.println(locate.indexOf("镇"));
											if(locate.indexOf("镇")!=-1 && locate.indexOf("镇")>indexOf){//&&后面避免镇名含“区”字,indexOf+1<"镇"的位置
												town=locate.substring(indexOf+1,locate.indexOf("镇"))+"镇";
											}
										}
									}
								}
								
								paramList.add(province);
								paramList.add(city);
								paramList.add(distrinct);
								paramList.add(town);
								//i+=4;
							}
							
							if(j==10){
								sql+=" ?),";		paramList.add(class_id);
							}
						}else{
							sql2+="?,";
							paramList2.add(rowList.get(j));	
							
							if(j==rowLen-1){
								sql2+="?),"; 		
								paramList2.add(rowList.get(1));
							}
						}
						//更新situation表
						//int len2=cols2.split(",").length-1;
						//for(;i<len+len2;i++){
						/*for(;i<len+len2;i++){
							sql2+="?,";
							paramList2.add(rowList.get(j));	
						}*/
						//sql2+="?),"; 		paramList2.add(rowList.get(1));			
					}
					
				}
				sql=sql.substring(0, sql.length()-1);
				sql2=sql2.substring(0, sql2.length()-1);
				InsertUpdateDelBean insertUpdateDelBean=new InsertUpdateDelBean();
				
				conn.setAutoCommit(false);
				int c=insertUpdateDelBean.insertANDupdateANDdel(sql,paramList,conn);
				int c2=insertUpdateDelBean.insertANDupdateANDdel(sql2,paramList2,conn);
				
				boolean flag=true;
				if(c*c2>0){
					//return true;
					flag=true;
					
				}else{
					//return false;
					flag=false;
					conn.rollback();
				}
				conn.commit();
				conn.setAutoCommit(true);
				return flag;
			}else{
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBConn.close(conn, null, null);
		}
		
		return false;
	}
	
	public boolean del(String studentIdList) throws SQLException{
		InsertUpdateDelBean insertUpdateDelBean=new InsertUpdateDelBean();
		//String str[]=studentIdList.split(",");
		Connection conn=DBConn.getConn();
		conn.setAutoCommit(false);
		String sql="delete from student where student_id in ("+studentIdList+")";
		String sql2="delete from situation where student_id in ("+studentIdList+")";
		int c=insertUpdateDelBean.insertANDupdateANDdel(sql,conn);
		int c2=insertUpdateDelBean.insertANDupdateANDdel(sql2,conn);
		conn.commit();
		conn.setAutoCommit(true);
		if(c*c2>0){
			return true;
		}else{
			return false;
		}
	}

	//不及格
	public List<Map> scoreAnalyze(Map param){
		AllBean ab=new AllBean();
		List res=ab.queryStuWarning2(param);
		long lows[]=new long[11];
		long sum=0;
		for (Object object : res) {
			Map item=(Map)object;
			switch(Integer.parseInt(item.get("sum_60")+"")){
			//case 0:lows[0]++;break;
			case 1:lows[1]++;sum++;break;
			case 2:lows[2]++;sum++;break;
			case 3:lows[3]++;sum++;break;
			case 4:lows[4]++;sum++;break;
			case 5:lows[5]++;sum++;break;
			case 6:lows[6]++;sum++;break;
			case 7:lows[7]++;sum++;break;
			case 8:lows[8]++;sum++;break;
			case 9:lows[9]++;sum++;break;
			case 10:lows[10]++;sum++;break;
			}
		}
		
		List list=new ArrayList();
		for (int i = 1; i < lows.length; i++) {
			Map map=new HashMap();
			map.put("name",i+"门" );
			map.put("y",100.0*lows[i]/sum);
			list.add(map);
		}
		return list;
	}
	
	public List querySituation(Map<String, Object> map) {
		//map.get("pro");
		SelectBean sb=new SelectBean();
		String studentId=map.get("studentId")+"";
		String sqlCondition=" and student_id=? ";
		List paramList = new ArrayList();
		paramList.add(studentId);
		//String classId=map.get("studentId")+"";
		String sql="select * from situation where 1=1 " + sqlCondition;
		return sb.query(sql, paramList);
	}
	/*series: [{
    name: 'Brands',
    colorByPoint: true,
    data: [{
        name: 'Microsoft Internet Explorer',
        y: 56.33,
        drilldown: 'Microsoft Internet Explorer'
    }, {
        name: 'Chrome',
        y: 24.03,
        drilldown: 'Chrome'
    }]
}]*/
	/*
	 * drilldown: {
        series: [{
            name: 'Microsoft Internet Explorer',
            id: 'Microsoft Internet Explorer',
            data: [
                ['v11.0', 24.13],
                ['v8.0', 17.2],
                ['v9.0', 8.11],
                ['v10.0', 5.33],
                ['v6.0', 1.06],
                ['v7.0', 0.5]
            ]
        }, 
	 */
	public Map queryAddress(Map param) throws UnsupportedEncodingException {
		String province=param.get("province")==null?"":param.get("province").toString();
		String city=param.get("city")==null?"":param.get("city").toString();
		String distrinct=param.get("distrinct")==null?"":param.get("distrinct").toString();
		String grade=param.get("grade")==null?"":param.get("grade").toString();
		String pro=param.get("pro")==null?"":param.get("pro").toString();//new String(param.get("pro").toString().getBytes("ISO-8859-1"),"utf-8");
		String cls=param.get("cls")==null?"":param.get("cls").toString();
		String condition="";
		//String condition2="";
		List paramList=new ArrayList();
		//List paramList2=new ArrayList();
		
		if(!"".equals(grade)){
			condition += " and grade=? ";
			paramList.add(grade);
		}
		if(!"".equals(pro)){
			condition += " and student.pro=? ";
			paramList.add(pro);
		}
		if(!"".equals(cls)){
			condition += " and class=? ";
			paramList.add(cls);
		}
		if(!"".equals(province)){
			condition += " and province=? ";
			paramList.add(province);
		}
		if(!"".equals(city)){
			condition += " and city=? ";
			paramList.add(city);
		}
		if(!"".equals(distrinct)){
			condition += " and distrinct=? ";
			paramList.add(distrinct);
		}

		SelectBean sb=new SelectBean();
		Map res=new HashMap();
				
		Connection conn_=DBConn.getConn();
		String sql="";
		String allSql="SELECT COUNT(0) AS `count` FROM student left join class on class.class_id=student.class_id where 1=1 " + condition;
		String provinceSql=" SELECT province addr_name,COUNT(0) c FROM student left join class on class.class_id=student.class_id where 1=1 " + condition + " GROUP BY province HAVING province IS NOT NULL ";
		String citySql="SELECT city addr_name,COUNT(0) c FROM student left join class on class.class_id=student.class_id WHERE 1=1 " + condition + " GROUP BY city  HAVING city IS NOT NULL ";
		String distrinctSql="SELECT distrinct addr_name,COUNT(0) c FROM student left join class on class.class_id=student.class_id WHERE 1=1 " + condition + " GROUP BY distrinct  HAVING distrinct IS NOT NULL ";
		String townSql="SELECT town addr_name,COUNT(0) c FROM student left join class on class.class_id=student.class_id WHERE 1=1 " + condition + " GROUP BY town  HAVING town IS NOT NULL ";
		Integer countAll=sb.queryCount(allSql, paramList, conn_);// all student
		
		if("".equals(province) && "".equals(city) && "".equals(distrinct)){
			sql=provinceSql;
		}else
		if(!"".equals(province) && "".equals(city) && "".equals(distrinct)){
			sql=citySql;
		}else
		if(!"".equals(city) && "".equals(distrinct)){
			sql=distrinctSql;
		}else
		if(!"".equals(distrinct)){
			sql=townSql;
		}
		
		List list=sb.query(sql, paramList, conn_);
		List list2=new ArrayList();
		//List cityList3=new ArrayList();
		for (Object object : list) {
			Map item=(Map)object;
			Map jsonItem=new LinkedHashMap();
			//jsonItem.put("name", "'"+item.get("province")+"'");
			jsonItem.put("name", item.get("addr_name"));
			jsonItem.put("num", Integer.parseInt(item.get("c")+""));
			jsonItem.put("y", 100*Double.parseDouble(item.get("c")+"")/countAll);
			//jsonItem.put("drilldown", "'"+item.get("province")+"'");
			jsonItem.put("drilldown", item.get("addr_name"));
			list2.add(jsonItem);
			
			
			/*String citySql="SELECT city,COUNT(0) c FROM student WHERE province = ? GROUP BY city  HAVING city IS NOT NULL ";
			List paramList=new ArrayList();
			paramList.add(item.get("province"));
			//String[] args={"city","c"};
			List cityList=sb.query(citySql, paramList, conn_);
			List cityList2=new ArrayList();
			for (Object object2 : cityList) {
				Map item2=(Map)object2;
				List tempList=new ArrayList();
				tempList.add(item2.get("city"));
				tempList.add(100*Double.parseDouble(item2.get("c")+"")/Integer.parseInt(item.get("c")+""));
				cityList2.add(tempList);
				
			}
			Map map=new LinkedHashMap();
			map.put("name", item.get("province"));
			//map.put("name", item.get("province"));
			//map.put("id", "'"+item.get("province")+"'");
			map.put("id", item.get("province"));
			map.put("data",cityList2);
			cityList3.add(map);*/
			
		}
		
		DBConn.close(conn_, null, null);
		res.put("list", list2);
		//res.put("city", cityList3);
		return res;
	}

	public Map stuWarningbyTime(){
		Map res=new HashMap();
		AllBean ab=new AllBean();
		ClassService classService=new ClassService();
		//List list=ab.queryStuWarning2(param);
		Connection conn=DBConn.getConn();
		List classList=classService.queryClass(conn);//ab.queryClassIdOfStudent();
		List gradeList=classService.queryDistinctGrade(conn);//ab.queryClassIdOfStudent();
		List seriesList=new ArrayList();
		for (Object object : classList) {
			Map it=(Map)object;
			String[] proClass=(""+it.get("pro_class")).split(",");
			String name=proClass[0]+proClass[1]+"班";
			Map series=new HashMap();
			series.put("name", name);
			//series.put("cslor", "潘锡文");
			List dataList=new ArrayList();
			
			Map param=new HashMap();
			param.put("pro", proClass[0]);
			param.put("class", proClass[1]);
			for (Object object2 : gradeList) {
				param.put("grade", ((ArrayList)object2).get(0)+"");
				Map map=ab.queryStuWarning3(param, conn);//queryStuWarning2(param,conn);
				List list=map.get("list")==null?new ArrayList():(List)map.get("list");
				long count=map.get("count")==null?0:Long.parseLong(map.get("count")+"");
				long warningNum=0;
				String mgr="";
				for (Object object3 : list) {
					Map item=(Map)object3;
					mgr=item.get("mgr")+"";
					if(Long.parseLong(item.get("sum_60")+"")>=4){
						warningNum++;
					}
				}
				//System.out.println(100.0*warningNum/list.size());
				dataList.add(list.size()==0?0:100.0*warningNum/count);
			}
			
			series.put("data", dataList);
			seriesList.add(series);
		}
		
		DBConn.close(conn, null, null);
		res.put("grade", gradeList);
		res.put("stuWarning", seriesList);
		return res;
	}

}
