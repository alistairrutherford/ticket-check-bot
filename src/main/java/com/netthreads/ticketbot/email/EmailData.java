package com.netthreads.ticketbot.email;


/**
 * Represents the recipient and message for an email.
 * 
 */
public class EmailData
{
	private String emailContents;
	private String subject;
	
	public String getEmailContents()
	{
		return emailContents;
	}
	
	public void setEmailContents(String emailContents)
	{
		this.emailContents = emailContents;
	}
	
	public String getSubject()
	{
		return subject;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
}
