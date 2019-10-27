package com.service;

import java.util.List;

import com.spider.db.po.SoundCity;

public interface testService {
	
	List<SoundCity> getAllSoundCityData();

	SoundCity selectByPrimaryKey(long i);

}
