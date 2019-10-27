package com.spider.db.po;

import java.io.Serializable;
import java.util.List;

public class StevenMusicVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SoundCity soundCity;
	
	private List<SoundFiles> SoundFilesList;
	
	public SoundCity getSoundCity() {
		return soundCity;
	}

	public void setSoundCity(SoundCity soundCity) {
		this.soundCity = soundCity;
	}

	public List<SoundFiles> getSoundFilesList() {
		return SoundFilesList;
	}

	public void setSoundFilesList(List<SoundFiles> soundFilesList) {
		SoundFilesList = soundFilesList;
	}

}
