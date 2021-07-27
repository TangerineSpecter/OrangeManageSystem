package com.tangerinespecter.oms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;

public class SeleniumDemoTest {

    private static String chromeDrivePath;

//    private WebDriver webDriver;

    private static final String THS_CONCEPT_PLATE_STOCK_TABLE = "http://q.10jqka.com.cn/gn/detail/field/264648/order/desc/page/{page}/ajax/1/code/{code}";

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", chromeDrivePath);
//进行chrome设置
        ChromeOptions chromeOptions = new ChromeOptions();
        //无界面参数
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("disable-dev-shm-usage");
//        chromeOptions.addArguments("--disable-gpu");
        //禁用沙盒 就是被这个参数搞了一天
        chromeOptions.addArguments("no-sandbox");
        chromeOptions.addArguments("--incognito");
        //设置chrome浏览器的参数，使其不弹框提示（chrome正在受自动测试软件的控制）
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("Host=q.10jqka.com.cn");
//        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("Referer=http://q.10jqka.com.cn/gn/");
//        chromeOptions.addArguments("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36");
        chromeOptions.addArguments("User-Agent=\"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36\"");
//        String proxyServer = "58.17.98.211:9999";
//        Proxy proxy = new Proxy().setHttpProxy(proxyServer).setSslProxy(proxyServer);
//        //设置代理IP
//        chromeOptions.setProxy(proxy);
        // 开启开发者模式
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
//        driver.manage().deleteAllCookies();
//        driver.get(THS_CONCEPT_PLATE_STOCK_TABLE.replace("{page}", "1").replace("{code}", "300800"));
        driver.get("http://q.10jqka.com.cn/gn/");
        //设置隐性等待
//        driver.manage().timeouts().implicitlyWakait(3, TimeUnit.SECONDS);
        String pageSource = driver.getPageSource();
        System.out.println("----");
        System.out.println(pageSource);
//        Thread.sleep(30000);
//        driver.navigate().to(THS_CONCEPT_PLATE_STOCK_TABLE.replace("{page}", "2").replace("{code}", "300800"));
//        Thread.sleep(5000);
//        driver.quit();
    }

}
