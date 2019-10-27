package com.spider.utile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class FileUtile {
	
	public static <T> void writeFileList(List<T>  obj, String path) throws Exception {
		File file = new File(path);
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (T l:obj){
            writer.write(l + "\r\n");
        }
        writer.close();
        System.out.println("===================End=====================");
	}
	public static void writeFileJson(JSON json, String path) throws Exception {
		File file = new File(path);
		//如果没有文件就创建
		if (!file.isFile()) {
			file.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write(json.toJSONString());
		writer.close();
		System.out.println("===================End=====================");
	}
	
	/**
	 * 文件名在操作系统中不允许出现 / \ ” : | * ? < > 故将其以空替代
	 * @param fileName
	 * @return
	 */
	public static String fileNameCheck (String fileName) {
		if(StringUtils.isNotBlank(fileName)) {
			Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\-\\#\\;\\'\\!\\,\\?\\\"<>\\|\\¿\\']");
			Matcher matcher = pattern.matcher(fileName);
			fileName= matcher.replaceAll(""); // 将匹配到的非法字符以空替换
		}
		return fileName;
	}
	
	
	public static void main(String[] args) {
		String[] arr = new String[]{
				"Si\\No\\Oblique-anewlookatVenice",
				"UnnervingbarnnoiseWiltshire\\Somersetborder",
				"Buskerplays\"WilliamTellOverture\"onglasses.mp3 ",
				"Mãe\\Mother(part2)(MexicoCity).mp3",
				"F**kYourAusterity(Greece).mp3",
				"What,Iwonder,lurksbeneathcozySt.Pauli?.mp3",
				"Si\\No\\Oblique-anewlookatVenice.mp3",
				"Delay\\reverb\\garage.mp3",
				"Seagulls,seals,mermaidsperhaps?.mp3",
				"Unnervingbarnnoise,Wiltshire\\Somersetborder.mp3",
				"Mirvama\\Peacetoyou.mp3",
		};
		
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
			System.out.println(fileNameCheck(arr[i])+"----");
		}
			
	}

}
