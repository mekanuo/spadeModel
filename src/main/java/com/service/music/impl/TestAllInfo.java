package com.service.music.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class TestAllInfo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] contents = FileUtils.readFileToString(new File("D:\\data\\test.txt"), "UTF-8").split("\n");
		int temp1 = 0;
		int temp2 = 0;
		int temp3 = 0;
		
		int temp4 = 0;
		int temp5=0;
		int temp6=0;
		int temp7=0;
		for (String content : contents) {
			
			String key =content.substring(content.indexOf("newPoints[")+10, content.indexOf("=")-2).trim();
			String infos=content.substring(content.indexOf("=")+2, content.length()-1);
			//System.out.println(infos);
			String newPoints[] = JSON.parseObject(infos,String[].class);
			//情况1
			if (!newPoints[18].equals("")){
				temp1++;
				//应该是直接播放
                //createMarkerPlaylist(marker, i);
				String audioBoomPlayer="//embeds.audioboom.com/publishing/playlist/v4?bg_fill_col=%23ecefef&amp;amp;boo_content_type=playlist&amp;amp;data_for_content_type=" + newPoints[18] + "&amp;amp;image_option=small&amp;amp;link_color=%2358d1eb&amp;amp;player_theme=light&amp;amp;src=https%3A%2F%2Fapi.audioboom.com%2Fplaylists%2F" + newPoints[18];
//				System.out.println(">>>>>>>>::::"+audioBoomPlayer);
				String notes = "";
				if (!newPoints[17].equals("")) {
					notes = newPoints[17];
				}
				String thisContent = newPoints[3];
				//最后所有内容结果
				//System.out.println("notes:"+notes+"\tthisContent:"+thisContent+"\taudioBoomPlayer:"+audioBoomPlayer);
				
            }else{
            	//情况2
                if (!newPoints[21].equals("1")){
                	temp2++;
                	String title = newPoints[2];
                	String description = "";
                	String cityVersionBy = "";
                	String cityAudioBoomPlayer = "";
                	
                	String memoryVersionBy1 = "";
                	String memoryAudio1 = "";
                	
                	String memoryVersionBy2 = "";
                	String memoryAudio2 = "";
                	
                	String memoryVersionBy3 = "";
                	String memoryAudio3 = "";
                	
                	String memoryVersionBy4 = "";
                	String memoryAudio4 = "";
                	
                	String memoryVersionBy5 = "";
                	String memoryAudio5 = "";
                	
                	                	// City Version By
                    if (!newPoints[4].equals("")) 
                    	cityVersionBy = newPoints[4];
                    // City Audio
                    if (!newPoints[5].equals("")) 
                    	cityAudioBoomPlayer = "//embeds.audioboom.com/posts/" + newPoints[5] + "/embed/v4?eid=AQAAACvnT1nVY1oA";
                    // Memory version by
                    if (!newPoints[7].equals("")) 
                    	memoryVersionBy1 = "Memory version by " + newPoints[7];
                    // Memory Audio
                    if (!newPoints[8].equals("")) 
                    	memoryAudio1 = "//embeds.audioboom.com/posts/" + newPoints[8] + "/embed/v4?eid=AQAAACnnT1nUY1oA";
                    // Memory version by
                    if (!newPoints[9].equals("")) 
                    	memoryVersionBy2 = "Memory version 2 by " + newPoints[9];
                    // Memory Audio
                    if (!newPoints[10].equals("")) 
                    	memoryAudio2 = "//embeds.audioboom.com/posts/" + newPoints[10] + "/embed/v4?eid=AQAAACnnT1nUY1oA";
                    // Memory version by
                    if (!newPoints[11].equals("")) 
                    	memoryVersionBy3 = "Memory version 3 by " + newPoints[11];
                    // Memory Audio
                    if (!newPoints[12].equals("")) 
                    	memoryAudio3 = "//embeds.audioboom.com/posts/" + newPoints[12] + "/embed/v4?eid=AQAAACnnT1nUY1oA";
                    // Memory version by
                    if (!newPoints[13].equals("")) 
                    	memoryVersionBy4 = "Memory version 4 by " + newPoints[12];
                    // Memory Audio
                    if (!newPoints[14].equals("")) 
                    	memoryAudio4 = "//embeds.audioboom.com/posts/" + newPoints[14] + "/embed/v4?eid=AQAAACnnT1nUY1oA";
                    // Memory version by
                    if (!newPoints[15].equals("")) 
                    	memoryVersionBy5 = "Memory version 5 by " + newPoints[13];
                    // Memory Audio
                    if (!newPoints[16].equals("")) 
                    	memoryAudio5 = "//embeds.audioboom.com/posts/" + newPoints[16] + "/embed/v4?eid=AQAAACnnT1nUY1oA";
                    // Description
                    if (!newPoints[17].equals("")) 
                    	description = newPoints[17];
                	
                    
                    System.out.println("title:::::::"+title);
                	System.out.println("description:::::::"+description);
                	System.out.println("cityVersionBy:::::::"+cityVersionBy);
                	System.out.println("cityAudioBoomPlayer:::::::"+cityAudioBoomPlayer);
                	System.out.println("memoryVersionBy1:::::::"+memoryVersionBy1);
                	System.out.println("memoryAudio1:::::::"+memoryAudio1);
                	System.out.println("memoryVersionBy2:::::::"+memoryVersionBy2);
                	System.out.println("memoryAudio2:::::::"+memoryAudio2);
                	System.out.println("memoryVersionBy3:::::::"+memoryVersionBy3);
                	System.out.println("memoryAudio3:::::::"+memoryAudio3);
                	System.out.println("memoryVersionBy4:::::::"+memoryVersionBy4);
                	System.out.println("memoryAudio4:::::::"+memoryAudio4);
                	System.out.println("memoryVersionBy5:::::::"+memoryVersionBy5);
                	System.out.println("memoryAudio5:::::::"+memoryAudio5);
                	System.out.println("=================================================================");
                	
                	if(!StringUtils.isEmpty(memoryAudio2.trim())) temp4++;
                	if(!StringUtils.isEmpty(memoryAudio3.trim())) temp5++;
                	if(!StringUtils.isEmpty(memoryAudio4.trim())) temp6++;
                	if(!StringUtils.isEmpty(memoryAudio5.trim())) temp7++;
                	//也不知道是什么玩意
                    //createMarker(marker, i);
                }
                //情况3
                else{
                	temp3++;
                	//最后排除后的就是图片播放
                    //createPhotoMarker(marker, i);
                	String photopopup = newPoints[22] + " by " + newPoints[7];
                    String img = "https://citiesandmemory.com/mappoints/photoproject/img/" + newPoints[19];
                    String ogg = "https://citiesandmemory.com/mappoints/photoproject/ogg/" + newPoints[20] + ".ogg";
                    String mp3 = "https://citiesandmemory.com/mappoints/photoproject/mp3/" + newPoints[20] + ".mp3";
                    if (!newPoints[2].equals("")) {
                    	String title = newPoints[2];
                    }
                    if (!newPoints[4].equals("")) {
                    	String photograph = newPoints[4];
                    }
                    //View the Sound Photography project
                    String projectUrl = "https://citiesandmemory.com/sound-photography/";
                }
            }
			
			
		}
		System.out.println("1:::"+temp1);
		System.out.println("2:::"+temp2);
		System.out.println("3:::"+temp3);
		
		System.out.println("memoryAudio2:::"+temp4);
		System.out.println("memoryAudio3:::"+temp5);
		System.out.println("memoryAudio4:::"+temp6);
		System.out.println("memoryAudio5:::"+temp7);
	}
}
