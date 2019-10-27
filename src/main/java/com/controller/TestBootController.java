package com.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.testService;
import com.spider.db.po.SoundCity;

@RestController
@EnableAutoConfiguration
@RequestMapping("/testboot")
public class TestBootController {
	
	@Resource
	private testService testService;
	
    @RequestMapping("getuser")
    public SoundCity getUser() {
    	SoundCity user = new SoundCity();
    	user.setLat("1");
        return user;
    }
    
	@RequestMapping("/showUser")
	@ResponseBody
	public List<SoundCity> getData() {
		return testService.getAllSoundCityData();
	}
	@RequestMapping("/user")
	@ResponseBody
	public SoundCity userById() {
		return testService.selectByPrimaryKey(1);
	}
}