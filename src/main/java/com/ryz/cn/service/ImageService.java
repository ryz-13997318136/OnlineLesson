package com.ryz.cn.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryz.cn.entity.PImage;

@Service
@Transactional
public class ImageService  extends BaseService {
	public void save(PImage image){
		dao.save(image);
    }
	
	public List getImageByUserId(String userId){
		String hql = "select image from PImage image where image.userId=?";
		return dao.HqlQuery(PImage.class, hql, new Object[]{userId});
	}
	public void delete(String imageId){
		Object obj = dao.get(PImage.class, imageId);
		if(obj != null){
			PImage image = (PImage)obj;
			dao.delete(image);
		}
	}
	public PImage get(String imageId){
		Object obj = dao.get(PImage.class, imageId);
		if(obj != null){
			return (PImage)obj;
		}else{
			return null;
		}
	}
}
