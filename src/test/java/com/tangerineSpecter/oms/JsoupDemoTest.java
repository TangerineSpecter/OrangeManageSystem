package com.tangerinespecter.oms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDemoTest {

    private static final String url = "http://q.10jqka.com.cn/gn/detail/field/264648/order/desc/page/{page}/ajax/1/code/{code}";

    public static void main(String[] args) throws Exception {
        handlerStockData(8, "300163");
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
}
