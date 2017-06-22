package service;

import java.util.List;

import bean.InsertUpdateDelBean;

public class UserService {

	private InsertUpdateDelBean insertUpdateDelBean=new InsertUpdateDelBean();
	
	public int del(List paramList){
		String sql=" delete from `user` where user_id=? ";
		return insertUpdateDelBean.insertANDupdateANDdel(sql, paramList);
	}
}
