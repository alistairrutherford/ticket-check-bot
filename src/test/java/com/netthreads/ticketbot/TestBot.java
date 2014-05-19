package com.netthreads.ticketbot;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.netthreads.ticketbot.module.ModuleGigsAndTours;

/**
 * Test check availability ticket bot.
 * 
 */
public class TestBot
{
	private static final Logger logger = LoggerFactory.getLogger(TestBot.class);
	
	private static WebClient webClient = null;
	
	@BeforeClass
	public static void beforeClass()
	{
		
		webClient = new WebClient();
	}
	
	@AfterClass
	public static void afterClass()
	{
		if (webClient != null)
		{
			webClient.closeAllWindows();
		}
	}
	
	@Test
	public void testGigsAndTours()
	{
		logger.info("Start...test");
		
		ModuleGigsAndTours module = new ModuleGigsAndTours(webClient);
		
		module.run();
		
		List<String> links = module.getLinks();
		
		junit.framework.Assert.assertNotNull(links);
		
		junit.framework.Assert.assertTrue(links.size() > 0);
		
		List<String> buttons = module.getLinkStatus();
		
		junit.framework.Assert.assertNotNull(buttons);
		
		logger.info("End..test");
	}
	
}
