package com.tangerinespecter.oms.job.quartz;

import com.tangerinespecter.oms.job.service.WallPageQuartzService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * 每日壁纸任务
 *
 * @author 丢失的橘子
 * @version 0.5.0
 * @date 2022年01月06日19:27:35
 */
public class WallPageConfigQuartz extends QuartzJobBean {
	
	@Resource
	private WallPageQuartzService wallPageQuartzService;
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) {
		wallPageQuartzService.runData();
	}
}
