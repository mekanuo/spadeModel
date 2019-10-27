package com.spider.db.dao;

import java.util.List;

import com.spider.db.po.SoundCity;

public interface SoundCityDao {
    int deleteByPrimaryKey(Long id);

    int insert(SoundCity record);

    int insertSelective(SoundCity record);

    SoundCity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SoundCity record);

    int updateByPrimaryKey(SoundCity record);

	List<SoundCity> getAllSoundCityData();
}