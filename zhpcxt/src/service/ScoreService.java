package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.AllBean;
import bean.SelectBean;

public class ScoreService {

	SelectBean sb=new SelectBean();
	AllBean ab=new AllBean();
	
	
	public ArrayList queryStuWarning4(Map<String, Object> param){
		String tableName=(String) param.get("tableName");//==null?"":param.get("tableName").toString();
		String grade=param.get("grade")==null?"":param.get("grade").toString();
		String pro=param.get("pro")==null?"":param.get("pro").toString();
		String cls=param.get("class")==null?"":param.get("class").toString();
		String schoolYear=param.get("schoolYear")==null?"":param.get("schoolYear").toString();
		String term=param.get("term")==null?"":param.get("term").toString();
		String noQuality=param.get("noQuality")==null?"":param.get("noQuality").toString();//new Integer(4):Integer.parseInt(param.get("noQuality").toString());
		String classId=param.get("classId")==null?"":param.get("classId").toString();//new Integer(4):Integer.parseInt(param.get("noQuality").toString());
		List paramList=new ArrayList();
		String condition="";
		String condition2="";
		if(!"".equals(grade)){
			condition += " and class.grade=? ";
			paramList.add(grade);
		}
		if(!"".equals(pro)){
			condition += " and class.pro=? ";
			paramList.add(pro);
		}
		if(!"".equals(cls)){
			condition += " and class.class=? ";
			paramList.add(cls);
		}
		if(!"".equals(schoolYear)){
			condition += " and school_year=? ";
			paramList.add(schoolYear);
		}
		if(!"".equals(term)){
			condition+=" and term=? ";
			paramList.add(term);
		}
		if(!"".equals(classId)){
			condition += " and class.class_id = ?";
			paramList.add(classId);
		}
		if(!"".equals(noQuality)){
			condition2 += " and b.sum_60 >= ?";
			paramList.add(noQuality);
		}
		condition += " AND CAST(score AS DECIMAL(3,1))<60.0 ";
		
		String sql =" select * from (select student.`student_id`,student.`name`,student.`sex`,student.`class_id`,grade,CONCAT( grade,'级',class.pro,class,'班') class_name,class.`dept`,class.`pro`,COUNT(0) sum_60 "
				+ "  from (";
		if(tableName==null || "".equals(tableName)){
			List classIdList=ab.queryClassIdOfStudent();
			int size=classIdList.size();
			for(int i=0;i<size;i++){
				Object o= classIdList.get(i); 
				if(o!=null && ((Map)o).get("class_id")!=null){
					if(i==0){
						sql+= " SELECT CASE score WHEN '优秀' THEN 90 WHEN '良好' THEN 80 WHEN '中等' THEN 70 WHEN '合格' THEN 60 WHEN '不合格' THEN 50 ELSE score END score,student_id,school_year,term "
								+ " FROM `"+((Map)o).get("class_id").toString()+"` ";
					}else{
						sql+=" union all SELECT CASE score WHEN '优秀' THEN 90 WHEN '良好' THEN 80 WHEN '中等' THEN 70 WHEN '合格' THEN 60 WHEN '不合格' THEN 50 ELSE score END score,student_id,school_year,term "
								+ " FROM `"+((Map)o).get("class_id").toString()+"` ";
					}
				}
			}
			
		
		}else{
			sql += " SELECT CASE score WHEN '优秀' THEN 90 WHEN '良好' THEN 80 WHEN '中等' THEN 70 WHEN '合格' THEN 60 WHEN '不合格' THEN 50 ELSE score END score,student_id,school_year,term  FROM `"+tableName+"` ";
		}
		
		sql+=" ) a LEFT JOIN student ON student.`student_id`=a.student_id "
				+ " LEFT JOIN class ON class.class_id=student.class_id where 1=1 " + condition
				+ " GROUP BY student.`student_id` having student.`student_id` is not null "
				+ ") b where 1=1 " + condition2 + " order by student_id asc ";
		String[] args={"student_id","name","sex","class_name","sum_60"};
		// and b.sum_60 >= ?
		return sb.select2(sql, args,paramList);
		
	}
	
	public static void main(String[] args) {
		ArrayList al=new ScoreService().queryStuWarning4(new HashMap<String, Object>());
		for (int i=0;i<al.size();i++) {
			ArrayList al2=(ArrayList)al.get(i);
			for (int j = 0; j < al2.size(); j++) {
				System.out.println(al2.get(j)+"");
				
			}
		}
	}
}
