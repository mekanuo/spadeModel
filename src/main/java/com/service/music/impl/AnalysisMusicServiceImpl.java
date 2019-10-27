package com.service.music.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.service.music.AnalysisMusicService;
import com.spider.db.dao.SoundCityDao;
import com.spider.db.dao.SoundFilesDao;
import com.spider.db.po.SoundCity;
import com.spider.db.po.SoundFiles;
import com.spider.db.po.StevenMusicVo;
import com.spider.utile.CoordinateConversionUtile;
import com.spider.utile.HttpUtil;
import com.spider.utile.MathematicsUtils;
import com.spider.utile.Point;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("analysisMusicService")
public class AnalysisMusicServiceImpl implements AnalysisMusicService{

	@Resource
	private SoundCityDao soundCityDao;
	@Resource
	private SoundFilesDao soundFilesDao;
	
	//线上
	private static String MUSCI_ONLINE = "0";
	private static String MUSCI_NOT_ONLINE = "1";
	
	private static String HTTP_WEB = "http://";
	private static String HTTPS_WEB = "https://";
	private static String DOWNLODE_FLAG_YES = "1";
	private static String DOWNLODE_FLAG_NO = "0";
	
	
	
	@Override
	public String[] getDataSource(String filePath) throws IOException {
		return FileUtils.readFileToString(new File(filePath), "UTF-8").split("\n");
	}

	@Override
	public List<StevenMusicVo> washData(String[] dataSource) throws InterruptedException {
		List<StevenMusicVo> listVo = new ArrayList<>(dataSource.length);
		for (String content : dataSource) {
			String infos=content.substring(content.indexOf("=")+2, content.length()-1);
			String dataAtom[] = JSON.parseObject(infos,String[].class);
			if(dataAtom!=null) {
				StevenMusicVo vo = VoFactiry(dataAtom);
				if(!dataAtom[18].equals("")) {
					modeOne(vo,dataAtom);
				} else if(!dataAtom[21].equals("1")) {
					modeTwo(vo,dataAtom);
				} else {
					modeThree(vo,dataAtom);
				}
				listVo.add(vo);
			}
			Thread.sleep(1000);
			log.info("----->>>已获取到"+listVo.size()+"条数据，进度："+MathematicsUtils.accuracy(listVo.size(), dataSource.length, 3));
		}
		return listVo;
	}

	@Override
	public int saveDate(List<StevenMusicVo> musicList) {
		int i = 0;
		for (StevenMusicVo stevenMusicVo : musicList) {
			int result = soundCityDao.insertSelective(stevenMusicVo.getSoundCity());
			List<SoundFiles> fileList = stevenMusicVo.getSoundFilesList();
			for (SoundFiles soundFiles : fileList) {
				soundFiles.setCitySoundId(stevenMusicVo.getSoundCity().getId());
				soundFilesDao.insertSelective(soundFiles);
			}
			i+=result;
		}
		return i;
	}
	
	/**
	 * 偷懒类
	 * @param dataAtom
	 * @return
	 */
	private static StevenMusicVo VoFactiry(String[] dataAtom) {
		StevenMusicVo vo = new StevenMusicVo();
		SoundCity soundCity = new SoundCity();
		List<SoundFiles> list = new ArrayList<>();
		if(StringUtils.isNotBlank(dataAtom[0]) && StringUtils.isNotBlank(dataAtom[1])) {
			Point point = CoordinateConversionUtile.google_bd_encrypt(Double.valueOf(dataAtom[0]), Double.valueOf(dataAtom[1]));
			soundCity.setLat(point.getLat()+"");
			soundCity.setLng(point.getLng()+"");
		}
		soundCity.setTitle(dataAtom[2]);
		soundCity.setCreated(new Date());
		vo.setSoundCity(soundCity);
		vo.setSoundFilesList(list);
		return vo;
	}
	/**
	 * 偷懒类
	 * @param dataAtom
	 * @return
	 */
	private static SoundFiles SoundFileFactiry() {
		SoundFiles soundFiles = new SoundFiles();
		soundFiles.setCreated(new Date());
		soundFiles.setDownlodeFlag(DOWNLODE_FLAG_NO);
		return soundFiles;
	}
	
	private static void modeOne(StevenMusicVo vo,String[] dataAtom) {
		//0：精度，1：维度；2标题；
		//描述-大图-小图-音频-作者
		vo.getSoundCity().setStatus(MUSCI_ONLINE);
		if(StringUtils.isNotBlank(dataAtom[18])) {
			String baseUrl =HTTP_WEB + 
					"embeds.audioboom.com/publishing/playlist/v4?bg_fill_col=%23ecefef&amp;amp;boo_content_type="
					+ "playlist&amp;amp;data_for_content_type=" + dataAtom[18] 
							+ "&amp;amp;image_option=small&amp;amp;link_color=%2358d1eb&amp;amp;player_theme=light&amp;"
							+ "amp;src=https%3A%2F%2Fapi.audioboom.com%2Fplaylists%2F" + dataAtom[18];
			if(StringUtils.isNotBlank(baseUrl)) {
				Set<String> set = new HashSet<>();//列表页获取详情列表链接
				Document rest = HttpUtil.getJsoupDocGetDocument(baseUrl);
				Elements aTag = rest.select("a.underline");
				for (Element element : aTag) {
					String dataPage = element.attr("abs:href");
					if(StringUtils.isNotBlank(dataPage)) {
						set.add(dataPage);
					}
				}
				
				Set<String> infoPageSet = new HashSet<>();//详情页列表链接
				for (String dataPage : set) {
					Document dataPageData = HttpUtil.getJsoupDocGetDocument(dataPage);
					Elements infoPageElements = dataPageData.select("a.l-abs-fill-all");
					for (Element element : infoPageElements) {
						String infoPageData = element.attr("abs:href");
						if(StringUtils.isNotBlank(infoPageData)) {
							infoPageSet.add( infoPageData);
						}
					}
				}
				if(infoPageSet!=null && infoPageSet.size()>0) {
					List<SoundFiles> list = new ArrayList<>(infoPageSet.size());
					for (String infoHref : infoPageSet) {
						modeOneWashInfo(infoHref,list);
					}
					vo.setSoundFilesList(list);
				}
			}
			
		}
	}
	private static void modeOneWashInfo(String url, List<SoundFiles> list) {
		if(StringUtils.isNotBlank(url)) {
			Document infoDataURL = HttpUtil.getJsoupDocGetDocument(url);
			if(infoDataURL!=null) {
				SoundFiles po = SoundFileFactiry();	
				String tital = infoDataURL.select("h1[dir=auto]").text();
				String author = infoDataURL.select("div.plm,div.small").select("a.emphasis").text();
				String picture = infoDataURL.select("img.wi-100").attr("abs:src");
				String audio = infoDataURL.select("link[rel=audio_src]").attr("abs:href");
				String describe = infoDataURL.select("meta[name=description]").attr("abs:content");
				
				if(StringUtils.isNotBlank(tital)) po.setTitle(tital);
				if(StringUtils.isNotBlank(author)) po.setOwer(author);
				if(StringUtils.isNotBlank(picture)) po.setBigImgPath(picture);
				if(StringUtils.isNotBlank(audio)) po.setAudioPath(audio);
				if(StringUtils.isNotBlank(describe))po.setDescription(describe);
				list.add(po);
			}
		}
		
	}
	
	private static void modeTwo(StevenMusicVo vo,String[] dataAtom) {
		vo.getSoundCity().setStatus(MUSCI_ONLINE);
		
		if(dataAtom != null && dataAtom.length > 16) {
			Set<String> set = new HashSet<>();
			if(StringUtils.isNotBlank(dataAtom[5])) set.add(HTTPS_WEB +"embeds.audioboom.com/posts/" 
			+ dataAtom[5] + "/embed/v4?eid=AQAAACvnT1nVY1oA");
			
			if(StringUtils.isNotBlank(dataAtom[8])) set.add(HTTPS_WEB +"embeds.audioboom.com/posts/" 
					+ dataAtom[8] + "/embed/v4?eid=AQAAACvnT1nVY1oA");
			
			if(StringUtils.isNotBlank(dataAtom[10])) set.add(HTTPS_WEB +"embeds.audioboom.com/posts/" 
					+ dataAtom[10] + "/embed/v4?eid=AQAAACvnT1nVY1oA");
			
			if(StringUtils.isNotBlank(dataAtom[12])) set.add(HTTPS_WEB +"embeds.audioboom.com/posts/" 
					+ dataAtom[12] + "/embed/v4?eid=AQAAACvnT1nVY1oA");
			
			if(StringUtils.isNotBlank(dataAtom[14])) set.add(HTTPS_WEB +"embeds.audioboom.com/posts/" 
					+ dataAtom[14] + "/embed/v4?eid=AQAAACvnT1nVY1oA");
			
			if(StringUtils.isNotBlank(dataAtom[16])) set.add(HTTPS_WEB +"embeds.audioboom.com/posts/" 
					+ dataAtom[16] + "/embed/v4?eid=AQAAACvnT1nVY1oA");
			if(set!=null && set.size()>0) {
				
				Set<String> dataPageSet = new HashSet<>();
				for (String url : set) {
					Document rest = HttpUtil.getJsoupDocGetDocument(url);//获取详情页
					Elements aTag = rest.select("a.underline");
					for (Element element : aTag) {
						String dataPage = element.attr("abs:href");
						if(StringUtils.isNotBlank(dataPage)) {
							dataPageSet.add(dataPage);
						}
					}
				}
				
				List<SoundFiles> list = new ArrayList<>(dataPageSet.size());
				for (String url : dataPageSet) {
					modeOneWashInfo(url,list);
				}
				vo.setSoundFilesList(list);
			}
		}
		
	}
	
	private static void modeThree(StevenMusicVo vo,String[] dataAtom) {
		vo.getSoundCity().setStatus(MUSCI_NOT_ONLINE);
		if(dataAtom.length>19 && StringUtils.isNotBlank(dataAtom[20])) {
			SoundFiles po = SoundFileFactiry();
			if(StringUtils.isNotBlank(dataAtom[2])) po.setTitle(dataAtom[2]);
			if(StringUtils.isNotBlank(dataAtom[4])) po.setOwer(dataAtom[4]);
			if(StringUtils.isNotBlank(dataAtom[19])) po.setBigImgPath("https://citiesandmemory.com/mappoints/photoproject/img/" + dataAtom[19]);
			if(StringUtils.isNotBlank(dataAtom[20])) {
				po.setAudioPath("https://citiesandmemory.com/mappoints/photoproject/mp3/" + dataAtom[20] + ".mp3");
				po.setDescription(dataAtom[20]);
			}
			List<SoundFiles> soundFilesList = new ArrayList<>();
			soundFilesList.add(po);
			vo.setSoundFilesList(soundFilesList);
		}
	}
	
}
