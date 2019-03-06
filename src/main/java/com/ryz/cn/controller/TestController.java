package com.ryz.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ryz.cn.dao.Dao;
import com.ryz.cn.entity.User;
import com.ryz.cn.service.UserService;

@Controller
public class TestController {

@Autowired
private UserService userService;
	
	@RequestMapping("/show")
    public String show(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name,ModelMap model) {
       model.addAttribute("name",name);
       userService.saveUser();
        return "test";
    }
}
