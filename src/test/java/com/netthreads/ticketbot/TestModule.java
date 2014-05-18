package com.netthreads.ticketbot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.netthreads.ticketbot.email.EmailService;
import com.netthreads.ticketbot.email.EmailServiceImpl;
import com.netthreads.ticketbot.template.TemplateService;
import com.netthreads.ticketbot.template.TemplateServiceImpl;

public class TestModule extends AbstractModule
{
	private static final String FILENAME_PROPERTIES = "application-test.properties";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure()
	{
		// Bind our service(s).
		bind(EmailService.class).to(EmailServiceImpl.class);
		bind(TemplateService.class).to(TemplateServiceImpl.class);
		
		try
		{
			Properties properties = new Properties();
			InputStream inputStream = Class.class.getResourceAsStream("/" + FILENAME_PROPERTIES);
			properties.load(inputStream);
			
			Names.bindProperties(binder(), properties);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("The configuration file Test.properties can not be found");
		}
		catch (IOException e)
		{
			System.out.println("I/O Exception during loading configuration");
		}
	}
	
}