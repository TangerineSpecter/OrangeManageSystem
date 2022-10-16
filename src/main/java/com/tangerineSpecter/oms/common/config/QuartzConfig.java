package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.job.quartz.ConstellationDataQuartz;
import com.tangerinespecter.oms.job.quartz.ExchangeRateDataQuartz;
import com.tangerinespecter.oms.job.quartz.FundDataQuartz;
import com.tangerinespecter.oms.job.quartz.WallPageConfigQuartz;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Trigger constellationQuartzTrigger() {
        //定时执行时间(单位：秒)
        int quartzTime = 6 * 60 * 60;
        // 设置时间周期单位秒
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzTime)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(constellationQuartz()).withIdentity("constellationQuartz")
                .withSchedule(scheduleBuilder).build();
    }

    @Bean
    public JobDetail wallPageQuartz() {
        return JobBuilder.newJob(WallPageConfigQuartz.class).withIdentity("wallPageConfigQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger wallPageQuartzTrigger() {
        //定时执行时间(单位：秒)
        int quartzTime = 6 * 60 * 60;
        // 设置时间周期单位秒
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzTime)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(wallPageQuartz()).withIdentity("wallPageConfigQuartz")
                .withSchedule(scheduleBuilder).build();
    }

    @Bean
    public JobDetail exchangeRateQuartz() {
        return JobBuilder.newJob(ExchangeRateDataQuartz.class).withIdentity("exchangeRateDataQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger exchangeRateTrigger() {
        //定时执行时间(单位：秒)
        int quartzTime = 3 * 60 * 60;
        // 设置时间周期单位秒
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzTime)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(exchangeRateQuartz()).withIdentity("exchangeRateDataQuartz")
                .withSchedule(scheduleBuilder).build();
    }

    @Bean
    public JobDetail fundDataQuartz() {
        return JobBuilder.newJob(FundDataQuartz.class).withIdentity("fundDataQuartz").storeDurably()
                .build();
    }

    @Bean
    public Trigger fundDataTrigger() {
        //定时执行时间(单位：秒)
        int quartzTime = 12 * 60 * 60;
        // 设置时间周期单位秒
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzTime)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(fundDataQuartz()).withIdentity("fundDataQuartz")
                .withSchedule(scheduleBuilder).build();
    }
}
