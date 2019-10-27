package com.spider.utile;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpUtil {
	
	private static final int MAX = 3;
	
	/**
	 * GET
	 * Document
	 */
	public static Document getJsoupDocGetDocument(String url) {
        int time = 0;
        Document doc = null;
        while (time < MAX) {
            try {
				doc = Jsoup
                        .connect(url)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .timeout(1000 * 30)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("accept-encoding","gzip, deflate, br")
                        .header("accept-language","en-US,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                        .proxy("127.0.0.1",1080)
                        .get();
                return doc;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                time++;
            }
        }
        return doc;
    }
	
	/**
	 * Get
	 * string
	 */
	public static String getJsoupDocGetText(String url) {
		int time = 0;
		String text = null;
		while (time < MAX) {
			try {
				text = Jsoup
						.connect(url)
						.ignoreContentType(true)
						.ignoreHttpErrors(true)
						.timeout(1000 * 30)
						.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
						.header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
						.header("accept-encoding","gzip, deflate, br")
						.header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
						.get()
						.text();
				return text;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				time++;
			}
		}
		return text;
	}

	/**
	 * POST
	 * Document
	 */
    public static Document getJsoupDocPostDocument(String url, Map<String,String> paramMap) {
        int time = 0;
        Document doc = null;
        while (time < MAX) {
            try {
                doc = Jsoup
                        .connect(url)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .timeout(1000 * 30)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                        .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("accept-encoding","gzip, deflate, br")
                        .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                        .data(paramMap)
                        .post();
                return doc;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                time++;
            }
        }
        return doc;
    }
    
    
    /**
     * POST
     * String
     */
    public static String getJsoupDocPostString(String url, Map<String,String> paramMap) {
    	int time = 0;
    	String text = null;
    	while (time < MAX) {
    		try {
    			text = Jsoup
    					.connect(url)
    					.ignoreContentType(true)
    					.ignoreHttpErrors(true)
    					.timeout(1000 * 30)
    					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
    					.header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    					.header("accept-encoding","gzip, deflate, br")
    					.header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
    					.data(paramMap)
    					.post()
    					.text();
    			return text;
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			time++;
    		}
    	}
    	return text;
    }

}
