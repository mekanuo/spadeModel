package com.service;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.service.music.AnalysisMusicService;
import com.spider.db.dao.SoundFilesDao;
import com.spider.db.dao.ThisdingSoundFilesDao;
import com.spider.db.po.SoundFiles;
import com.spider.db.po.StevenMusicVo;
import com.spider.db.po.ThisdingSoundFiles;
import com.spider.utile.FileUtile;
import com.spider.utile.MathematicsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MusicFileDownLodeSchedulerService {
	
	@Autowired
	private SoundFilesDao soundFilesDao;
	@Autowired
	private ThisdingSoundFilesDao thisdingSoundFilesDao;
	@Resource
	private AnalysisMusicService analysisMusicService;
	
	private static String DOWNLODE_FLAG_YES = "1";
	private static String DOWNLODE_FLAG_NO = "0";
	
	private static String photoPath = "d:\\photo\\";
	private static String musicPath = "d:\\music\\";
	
	private static boolean MADE_DATA_SOURCE_FLAG = false;
	
	/**
	 * 读取文件 - 解析文件数据  - 爬取文件网页  - 封装入库
	 * 该方法脆弱，不支持任务中断
	 */
	public void dataSourceFactory() {
		String filePath = "D:\\data\\googlemusic.txt";
		try {
			if(MADE_DATA_SOURCE_FLAG) {
				long startDate = System.nanoTime();
				String[] dataSource = analysisMusicService.getDataSource(filePath);
				log.info("==========获取数据源完成，当前数据源共{}条数据==========",dataSource.length);
				List<StevenMusicVo> musicList = analysisMusicService.washData(dataSource);
				log.info("==========数据源解析完成，共解析到{}条数据==========",musicList.size());
				int save = analysisMusicService.saveDate(musicList);
				log.info("本次共保存{}条数据，耗时{}秒",save,(System.nanoTime()-startDate)/1000000000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据入库后下载文件
	 * @throws IOException
	 * @throws InterruptedException
	 */
	/*@Scheduled(fixedDelay=1000 * 100)*/
	public void fileDownLoadJob() throws IOException, InterruptedException {
		log.info("定时任务--下载文件--开始执行啦.....");
		List<SoundFiles> list = soundFilesDao.getDownLoadFlagNotDownLoadData(DOWNLODE_FLAG_NO);
		log.info("定时任务--下载文件--这次扫描到了{}个任务.....",list.size());
		if(list.size()>0) {
			fileDownInfo(list);
		}
		log.info("定时任务--下载文件--我活干完啦.....");
	}
	
	/*@Scheduled(fixedDelay=1000 * 10)*/
	public void overDownLoadInfo() throws IOException, InterruptedException {
		log.info("定时任务--检查错误文件--开始执行啦.....");
		overDownLoadInfo(musicPath);
	}
	
	/**
	 * 重新下载漏掉的文件
	 */
	/*@Scheduled(fixedDelay=1000 * 10)*/
	public void SerchLostFile() {
		File file = new File(musicPath);
		File[] fileList = file.listFiles();
		Set<String> set = new HashSet<>();
		for (File file2 : fileList) {
			if(file2.isFile() && StringUtils.isNotBlank(file2.getName())) {
				set.add(file2.getName());
			}
		}
		List<SoundFiles> list = soundFilesDao.getAllFileInfo();
		Map<String, SoundFiles> map = new HashMap<>(list.size());
		for (SoundFiles po : list) {
			map.put(po.getAudioPath(), po);
			
		}
		for (String fileName : set) {
			map.remove(fileName);
		}
		
		List<SoundFiles> lostData = new ArrayList<>();
		for (Map.Entry<String, SoundFiles> entry : map.entrySet()) {
			SoundFiles po = (SoundFiles)entry.getValue();
			lostData.add(po);
		}
		
		if(lostData.size()>0) {
			log.info("定时任务--重新下载漏掉的文件--开始下载错误文件------");
			fileDownInfo(lostData);
			
		}
	}
	
	/*@Scheduled(fixedDelay=1000 * 10)*/
	public void renameToSQL() {
		List<SoundFiles> list = soundFilesDao.getAllFileInfo();
		int i = 0;
		for (SoundFiles soundFiles : list) {
			SoundFiles po = new SoundFiles();
			if(StringUtils.isNotBlank(soundFiles.getAudioFilePath())) {
				if(soundFiles.getAudioFilePath().length() != FileUtile.fileNameCheck(soundFiles.getAudioFilePath()).length()) {
					po.setAudioFilePath(FileUtile.fileNameCheck(soundFiles.getAudioFilePath()));
				}
			}
			
			if(StringUtils.isNotBlank(soundFiles.getImgFilePath())) {
				if(soundFiles.getImgFilePath().length() != FileUtile.fileNameCheck(soundFiles.getImgFilePath()).length()) {
					po.setImgFilePath(FileUtile.fileNameCheck(soundFiles.getImgFilePath()));
				}
			}
			
			if(StringUtils.isNotBlank(po.getAudioFilePath())) {
				po.setId(soundFiles.getId());
				i+= soundFilesDao.updateByPrimaryKeySelective(po);
			}
		}
		System.out.println(i);
	}
	public static String imgPath = "http://cloud.thisding.com/photo/";
	public static String audoPath = "http://cloud.thisding.com/music/";
	
	@Scheduled(fixedDelay=1000 * 10)
	public void modeToNewMode() {
		List<SoundFiles> list = soundFilesDao.getAllFileInfo();
		int i =0;
		for (SoundFiles soundFiles : list) {
			ThisdingSoundFiles po = new ThisdingSoundFiles();
			po.setCitySoundId(soundFiles.getCitySoundId());
			po.setTitle(soundFiles.getTitle());
			if(StringUtils.isNotBlank(soundFiles.getDescription())) {
				po.setDescription(soundFiles.getDescription().replace("https://audioboom.com/posts/", ""));
			}
			if(StringUtils.isNotBlank(soundFiles.getImgFilePath())) {
				po.setBigImgPath(imgPath + soundFiles.getImgFilePath());
			}
			if(StringUtils.isNotBlank(soundFiles.getAudioFilePath())) {
				po.setAudioPath(audoPath + soundFiles.getAudioFilePath());
			}
			po.setOwer(soundFiles.getOwer());
			po.setCreated(new Date());
			i+=thisdingSoundFilesDao.insertSelective(po);
		}
		System.out.println(i+"");
	}
	
	/**
	 * 本地文件名过滤特殊符号
	 * @param path
	 */
	private static void renameToInfo(String path) {
		File[] fileList = new File(path).listFiles();
		int i = 0;
		for (File file : fileList) {
			if(StringUtils.isNotBlank(file.getName())) {
				 FileUtile.fileNameCheck(file.getName());
				 if(file.getName().length()!=FileUtile.fileNameCheck(file.getName()).length()) {
					 i++;
					 System.out.println("命名前："+file.getName());
					 String newName = FileUtile.fileNameCheck(file.getName());
					 System.out.println("命名后："+newName);
					 File newDir = new File(path + newName);//文件所在文件夹路径+新文件名
					 file.renameTo(newDir);//重命名
				 }
			}
		}
		System.out.println("count==" + i);
	}
	
	public void overDownLoadInfo(String path) {
		File file = new File(path);
		File[] fileList = file.listFiles();
		List<String> name = new ArrayList<>();
		for (File file2 : fileList) {
			if(file2.isFile() && StringUtils.isNotBlank(file2.getName()) && !file2.getName().contains(".")) {
				name.add(file2.getName());
			}
		}
		log.info("定时任务--检查错误文件--错误文件数："+name.size());
		List<SoundFiles> filePoList = new ArrayList<>(name.size());
		for (String string : name) {
			if(StringUtils.isNotBlank(string)) {
				System.out.println(string);
				List<SoundFiles> po = soundFilesDao.getFileInFoByLikeAudoFileName(string+"%");
				if(po!=null) filePoList.addAll(po);
			}
		}
		log.info("定时任务--检查错误文件--获取数据库错误文件数："+filePoList.size());
		
		if(filePoList.size()>0) {
			log.info("定时任务--检查错误文件--开始下载错误文件------");
			fileDownInfo(filePoList);
			
		}
		
		for (String string : name) {
			log.info("定时任务--检查错误文件--删除错误数据....");
			deleteFile(musicPath+string);
		}
		
		log.info("定时任务--检查错误文件--我活干完啦.....");
		
	}
	
	private void deleteFile(String path) {
		new File(path).delete();
	}
	
	private void fileDownInfo(List<SoundFiles> filePoList) {
		int i = 0;
		for(SoundFiles po : filePoList) {
			try {
				SoundFiles soundFiles = new SoundFiles();
				String fileName =po.getTitle();
				if(StringUtils.isNotBlank(po.getAudioPath())) {
					String audioFileName = fileName + StringUtils.deleteWhitespace(po.getAudioPath().substring(po.getAudioPath().lastIndexOf('.')));
					audioFileName = FileUtile.fileNameCheck( StringUtils.deleteWhitespace(audioFileName));
					fileDown(po.getAudioPath(),audioFileName,false);
					soundFiles.setAudioFilePath(audioFileName);
				}
				
				if(StringUtils.isNotBlank(po.getBigImgPath())) {
					String photoName = fileName + po.getBigImgPath().substring(po.getBigImgPath().lastIndexOf('.'));
					photoName = FileUtile.fileNameCheck( StringUtils.deleteWhitespace(photoName));
					fileDown(po.getBigImgPath(),photoName,true);
					soundFiles.setImgFilePath(photoName);
				}
				
				soundFiles.setId(po.getId());
				soundFiles.setDownlodeFlag(DOWNLODE_FLAG_YES);
				soundFilesDao.updateByPrimaryKeySelective(soundFiles);
				i++;
				log.info("----->>>已下载"+i+"条数据，进度："+MathematicsUtils.accuracy(i, filePoList.size(), 3));
			} catch  (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void fileDown(String url,String fileName,Boolean flag) throws IOException {
		//30M以上的可能会有问题
		BufferedInputStream bufferedInputStream = Jsoup.connect(url)
				.ignoreContentType(true)
				.maxBodySize(60000000)
				.ignoreHttpErrors(true)
				.proxy("127.0.0.1",1080)
				.timeout(1000 * 3000)
				.execute()
				.bodyStream();
		FileOutputStream  fileOut = null;
		if(flag) {
			fileOut = new FileOutputStream(photoPath  + fileName); 
		} else {
			fileOut = new FileOutputStream(musicPath + fileName); 
		}
		IOUtils.copyLarge(bufferedInputStream,fileOut);	
		log.info("文件{}下载成功！",fileName);
	}
}
