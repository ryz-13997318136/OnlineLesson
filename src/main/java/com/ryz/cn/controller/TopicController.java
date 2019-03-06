package com.ryz.cn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ryz.cn.entity.Question;
import com.ryz.cn.entity.User;
import com.ryz.cn.model.Res;
import com.ryz.cn.model.Result;
import com.ryz.cn.model.TopicForm;
import com.ryz.cn.service.TopicService;

@Controller
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	TopicService topicService;
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody TopicForm topicForm){
		User user = (User)request.getSession().getAttribute("user");
		topicForm.setUserId(String.valueOf(user.getUserId()));
		topicService.saveTopic(topicForm);
		return new Result(Res.SUCCESS,"Save Success !");
	}
	
	@RequestMapping(value = "/search", method=RequestMethod.GET)
	@ResponseBody
	public Result search(HttpServletRequest request, @RequestParam String groupNo, @RequestParam String topicType,
			@RequestParam String keyword, @RequestParam String point,@RequestParam int pageSize,@RequestParam int index){
		User user = (User)request.getSession().getAttribute("user");
		Map<String,Object> res = topicService.search(user.getUserId(),groupNo,topicType,keyword,point,pageSize,index);
		return new Result(Res.SUCCESS,"查询成功！",res.get("list"),Integer.parseInt(res.get("count").toString()));
	}
	
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request, @RequestBody Map<String,Object> params){
		User user = (User)request.getSession().getAttribute("user");
		String topicId = params.get("topicId").toString();
		topicService.delete(user.getUserId(),topicId);
		return new Result(Res.SUCCESS,"delete success!");
	}
	
	@RequestMapping(value = "/getTopic", method=RequestMethod.GET)
	@ResponseBody
	public Result show(HttpServletRequest request,@RequestParam String groupNo, @RequestParam(required=false) String topicId,ModelMap model){
		User user = (User)request.getSession().getAttribute("user");
		List<Question> list = new ArrayList<Question>();
		if(topicId==null||topicId.equals("")){
			list = topicService.searchByGroup(user.getUserId(),groupNo);
		}else{
			list = topicService.searchById(topicId);
		}
		return new Result(Res.SUCCESS,"success!",list);
	}
	
	@RequestMapping(value = "/showView", method=RequestMethod.GET)
	public String showView(HttpServletRequest request,@RequestParam String groupNo, @RequestParam(required=false) String topicId,ModelMap model){
		model.put("groupNo", groupNo);
		model.put("topicId", topicId==null?"":topicId);
		return "show";
	}
	
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public String upload(HttpServletRequest request,@RequestParam("excelFile") MultipartFile excelFile){
		User user = (User)request.getSession().getAttribute("user");
		if(excelFile != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        String location =df.format(new Date()) + System.getProperty("file.separator");
	       
	        String fileName = excelFile.getOriginalFilename();
	        String suffix = fileName.substring(fileName.lastIndexOf(".") , fileName.length());
	        if(!suffix.endsWith(".xls")&&!suffix.endsWith(".xlsx")){
	        	throw new RuntimeException("文件格式不支持，目前只支持 .xls 和  .xlsx 格式的资源");
	        }
	        // 判断文件夹是否存在，不存在则
	        String basePath = "all_image"+System.getProperty("file.separator");
	        File targetFile = new File(basePath + location+fileName);
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }
	        try {
				excelFile.transferTo(targetFile);
				InputStream is = null;
			    HSSFWorkbook hWorkbook = null;
			    is = new FileInputStream(targetFile);
			    hWorkbook = new HSSFWorkbook(is);
			    HSSFSheet hSheet = hWorkbook.getSheetAt(0);
		    	if (null != hSheet){
		    	  List<TopicForm> topicList = new ArrayList<>();
		          for (int i = 1; i < hSheet.getPhysicalNumberOfRows(); i++){ 
		            HSSFRow hRow = hSheet.getRow(i);
		            TopicForm topic = new TopicForm();
		            HSSFCell cell0 = hRow.getCell(0);
		            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		            topic.setGroupNo(cell0.toString());
		            HSSFCell cell1 = hRow.getCell(1);
		            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		            String topicType = cell1.toString();
		            topic.setTopicType(topicType);
		            topic.setTopicName(hRow.getCell(2)==null?"":hRow.getCell(2).toString());
		            topic.setSubTopicName(hRow.getCell(3)==null?"":hRow.getCell(3).toString());
		            topic.setTopicTip(hRow.getCell(4)==null?"":hRow.getCell(4).toString());
		            topic.setOptionA(hRow.getCell(5)==null?"":hRow.getCell(5).toString());
		            topic.setOptionB(hRow.getCell(6)==null?"":hRow.getCell(6).toString());
		            topic.setOptionC(hRow.getCell(7)==null?"":hRow.getCell(7).toString());
		            topic.setOptionD(hRow.getCell(8)==null?"":hRow.getCell(8).toString());
		            topic.setOptionE(hRow.getCell(9)==null?"":hRow.getCell(9).toString());
		            topic.setOptionF(hRow.getCell(10)==null?"":hRow.getCell(10).toString());
		            if("1".equals(topicType)){ // 简单题
		            	topic.setAnswer1(hRow.getCell(11)==null?"":hRow.getCell(11).toString());
		    		}
		    		if("2".equals(topicType)){ // 选择题
		    			topic.setAnswer2(hRow.getCell(11)==null?"":hRow.getCell(11).toString());
		    		}
		    		if("3".equals(topicType)){ // 简单题
		    			topic.setAnswer3(hRow.getCell(11)==null?"":hRow.getCell(11).toString());
		    		}
		    		if("4".equals(topicType)){ // 判别题
		    			topic.setAnswer4(hRow.getCell(11)==null?"":hRow.getCell(11).toString());
		    		}
		    		topic.setPoint(hRow.getCell(12)==null?0:(float)hRow.getCell(12).getNumericCellValue());
		    		topic.setIndex(hRow.getCell(13)==null?0:(int)hRow.getCell(13).getNumericCellValue());
		    		topic.setUserId(user.getUserId().toString());
		    		
		    		topicList.add(topic);
		          } 
		          topicService.saveTopic(topicList);
		          
		        }
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		return "main";
	}
	@RequestMapping(value = "/downLoadExcel", method=RequestMethod.GET)
	@ResponseBody
	public void upload(HttpServletResponse response){
		HSSFWorkbook workbook = new HSSFWorkbook();  
        // 2.创建一个 workSheet  
        HSSFSheet worksheet = workbook.createSheet();
        workbook.setSheetName(0, "Topic 导入模板");// 设置sheet中文编码;
        HSSFRow titleRow = worksheet.createRow(0);
        // 设置标题
        String[] titleName = new String[]{"GroupNo",  "TopicType","TopicName", "SubTopicName","Tip","optionA","optionB","optionC ","optionD","optionE",	"optionF",	"Answer","Point","Index"};
        for(int i = 0;i<titleName.length;i++){
        	titleRow.createCell(i).setCellValue(titleName[i]);
        }
        ServletOutputStream outputStream = null;
        try {
        	outputStream = response.getOutputStream();
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(outputStream!=null){
				try{
					String fileName = "Topic导入模板.xls";  
                	response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            	    // 确保发送的当前文本格式  
            	    response.setContentType("application/vnd.ms-excel; charset=utf-8");  
                    response.setCharacterEncoding("utf-8");
                	outputStream.flush();
                	outputStream.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
