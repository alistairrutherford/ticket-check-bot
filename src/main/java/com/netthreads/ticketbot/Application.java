package com.netthreads.ticketbot;

import com.netthreads.ticketbot.guice.AppInjector;

/**
 * Main app.
 * 
 * 
 */
public class Application
{
	public static void main(String[] args)
	{
		TicketBot ticketBot = AppInjector.getInjector().getInstance(TicketBot.class);
		
		ticketBot.go();
	}
}
