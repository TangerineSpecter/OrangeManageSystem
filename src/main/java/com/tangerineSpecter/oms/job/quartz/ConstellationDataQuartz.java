package com.tangerineSpecter.oms.job.quartz;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tangerineSpecter.oms.job.service.ConstellationService;

/**
 * 星座数据任务
 * 
 * @author TangerineSpecter
 * @Date 2019年1月6日
 * @version v0.0.4
 */
public class ConstellationDataQuartz extends QuartzJobBean {

	@Autowired
	private ConstellationService constellationService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) {
		constellationService.runData();
	}

}
