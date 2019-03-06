package com.ryz.cn.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryz.cn.dao.Dao;

@Service
@Transactional
public class BaseService {
	@Autowired
	protected Dao dao;
	
	public String getUUID(){
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString().replace("-", "");
	}
	
	public String obj2String(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
	public boolean isBlank(Object obj){
		if(obj==null){
			return true;
		}
		if(obj instanceof String){
			if(obj.equals("")){
				return true;
			}
		}
		return false;
	}
}
