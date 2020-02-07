package com.tangerinespecter.oms.system.service.dump;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;
import com.tangerinespecter.oms.system.service.dump.table.DataConstellationTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DumpDataService {

    public static final String DATA_ROOT_DIR = "/Users/zhouliangjun/Desktop/imooc_data/mysql_data";

    //各个表的数据存储文件名
    public static final String DATA_CONSTELLATION = "data_constellation.data";

    @Resource
    private DataConstellationMapper dataConstellationMapper;

    public void dumpAdPlanTable(String fileName) {
        List<DataConstellation> adPlans = dataConstellationMapper.selectList(null);
        if (CollUtil.isEmpty(adPlans)) {
            return;
        }
        List<DataConstellationTable> dataConstellationTables = new ArrayList<>();
        adPlans.forEach(d -> dataConstellationTables.add(
                new DataConstellationTable(
                        d.getId(),
                        d.getName(),
                        d.getDatetime()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (DataConstellationTable planTable : dataConstellationTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException ex) {
            log.error("dumpAdPlanTable error");
        }
    }
}
