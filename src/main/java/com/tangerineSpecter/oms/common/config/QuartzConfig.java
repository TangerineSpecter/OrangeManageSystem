package com.tangerineSpecter.oms.common.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tangerineSpecter.oms.job.quartz.ConstellationDataQuartz;

/**
 * Quartz定时配置
 * 
 * @author TangerineSpecter
 * @Date 2019年1月6日
 * @version v0.0.4
 */
@Configuration
public class QuartzConfig {
	/** 定时执行时间(单位：秒) */
	private final Integer quartzTime = 6 * 60 * 60;

	@Bean
	public JobDetail constellationQuartz() {
		return JobBuilder.newJob(ConstellationDataQuartz.class).withIdentity("constellationQuartz").storeDurably()
				.build();
	}

	@Bean
	public Trigger constellationQuartzTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzTime) // 设置时间周期单位秒
				.repeatForever();
		return TriggerBuilder.newTrigger().forJob(constellationQuartz()).withIdentity("constellationQuartz")
				.withSchedule(scheduleBuilder).build();
	}
}
