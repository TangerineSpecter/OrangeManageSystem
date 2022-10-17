package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.job.quartz.ConstellationDataQuartz;
import com.tangerinespecter.oms.job.quartz.ExchangeRateDataQuartz;
import com.tangerinespecter.oms.job.quartz.FundDataQuartz;
import com.tangerinespecter.oms.job.quartz.WallPageConfigQuartz;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;

/**
 * Quartz定时配置
 *
 * @author TangerineSpecter
 * @version v0.0.4
 * @date 2019年1月6日
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail constellationQuartz() {
        return JobBuilder.newJob(ConstellationDataQuartz.class).withIdentity("constellationQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger constellationQuartzTrigger() throws ParseException {
//        //定时执行时间(单位：秒)
//        int quartzTime = 6 * 60 * 60;
//        // 设置时间周期单位秒
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzTime)
//                .repeatForever();
        // 每间隔6小时执行
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(new CronExpression("0 0 0/6 * * ?"));
        return TriggerBuilder.newTrigger().forJob(constellationQuartz()).withIdentity("constellationQuartz")
                .withSchedule(cronScheduleBuilder).build();
    }

    @Bean
    public JobDetail wallPageQuartz() {
        return JobBuilder.newJob(WallPageConfigQuartz.class).withIdentity("wallPageConfigQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger wallPageQuartzTrigger() throws ParseException {
        // 每间隔6小时执行
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(new CronExpression("0 0 0/6 * * ?"));
        return TriggerBuilder.newTrigger().forJob(wallPageQuartz()).withIdentity("wallPageConfigQuartz")
                .withSchedule(cronScheduleBuilder).build();
    }

    @Bean
    public JobDetail exchangeRateQuartz() {
        return JobBuilder.newJob(ExchangeRateDataQuartz.class).withIdentity("exchangeRateDataQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger exchangeRateTrigger() throws ParseException {
        // 每间隔3小时执行
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(new CronExpression("0 0 0/3 * * ?"));
        return TriggerBuilder.newTrigger().forJob(exchangeRateQuartz()).withIdentity("exchangeRateDataQuartz")
                .withSchedule(cronScheduleBuilder).build();
    }

    @Bean
    public JobDetail fundDataQuartz() {
        return JobBuilder.newJob(FundDataQuartz.class).withIdentity("fundDataQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger fundDataTrigger() throws ParseException {
        // 每天定时23点执行
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(new CronExpression("0 0 23 * * ?"));
        return TriggerBuilder.newTrigger().forJob(fundDataQuartz()).withIdentity("fundDataQuartz")
                .withSchedule(cronScheduleBuilder).build();
    }
}
