package com.ryz.cn.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryz.cn.dao.Dao;
import com.ryz.cn.entity.User;
import com.ryz.cn.model.Res;
import com.ryz.cn.model.Result;

@Service
public class UserService extends BaseService {

	
	public void saveUser(){
		  User user = new User();
	       user.setUserId(234L);
	       user.setName("234");
	       user.setAge(26L);
	       user.setPassword("password");
	       dao.save(user);
	}
	
	public User getUser(String userName,String password){
		List<Map<String,Object>> res = dao.executeQuery("select user_id  from user where name=? and password = ? ",
				new String[]{userName,password});
		if(res.size()==0){
			return null;
		}else if(res.size()==1){
			String userId = res.get(0).get("user_id").toString();
			User user = (User)dao.get(User.class, Long.valueOf(userId));
			user.setLastLoginTime(new Date());
			return user;
		}else{
			throw new RuntimeException("数据异常，该用户存在两条记录");
		}
		
	}
	
	public void save(User user,Map<String,Object> params){
		String userId = obj2String(params.get("userId"));
		if(isBlank(userId)){
			throw new RuntimeException("userId can not be empty!");
		}
		if(!user.getUserId().toString().equals(userId)){
			throw new RuntimeException("can not modify the user!");
		}
		String userName = obj2String(params.get("userName"));
		String userAge = obj2String(params.get("userAge"));
		String sex = obj2String(params.get("sex"));
		String password = obj2String(params.get("password"));
		if(!isBlank(userName)){
			user.setName(userName);
		}
		if(!isBlank(userAge)){
			user.setAge(Long.valueOf(userAge));
		}
		if(!isBlank(sex)){
			user.setSex(sex);
		}
		if(!isBlank(password)){
			user.setPassword(password);
		}
		dao.saveOrUpdate(user);
	}
}
