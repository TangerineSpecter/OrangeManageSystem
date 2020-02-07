package com.tangerinespecter.oms;

import com.tangerinespecter.oms.system.service.dump.DumpDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OmsApplicationTests {

    @Resource
    private DumpDataService dumpDataService;

    @Test
    public void contextLoads() {
        dumpDataService.dumpAdPlanTable(String.format("%s%s", DumpDataService.DATA_ROOT_DIR,
                DumpDataService.DATA_CONSTELLATION));
    }

}

