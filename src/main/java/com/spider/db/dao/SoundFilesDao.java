package com.spider.db.dao;

import java.util.List;

import com.spider.db.po.SoundFiles;

public interface SoundFilesDao {
    int deleteByPrimaryKey(Long id);

    int insert(SoundFiles record);

    int insertSelective(SoundFiles record);

    SoundFiles selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SoundFiles record);

    int updateByPrimaryKey(SoundFiles record);
    
    /**
     * 获取尚未下载的文件列表
     * @return
     */
	List<SoundFiles> getDownLoadFlagNotDownLoadData(String flag);

	/**
	 * 根据音频文件名获取file详情
	 * @param string
	 * @return
	 */
	List<SoundFiles> getFileInFoByLikeAudoFileName(String string);

	/**
	 * 获取所有文件
	 * @return
	 */
	List<SoundFiles> getAllFileInfo();
}