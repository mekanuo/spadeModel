package com.controller.music;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.testService;
import com.service.music.AnalysisMusicService;
import com.spider.db.po.StevenMusicVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("music")
public class AnalysisMusicController {
	
	@Resource
	private AnalysisMusicService analysisMusicService;
	
	@Resource
	private testService testService;
	
	@RequestMapping("analysisData")
	@ResponseBody
	public int analysisData() throws Exception {
		String filePath = "D:\\data\\googlemusic.txt";
		try {
			long startDate = System.nanoTime();
			String[] dataSource = analysisMusicService.getDataSource(filePath);
			log.info("==========获取数据源完成，当前数据源共{}条数据==========",dataSource.length);
			List<StevenMusicVo> musicList = analysisMusicService.washData(dataSource);
			log.info("==========数据源解析完成，共解析到{}条数据==========",musicList.size());
			int save = analysisMusicService.saveDate(musicList);
			log.info("本次共保存{}条数据，耗时{}秒",save,(System.nanoTime()-startDate)/1000000000);
			return save;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
}
