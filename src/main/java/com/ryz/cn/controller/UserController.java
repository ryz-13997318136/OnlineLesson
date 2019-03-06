package com.ryz.cn.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryz.cn.entity.User;
import com.ryz.cn.model.Res;
import com.ryz.cn.model.Result;
import com.ryz.cn.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/openLogin")
	public String openLogin(){
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,@RequestParam String userName,@RequestParam String password) throws JsonProcessingException{
		User user = userService.getUser(userName, password);
		if(user != null){
			request.getSession().setAttribute("user", user);
			ObjectMapper mapper = new  ObjectMapper();
			request.getSession().setAttribute("userJson",  mapper.writeValueAsString(user));
			return "redirect:/main/openMain";
		}
		return "forward:/user/openLogin"; 
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody Map<String,Object> params) throws JsonProcessingException{
		User user = (User)request.getSession().getAttribute("user");
		userService.save(user,params);
		return new Result(Res.SUCCESS,"update user success!");
	}

	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		if(user != null){
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("userJson");
		}
		return "forward:/user/openLogin"; 
	}
}
