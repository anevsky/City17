/**
 * 
 */
package com.alexnevsky.web.business;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alexnevsky.web.capture.sites.EtodayContent;
import com.alexnevsky.web.capture.sites.GalyonkinContent;
import com.alexnevsky.web.service.IGeneralService;

/**
 * @author Alex Nevsky
 * 
 */
@Component
public class Schedule implements InitializingBean {

	private IGeneralService iGeneralService;

	private EtodayContent etodayContent;
	private GalyonkinContent galyonkinContent;

	@Autowired
	public void setIGeneralService(IGeneralService iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	@Override
	public void afterPropertiesSet() {
		this.etodayContent = new EtodayContent(this.iGeneralService);
		this.galyonkinContent = new GalyonkinContent(this.iGeneralService);
	}

	/*
	 * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/scheduling.html
	 * http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
	 * http://forum.springsource.org/showthread.php?83053-Feature-Scheduled-with-Value-cron-expression
	 * @Scheduled(fixedDelay = 10000) // every 100 sec
	 * @Scheduled(cron = "0 0 * * * *") // the top of every hour of every day
	 * @Scheduled(cron = "${schedule.cron}") // see application.properties
	 */

	@Scheduled(cron = "${schedule.cron}")
	public void doSomething() {
		this.etodayContent.update();
		this.galyonkinContent.update();
	}
}
