package com.netthreads.ticketbot.email;


public interface EmailService
{
	/**
	 * Send an email using the supplied object.
	 * 
	 * @param emailData
	 * 
	 * @return True if successful.
	 */
	public boolean sendMail(EmailData emailData);
}
