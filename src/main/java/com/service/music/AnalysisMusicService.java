package com.service.music;

import java.io.IOException;
import java.util.List;

import com.spider.db.po.StevenMusicVo;

public interface AnalysisMusicService {

	/**
	 * 获取数据源
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	String[] getDataSource(String filePath) throws IOException;

	/**
	 * 数据解析
	 * @param dataSource
	 * @return
	 * @throws InterruptedException 
	 */
	List<StevenMusicVo> washData(String[] dataSource) throws InterruptedException;

	/**
	 * 数据入库
	 * @param musicList
	 * @return
	 */
	int saveDate(List<StevenMusicVo> musicList);

}
