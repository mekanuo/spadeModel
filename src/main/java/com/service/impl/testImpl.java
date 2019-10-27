package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.testService;
import com.spider.db.dao.SoundCityDao;
import com.spider.db.po.SoundCity;

@Service("testService")
public class testImpl implements testService{
	
	@Autowired
	private SoundCityDao soundCityDao;

	@Override
	public List<SoundCity> getAllSoundCityData() {
		return soundCityDao.getAllSoundCityData();
	}

	@Override
	public SoundCity selectByPrimaryKey(long i) {
		return soundCityDao.selectByPrimaryKey(i);
	}

}
