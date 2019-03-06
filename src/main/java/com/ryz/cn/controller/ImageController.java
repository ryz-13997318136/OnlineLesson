package com.ryz.cn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ryz.cn.entity.PImage;
import com.ryz.cn.entity.User;
import com.ryz.cn.model.Res;
import com.ryz.cn.model.Result;
import com.ryz.cn.service.ImageService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@Resource
    private ResourceLoader resourceLoader;
	
	private  String basePath = "D:\\image\\";
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public String upload(HttpServletRequest request,@RequestParam(value="isZip",required = false) String isZip,@RequestParam("fileName") MultipartFile imageFile[]) throws UnsupportedEncodingException{
		User user = (User)request.getSession().getAttribute("user");
		for(MultipartFile file : imageFile){
			if(file.isEmpty()){
	            continue;
	        }
			
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        String location =df.format(new Date()) + System.getProperty("file.separator");
	        // 判断文件夹是否存在，不存在则
	        basePath = "all_image"+System.getProperty("file.separator");
	        File targetFile = new File(basePath + location);
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }
	        String fileName = file.getOriginalFilename();
	        if(!fileName.endsWith(".jpg")&&!fileName.endsWith(".png")){
	        	throw new RuntimeException("文件格式不支持，目前只支持 .jpg 和  .png 格式的资源");
	        }
	        fileName = new String(fileName.getBytes("ISO8859-1"),"UTF-8");
	        fileName = fileName.length()>20?fileName.substring(fileName.length()-20):fileName;
	        fileName.replace(" ", "");
	        String url ="";
	        try{
	        	File toFile = new File(fileName);
	        	file.transferTo(toFile);
	        	if("on".equals(isZip)){
	        		Thumbnails.of(toFile).size(500,600).toFile(toFile);
		    		Thumbnails.of(toFile).scale(1f).outputQuality(0.25f).toFile(toFile);
	        	}
	    		FileInputStream fi = new FileInputStream(toFile);
	            Files.copy(fi, Paths.get(basePath + location, fileName), StandardCopyOption.REPLACE_EXISTING);
	            fi.close();
	            System.out.println(toFile.delete())  ;
	            url = location + fileName;
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        PImage image = new PImage();
	        image.setId(String.valueOf(System.currentTimeMillis()));
	        image.setUserId(user.getUserId().toString());
	        image.setUploadDate(new Date());
	        image.setImageName(fileName);
	        image.setImageUrl(url);
	        imageService.save(image);
		}
		
		return "main";
	}
	
	@RequestMapping(value="/{filePath}/{filename}/",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filePath,@PathVariable String filename) throws UnsupportedEncodingException  {
        System.out.println("filePath----------------"+filePath);
        System.out.println("filename--------------"+filename);
        filename = new String(filename.getBytes("ISO8859-1"),"UTF-8");
        basePath = "all_image"+System.getProperty("file.separator");
        System.out.println("basePath-------------"+ basePath);
        ResponseEntity.ok().contentType(MediaType.IMAGE_PNG);
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(basePath +filePath+"\\"+ filename).toString()));
        }else{
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("/usr/photoalbum/all_image/" +filePath+"/"+ filename).toString()));
        }
    }
	
	@RequestMapping(value = "/getImageByUserId", method=RequestMethod.GET)
	@ResponseBody
	public Result getImageByUserId(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		List res = imageService.getImageByUserId(user.getUserId().toString());
		return new Result(Res.SUCCESS,"查询成功！",res,res.size());
	}
	
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request, @RequestBody Map<String,Object> params){
		String imageId = params.get("imageId").toString();
		imageService.delete(imageId);
		return new Result(Res.SUCCESS,"delete success!");
	}
	@RequestMapping(value="/getImageByImageId/{imageId}/",method = RequestMethod.GET)
    public String getImageByImageId(@PathVariable String imageId) throws UnsupportedEncodingException  {
		PImage image = imageService.get(imageId);
		if(image==null){
			return "";
		}
		String imageUrl = new String(image.getImageUrl().getBytes("UTF-8"),"ISO8859-1");
		return "forward:/image/"+imageUrl+"/"; 
        
    }
}
