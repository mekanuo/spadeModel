package com.spider.db.dao;

import com.spider.db.po.ThisdingSoundFiles;

public interface ThisdingSoundFilesDao {
    int deleteByPrimaryKey(Long id);

    int insert(ThisdingSoundFiles record);

    int insertSelective(ThisdingSoundFiles record);

    ThisdingSoundFiles selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ThisdingSoundFiles record);

    int updateByPrimaryKey(ThisdingSoundFiles record);
}