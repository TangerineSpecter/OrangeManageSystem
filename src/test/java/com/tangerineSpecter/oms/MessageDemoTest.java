package com.tangerinespecter.oms;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.query.SystemTokenQueryObject;
import com.tangerinespecter.oms.system.domain.pojo.FsSimpleCardMessage;
import com.tangerinespecter.oms.system.domain.pojo.MemosInfo;
import com.tangerinespecter.oms.system.service.system.ITokenManageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDemoTest {

    @Resource
    private ITokenManageService tokenManageService;

    public static void main(String[] args) throws Exception {
        String response = HttpUtil.get("http://10.144.116.114:5230/api/memo?openId=10e4c3db-3ad3-4864-8fc9-daec5b3fc6d5");
        MemosInfo memosInfo = JSON.parseObject(response, MemosInfo.class);
//        System.out.println(memosInfo);
        String content = memosInfo.getData().get(0).getContent();
//        System.out.println(content);
        String result = ReUtil.delAll("#\\S+", content);
        System.out.println(result);
        final FsSimpleCardMessage message = new FsSimpleCardMessage("\uD83D\uDCDA每日回顾", result);
        System.out.println(JSON.toJSONString(message));
//        HttpUtil.post("https://open.feishu.cn/open-apis/bot/v2/hook/50ed6fa7-497c-4ca2-bd02-8dc259fc93c4", JSON.toJSONString(message));
    }

    @Test
    public void test() {
        System.out.println(tokenManageService.list(new SystemTokenQueryObject()));
    }
}
