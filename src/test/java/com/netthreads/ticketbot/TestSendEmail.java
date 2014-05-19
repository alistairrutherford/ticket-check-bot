package com.netthreads.ticketbot;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.netthreads.ticketbot.email.EmailData;
import com.netthreads.ticketbot.email.EmailService;

/**
 * Email send test.
 *
 */
public class TestSendEmail
{
	@Test
	public void testSend()
	{
		// Use our test guice bindings (e.g. we load a different set of properties).
		Injector injector = Guice.createInjector(new TestModule());
		
		EmailService emailService = injector.getInstance(EmailService.class);
		
		EmailData emailData = new EmailData();
		
		emailData.setEmailContents("This is a test");
		emailData.setSubject("Test");
		
		junit.framework.Assert.assertTrue(emailService.sendMail(emailData));
	}
}
