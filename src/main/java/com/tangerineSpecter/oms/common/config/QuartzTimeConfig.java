package com.tangerinespecter.oms.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 丢失的橘子
 * @date 2022年10月22日21:10:34
 */
@Data
@Component
@PropertySource("classpath:/quartz-config.ini")
public class QuartzTimeConfig {

    /**
     * 星座定时任务执行时间
     * 默认：每6小时执行一次
     */
    private String constellationQuartzTime = "0 0 0/6 * * ?";

    /**
     * 微软壁纸定时任务执行时间
     * 默认：每6小时执行一次
     */
    private String wallPageQuartzTime = "0 0 0/6 * * ?";

    /**
     * 汇率定时任务执行时间
     * 默认：每3小时执行一次
     */
    private String exchangeRateQuartzTime = "0 0 0/3 * * ?";

    /**
     * 基金数据定时任务执行时间
     * 默认：每天23点执行一次
     */
    private String fundDataQuartzTime = "0 0 23 * * ?";
}
