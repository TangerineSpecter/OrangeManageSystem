package com.tangerinespecter.oms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class JsoupDemoTest {
	
	private static final String url = "http://q.10jqka.com.cn/gn/detail/field/264648/order/desc/page/{page}/ajax/1/code/{code}";
	
	public static void main(String[] args) throws Exception {
//		handlerStockData(8, "300163");
		maoyanData();
	}
	
	public static void handlerStockData(Integer page, String code) throws Exception {
		String cookie = JavaScriptEngineManagerDemoTest.ThsAesJs();
		Document document = Jsoup.connect(url.replace("{page}", Convert.toStr(page))
				.replace("{code}", code)).cookie("v", cookie).get();
//        Document document = Jsoup.connect(url).get();
		Elements tr = document.getElementsByTag("tr");
		int count = 0;
		System.out.println("---第" + page + "页---");
		Thread.sleep(RandomUtil.randomInt(500, 2000));
		for (int index = 1; index < tr.size(); index++) {
			Element element = tr.get(index);
//            System.out.println(element);
			Elements a = element.getElementsByTag("a");
			if (CollUtil.isEmpty(a)) {
				continue;
			}
			count++;
			System.out.println("----第" + count + "个----");
			for (int trIndex = 0; trIndex < 2; trIndex++) {
				Element element1 = a.get(trIndex);
				System.out.println(element1.text());
			}
			if (count == 10) {
				handlerStockData(++page, code);
			}
		}
	}
	
	public static void maoyanData() throws Exception {
		log.info("猫眼start...");
		int defaultOffset = 30;
		int page = 1;
		String url = "https://www.maoyan.com/films?showType=3&offset=" + ((page - 1) * defaultOffset);
//        String html = Jsoup.connect(url).execute().body();
		Document html = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36").get();
		System.out.println(html);
//        System.out.println(html);
//        Element body = html.body();
//		System.out.println(body);
		Elements elementsByAttribute = html.getElementsByClass("movies-list");
		for (Element element : elementsByAttribute) {
			System.out.println(element);
		}
	}
}
