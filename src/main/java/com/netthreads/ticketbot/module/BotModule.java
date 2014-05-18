package com.netthreads.ticketbot.module;

import java.util.List;

/**
 * A bot module will return a list of valid links for the sites which match the
 * implementing module requirement as 'valid' i.e tickets available.
 * 
 */
public interface BotModule
{
	/**
	 * Run module.
	 * 
	 */
	public void run();
	
	/**
	 * Return list of valid links.
	 * 
	 * @return links.
	 */
	public List<String> getLinkStatus();
}
