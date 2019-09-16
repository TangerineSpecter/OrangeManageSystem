package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.util.RandomUtil;
import com.tangerinespecter.oms.system.dao.SystemNoticeMapper;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public class SystemNoticeServiceImpl implements ISystemNoticeService {

    @Resource
    private SystemNoticeMapper systemNoticeMapper;

    @Override
    public void push(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        while (true) {
            try {
                int i = RandomUtil.randomInt(20);
                Thread.sleep(30 * 1000);
                PrintWriter pw = response.getWriter();
                if (i % 2 == 0) {
                    pw.write("data:true\n\n");
                } else {
                    pw.write("data:false\n\n");
                }
                pw.flush();
                //检测页面关闭，断开连接
                if (pw.checkError()) {
                    return;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
