package bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import util.DBConn;
import util.MyStringUtils;

public class AllBean {
	SelectBean sb = new SelectBean();
	ArrayList al = null;
	
	public ArrayList getAllAdmin(String user_id){
		String sql = "select * from user where id!=1 and user_id!="+user_id+" and status='辅导员' " + "order by id desc";
		String args[] = {"user_id","name","pwd","status"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getAllCounsellor(){
		String sql = "select * from user where status='辅导员' ";
		String args[] = {"user_id","name","pwd","status"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getCounsellor(){
		String sql = "select distinct(name) from user where status='辅导员' ";
		String args[] = {"name"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getAllCounsellor(String user_id){
		String sql = "select * from user where user_id="+user_id;
		String args[] = {"user_id","name","pwd","status"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getBjs(){
		String sql = "select distinct(nj) from bj ";
		String args[] = {"nj"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getMgrClass(String name){
		String sql = "select * from class where mgr='"+name+"'";
		String args[] = {"class_id","grade","dept","pro","class","mgr"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getVerify(){
		String sql = "select * from verify order by id desc";
		String args[] = {"id","student","name","addsubtract","score","reason","dates","evaluating","state"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getVerify(String id){
		String sql = "select * from verify where id="+id;
		String args[] = {"id","student","name","addsubtract","score","reason","dates","evaluating","state"};
		al = sb.selectRow(args, sql);
		return al;
	}
	
	public ArrayList getSportsgrade(){
		String sql = "select * from sportsgrade order by id desc";
		String args[] = {"id","student","grade","dates","evaluating","state"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getSportsgradeOne(String id){
		String sql = "select * from sportsgrade where id="+id;
		String args[] = {"id","student","grade","dates","evaluating","state"};
		al = sb.selectRow(args, sql);
		return al;
	}
	
	public ArrayList getIntellectualgrade(){
		String sql = "select * from intellectualgrade order by id desc";
		String args[] = {"id","student","term","name","grade","dates","evaluating","state"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getIntellectualgradeOne(String id){
		String sql = "select * from intellectualgrade where id="+id;
		String args[] = {"id","student","term","name","grade","dates","evaluating","state"};
		al = sb.selectRow(args, sql);
		return al;
	}
	
	public ArrayList getVerify(String type,String student){
		String sql = "select * from verify where name='"+type+"' and student="+student;
		String args[] = {"id","student","name","addsubtract","score","reason","dates","evaluating","state"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getSportsgrade(String student){
		String sql = "select * from sportsgrade where student="+student;
		String args[] = {"id","student","grade","dates","evaluating","state"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getIntellectualgrade(String student){
		String sql = "select * from intellectualgrade where student="+student;
		String args[] = {"id","student","term","name","grade","dates","evaluating","state"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getMyTest(String student){
		String sql="select * from yuanjingjihua where student="+student+"order by xueqi asc";
		String args[]={"student","xueqi","text"};
		al=sb.select(sql,args);
		return al;
	}
	public ArrayList getClassScore(String class_id){
		String sql = "select * from "+class_id;
		String args[] = {"student_id","sName","class_score_table","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getMyScore(){
		String sql = "select * from score1 ";
		String args[] = {"student_id","sName","class_score_table","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getMyScore(String s){
		String sql = "select * from score1 ";
		String args[] = {"student_id","sName","class_score_table","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getStuScoreTableName(String class_id){
		String sql = "select class_score_table from class where class_id="+class_id;
		String args[] = {"class_score_table"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getStuScore(String student_id,String class_score_table){
		String sql = "select * from `" + class_score_table + "` where student_id="+student_id;
		String args[] = {"student_id","school_year","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getStuScoreOfSchoolYear(String student_id,String class_score_table,String school_year){
		String sql = "select * from `" + class_score_table + "` where student_id="+student_id + " and school_year = '" + school_year + "'";
		String args[] = {"student_id","school_year","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getStuScoreOfTerm(String student_id,String class_score_table,String school_year,String term){
		String sql = "select * from `" + class_score_table + "` where student_id="+student_id + " and school_year = '" + school_year + "' and term ='" + term + "'";
		String args[] = {"student_id","school_year","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getStuScoreOfclassId(String student_id,String class_score_table,String school_year,String term){
		String sql = "select * from `" + class_score_table + "` where student_id="+student_id + " and school_year = '" + school_year + "' and term ='" + term + "'";
		String args[] = {"student_id","school_year","term","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getStuScoreOfcourseId(String student_id,String course_id,String class_score_table){
		String sql = "select score from `" + class_score_table + "` where student_id="+student_id + " and course_id="+course_id;
		String args[] = {"score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getCourse(String course_id){
		String sql = "select * from course where id="+course_id;
		String args[] = {"id","course_name","level","grades"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList queryCourse(String courseIdList){
		//String courseIdList=paramMap.get("courseIdList")==null?"":paramMap.get("courseIdList").toString();
		if(courseIdList!=null && !"".equals(courseIdList)){
			String sql = "select * from course where id in ("+courseIdList+")";
			String[] args = {"id","course_name","level","grades"};
			al = sb.select(sql,args);
			
		}
		return al;
	}
	public ArrayList getAllCourse(String tableName){
		String sql = "select distinct(course_id) from `" + tableName + "`";
		String args[] = {"course_id"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getAllCourseSchoolYear(String tableName){
		String sql = "select distinct(school_year) from `" + tableName + "`";
		String args[] = {"school_year"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getAllCourseForSchoolYear(String tableName,String schoolYear){
		String sql = "select distinct(course_id) from `" + tableName + "` where school_year='" + schoolYear + "'";
		String args[] = {"course_id"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getAllCourseForTerm(String tableName,String schoolYear,String term){
		String sql = "select distinct(course_id) from `" + tableName + "` where term='" + term + "' and school_year = '" + schoolYear + "'";
		String args[] = {"course_id"};
		al = sb.select(sql,args);
		return al;
	}
	public List queryCourse(Map<String, Object> param){
		//List paramList=new ArrayList();
		String sql=" select * from course order by course_name desc ";
		return sb.query(sql, null);
	}
	//20170422 pxw
	public List queryScore(Map<String, Object> param){
		String tableName=(String) param.get("tableName");//==null?"":param.get("tableName").toString();
		String schoolYear=param.get("schoolYear")==null?"":param.get("schoolYear").toString();
		String term=param.get("term")==null?"":param.get("term").toString();
		List paramList=new ArrayList();
		String condition="";
		String sql = " select distinct(course_name) distinct_course from `"+tableName+"` class "
				+ "left join course on course.id=class.course_id where 1=1 ";
		if(!"".equals(schoolYear )){
			condition += " and school_year=? ";
			paramList.add(schoolYear);
		}
		if(!"".equals(term)){
			condition+=" and term=? ";
			paramList.add(term);
		}
		
		sql+=condition+" order by class.course_id desc ";
		return sb.query(sql, paramList);
	}
	
	public List queryScore2(Map<String, Object> param){
		String tableName=(String) param.get("tableName");//==null?"":param.get("tableName").toString();
		String schoolYear=param.get("schoolYear")==null?"":param.get("schoolYear").toString();
		String term=param.get("term")==null?"":param.get("term").toString();
		List paramList=new ArrayList();
		String condition="";
		String sql = " SELECT *,class.student_id class_student_id FROM `"+tableName+"` class "
				+ " left join student on student.student_id=class.student_id WHERE 1=1 ";
		if(!"".equals(schoolYear)){
			condition += " and school_year=? ";
			paramList.add(schoolYear);
		}
		if(!"".equals(term)){
			condition+=" and term=? ";
			paramList.add(term);
		}
		
		sql+=condition+" GROUP BY class.student_id ";
		return sb.query(sql, paramList);
		
	}
	
	
	public List queryScore3(Map<String, Object> param){
		String tableName=(String) param.get("tableName");//==null?"":param.get("tableName").toString();
		String schoolYear=param.get("schoolYear")==null?"":param.get("schoolYear").toString();
		String term=param.get("term")==null?"":param.get("term").toString();
		String studentId=param.get("studentId")==null?"":param.get("studentId").toString();
		List paramList=new ArrayList();
		String condition="";
		String sql = "  SELECT score FROM `"+tableName+"` class WHERE 1=1 ";
		if(!"".equals(studentId)){
			condition += " and student_id=? ";
			paramList.add(studentId);
		}
		if(!"".equals(schoolYear )){
			condition += " and school_year=? ";
			paramList.add(schoolYear);
		}
		if(!"".equals(term)){
			condition+=" and term=? ";
			paramList.add(term);
		}
		
		sql+=condition+" ORDER BY course_id DESC ";
		return sb.query(sql, paramList);
		
	}
	
	public List queryScore4(Map<String, Object> param,Connection conn){
		String tableName=(String) param.get("tableName");//==null?"":param.get("tableName").toString();
		String schoolYear=param.get("schoolYear")==null?"":param.get("schoolYear").toString();
		String term=param.get("term")==null?"":param.get("term").toString();
		String studentId=param.get("studentId")==null?"":param.get("studentId").toString();
		String courseId=param.get("courseId")==null?"":param.get("courseId").toString();
		List paramList=new ArrayList();
		String condition="";
		String sql = "  SELECT score FROM `"+tableName+"` class WHERE 1=1 ";
		if(!"".equals(studentId)){
			condition += " and student_id=? ";
			paramList.add(studentId);
		}
		if(!"".equals(schoolYear )){
			condition += " and school_year=? ";
			paramList.add(schoolYear);
		}
		if(!"".equals(term)){
			condition+=" and term=? ";
			paramList.add(term);
		}
		if(!"".equals(courseId)){
			condition+=" and course_id=? ";
			paramList.add(courseId);
		}
		
		sql+=condition;//+" ORDER BY course_id DESC ";
		return sb.query(sql, paramList,conn);
		
	}
	
	
	public List queryStuWarning2(Map<String, Object> param){
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
			List classIdList=this.queryClassIdOfStudent();
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
		// and b.sum_60 >= ?
		return sb.query(sql, paramList);
		
	}
	
	public ArrayList getCourseName(String course_id){
		String sql = "select distinct(course_name) from course where id="+course_id;
		String args[] = {"course_name"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getCourse(){
		String sql = "select * from course ";
		String args[] = {"id","course_name"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getCourseOfName(String name){
		String sql = "select id from course where course_name = '" + name + "'";
		String args[] = {"id"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getCourseScore(String course_id,String class_id){
		String sql = "select * from `"+ class_id + "` where course_id = '" + course_id + "'";
		String args[] = {"student_id","course_id","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getAllBj(){
		String sql = "select distinct class_id,grade,dept,pro,class,mgr from class ";
		String args[] = {"class_id","grade","dept","pro","class","mgr"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getBjofPro(String pro){
		String sql = "select * from class where pro='"+pro+"'";
		String args[] = {"class_id","grade","dept","pro","class","mgr"};
		al = sb.select(sql,args);
		return al;
	}
	/*public ArrayList query(String pro){
		String sql = "select * from class where pro='"+pro+"'";
		String args[] = {"class_id","grade","dept","pro","class","mgr"};
		al = sb.select(sql,args);
		return al;
	}*/
	public ArrayList getBjofPro(){
		String sql = "select distinct(pro) from class ";
		String args[] = {"pro"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getBjofGrade(String grade){
		String sql = "select * from class where grade='"+grade+"'";
		String args[] = {"class_id","grade","dept","pro","class","mgr"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getBjofGrade(){
		String sql = "select distinct(grade)  from class";
		String args[] = {"grade"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getBj(String class_id){
		String sql = "select distinct(mgr) from class where class_id='"+class_id+"'";
		String args[] = {"mgr"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getBjInfo(String class_id){
		String sql = "select * from class where class_id='"+class_id+"'";
		String args[] = {"class_id","grade","dept","pro","class","mgr"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getSzcp(String student){
		String sql = "select * from szcp where student="+student;
		String args[] = {"student","dd","xx","sz","zj","zf"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList querySzcp(String studentId){
		String sql = "select * from evaluating where student_id='"+studentId+"'";
		String args[] = {"student_id","name","school_year","daodesuyangBasic","daodesuyangPlus","daodesuyangSub","daodesuyang","studyBasic","studyPlus","studySub","studying","tizhijiankangBasic","tizhijiankangBasic","tizhijiankangPlus","tizhijiankangSub","suzhituozhan","suzhituozhanPlus","suzhituozhanSub","suzhituozhan","doPlus","doSub","doPlusOrSub","sum"};
		al = sb.select(sql,args);
		return al;
	}
	
	public ArrayList getClassesScore(String classes,String score){
		String sql = "select a.*,cast(cast(a.moral as float)*0.35+cast(a.intellectual as float)*0.5+cast(a.sports as float)*0.05+cast(a.ability as float)*0.1 as decimal(20,2)) as zhonghe,b.truename as name from score a,student b where a.student=b.id and b.classes='"+classes+"' order by "+score+" desc";
		String args[] = {"id","student","moral","intellectual","sports","ability","zhonghe","name"};
		al = sb.select(sql, args);
		return al;
	}
	

	public ArrayList getSchYear(String class_id){
		String sql = "select distinct(school_year) from `" + class_id + "`";
		String args[] = {"school_year"};
		al = sb.select(sql,args);
		return al;
	}

	public ArrayList getScoreOfSchYear(String SchYear,String tableName,String student_id){
		String sql = "select * from `" + tableName + "` where school_year='"+SchYear+"' and student_id = " + student_id;
		String args[] = {"student_id","course_id","school_grades","school_year","term","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getTerm(String class_id){
		String sql = "select distinct(term) from `" + class_id + "`";
		String args[] = {"term"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getScoreOfTerm(String term,String SchYear,String tableName,String student_id){
		String sql = "select * from `" + tableName + "` where term=" + term + " and school_year='"+SchYear+"'and student_id = " + student_id;
		String args[] = {"student_id","course_id","school_grades","school_year","term","score"};
		al = sb.select(sql,args);
		return al;
	}
	public ArrayList getClassStudent(String class_id){
		String sql = "select * from student where class_id="+class_id;
		String args[] = {"student_id","name","sex","class_id","IDCard","dept","pro","address"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getClassStudent2(String class_id){
		String sql = "select student.*,concat(grade,'级',`class`.pro,class,'班') class_name from student left join `class` on student.class_id=`class`.class_id where student.class_id="+class_id;
		String args[] = {"student_id","name","sex","class_id","IDCard","dept","pro","locate","class_name"};
		al = sb.select(sql, args);
		return al;
	}
	public Map queryClass(Map map){
		//if(map==null)
		String classno=map.get("classno")==null?"":map.get("classno")+"";//StringUtils.trimToEmpty(map.get("classno")+"");
		String grade=MyStringUtils.null2Str(map.get("classGrade"));//StringUtils.trimToEmpty(map.get("classGrade")+"");
		String pro=MyStringUtils.null2Str(map.get("classPro"));//StringUtils.trimToEmpty(map.get("classPro")+"");
		String sql="select *,concat(grade,'级',pro,class,'班') class_name,concat(pro,class) pro_class from class where 1=1 ";
		List paramList = new ArrayList();
		if(!"".equals(classno)){
			sql += " and class_id=? ";
			paramList.add(classno);
		}
		if(!"".equals(grade)){
			sql += " and grade=? ";
			paramList.add(grade);
		}
		if(!"".equals(pro)){
			sql += " and pro=? ";
			paramList.add(pro);
		}
		List list=sb.query(sql, paramList);
		String className="";
		if(list!=null && list.size()>0){
			className=((Map)list.get(0)).get("class_name")+"";
		}
		Map<String,Object> res=new HashMap<String, Object>();
		res.put("className",className);
		res.put("list",list);
		return res;
	}
	public List queryClassStudent(String class_id){
		String sql = " select *,concat(province,city,distrinct,town) addr from student where class_id=? ";
		//String args[] = {"student_id","name","sex","class_id","IDCard","dept","pro","address"};
		//al = sb.select(sql, args);
		List paramList=new ArrayList();
		paramList.add(class_id);
		return sb.query(sql, paramList);
	}
	public ArrayList getStudentId(String class_id,String SchYear){
		String sql = "select distinct(student_id) from `" + class_id + "` where school_year='"+SchYear+"'";
		String args[] = {"student_id"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getStudentId(String class_id,String SchYear,String term){
		String sql = "select distinct(student_id) from `" + class_id + "` where school_year='"+SchYear+"' and term = '" + term +"'";
		String args[] = {"student_id"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getStudent(){
		String sql = "select * from student";
		String args[] = {"student_id","name","sex","class_id","IDCard","dept","pro","address"};
		al = sb.select(sql, args);
		return al;
	}
	
	public ArrayList getStudent(String student_id){
		String sql = "select * from student where student_id="+student_id;
		String args[] = {"student_id","name","sex","class_id","IDCard","dept","pro","address"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getStudentOfKeys(String keys){
		String sql = "select * from student where student_id like '%" + keys + "%' or name like '%" + keys + "%'";
		String args[] = {"student_id","name","sex","class_id","IDCard","dept","pro","address"};
		al = sb.select(sql, args);
		return al;
	}
	//public Map queryAllStudent(String keys,String strPageIndex,String strPageSize){
	public Map queryAllStudent(String keys,Integer pageIndex,Integer pageSize){
		//Integer pageIndex=Integer.parseInt(strPageIndex);
		//Integer pageSize=Integer.parseInt(strPageSize);
		String sql = " select student.*,concat(student.pro,class,'班') class_name from student left join class on class.class_id=student.class_id where 1=1 ";
		String sqlCount = " select count(0) count from student where 1=1 ";
		String sqlCondition = "";
		List paramList = new ArrayList();
		if(keys!=null && !"".equals(keys)){
			sqlCondition += " and student_id=? or name like ? ";
			paramList.add(keys);	paramList.add("%"+keys+"%");
			
		}
		
		sql += sqlCondition + " limit " + (pageIndex-1)*pageSize + "," + pageSize;
		sqlCount += sqlCondition;
		Map map=new HashMap();
		Connection conn=DBConn.getConn();
		//List resultList=sb.query(sql, paramList,conn);
		map.put("list", sb.query(sql, paramList,conn));
		//map.put("list", resultList);
		map.put("count", sb.queryCount(sqlCount, paramList,conn));
		DBConn.close(conn, null, null);
		return map;
	}
	public ArrayList getEvaluating(String student_id,String school_year,String tableName){
		String sql = "select distinct(school_year) from evaluating where school_year='" + school_year + "' and class_id = '" + tableName + "' and student_id =" + student_id;
		String args[] = {"school_year"};
		al = sb.selectRow(args, sql);
		return al;
	}
	public ArrayList getSchYearOfEvaluating(String classId){
		String sql = "select distinct(school_year) from evaluating where  class_id = '" + classId + "'";
		String args[] = {"school_year"};
		al = sb.select(sql,args);
		return al;
	}
	
	public List querySchYearOfEvaluating(String classId){
		String sql = "select distinct(school_year) from evaluating where class_id=? ";
		List paramList=new ArrayList();		paramList.add(classId);
 		/*String args[] = {"school_year"};
		al = sb.selectRow(args, sql);*/
		
		return sb.query(sql, paramList);
	}
	
	public ArrayList getEvaluating(String class_id){
		String sql = "select * from evaluating where class_id='" + class_id + "' order by sum desc";
		String args[] = {"student_id","name","school_year","daodesuyangBasic","daodesuyangPlus","daodesuyangSub","daodesuyang","studyBasic","studyPlus","studySub","studying","tizhijiankangBasic","tizhijiankangBasic","tizhijiankangPlus","tizhijiankangSub","suzhituozhan","suzhituozhanPlus","suzhituozhanSub","suzhituozhan","doPlus","doSub","doPlusOrSub","sum"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getEvaluating2(String class_id,String schoolYear){
		/*if(class_id!=null && !"".equals(class_id)){
			
		}*/
		String sql = "select * from evaluating where class_id='" + class_id + "' and school_year='"+schoolYear+"' order by sum desc";
		String args[] = {"student_id","name","school_year","daodesuyangBasic","daodesuyangPlus","daodesuyangSub","daodesuyang","studyBasic","studyPlus","studySub","studying","tizhijiankangBasic","tizhijiankangBasic","tizhijiankangPlus","tizhijiankangSub","suzhituozhan","suzhituozhanPlus","suzhituozhanSub","suzhituozhan","doPlus","doSub","doPlusOrSub","sum"};
		al = sb.select(sql, args);
		return al;
	}
	public ArrayList getAllclassId(){
		String sql = "select distinct(class_id) from student";
		String args[] = {"class_id"};
		al = sb.select(sql, args);
		return al;
	}
	//20170426
	public List queryClassIdOfStudent(){
		String sql = "select distinct(class_id) class_id from student";
		//String args[] = {"class_id"};
		//al = sb.select(sql, args);
		List list = sb.query(sql, null);
		return list;
	}
	
	public ArrayList getAllSchoolYear(String class_id){
		String sql = "select distinct(school_year) from `" + class_id + "`";
		String args[] = {"school_year"};
		al = sb.select(sql, args);
		return al;
	}
	
	public List queryAllSchoolYear(List classIdList){
		String sql = "select distinct(school_year) school_year from ( ";
        for (int i = 0; i< classIdList.size(); i++) {
        	
        	String classId = ((ArrayList) classIdList.get(i)).get(0).toString();
        	if(i==0){
        		sql += " select school_year from `"+classId+"` ";
        	}else{
        		sql += " union select school_year from `"+classId+"` ";
        	}
        }
        
        sql +=") a ";
		//String args[] = {"school_year"};
		List list = sb.query(sql, null);
		return list;
	}
	
	
	
	//20170426
	public List querySchoolYearOfClass(String class_id){
		String sql = "select distinct(school_year) school_year from `" + class_id + "`";
		/*String args[] = {"school_year"};
		al = sb.select(sql, args);*/
		List list=sb.query(sql, null);
		return list;
	}
	
	public List queryStuWarning2(Map param, Connection conn) {
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
		//condition2
		if(!"".equals(noQuality)){
			condition2 += " and b.sum_60 >= ?";
			paramList.add(noQuality);
		}
		condition += " AND CAST(score AS DECIMAL(3,1))<60.0 ";
		
		String sql =" select * from (select student.`student_id`,student.`name`,student.`sex`,student.`class_id`,grade,CONCAT( grade,'级',class.dept,class.pro,class,'班') class_name,class.`dept`,class.`pro`,COUNT(0) sum_60 "
				+ "  from (";
		if(tableName==null || "".equals(tableName)){
			List classIdList=this.queryClassIdOfStudent();
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
				+ ") b where 1=1 " + condition2 + "order by student_id asc ";
		// and b.sum_60 >= ?
		return sb.query(sql, paramList,conn);
	}
	
	public Map queryStuWarning3(Map param,Connection conn){
		String grade=param.get("grade")==null?"":param.get("grade").toString();
		String pro=param.get("pro")==null?"":param.get("pro").toString();
		String cls=param.get("class")==null?"":param.get("class").toString(); 
		List paramList=new ArrayList();
		String condition="";
		//String condition2="";
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
		
		//condition2 += " AND CAST(score AS DECIMAL(3,1))<60.0 ";
		
		String classSql="select class_id from class where 1=1 " + condition;
		List classList=sb.query(classSql, paramList, conn);
		String classId="";
		if(classList!=null && classList.size()>0){
			classId=MyStringUtils.null2Str(((Map)classList.get(0)).get("class_id"));
			
		}
		if("".equals(classId)){
			return new HashMap();
		}
		String studentSql="select count(0) count from student where class_id= " + classId;
		long count=sb.queryCount(studentSql, null,conn);
		String sql =" select * from (select student.`student_id`,student.`name`,student.`sex`,student.`class_id`,grade,CONCAT( grade,'级',class.dept,class.pro,class,'班') class_name,class.`dept`,class.`pro`,class.`mgr`,COUNT(0) sum_60 "
				+ "  from ("
				+ " SELECT CASE score WHEN '优秀' THEN 90 WHEN '良好' THEN 80 WHEN '中等' THEN 70 WHEN '合格' THEN 60 WHEN '不合格' THEN 50 ELSE score END score,student_id,school_year,term  FROM `"+classId+"` "
				+" ) a LEFT JOIN student ON student.`student_id`=a.student_id "
				+ " LEFT JOIN class ON class.class_id=student.class_id where CAST(score AS DECIMAL(3,1))<60.0 "//where 1=1 " + condition2
				+ " and class.class_id= " + classId
				+ " GROUP BY student.`student_id` having student.`student_id` is not null "
				+ ") b order by student_id asc ";
		// and b.sum_60 >= ?
		Map res=new HashMap();
		res.put("count", count);
		res.put("list", sb.query(sql, null,conn));
		return res;
	}
	
}
