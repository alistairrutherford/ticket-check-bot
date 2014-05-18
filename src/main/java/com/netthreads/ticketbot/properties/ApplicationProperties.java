package com.netthreads.ticketbot.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Simple application properties class.
 * 
 */
@Singleton
public class ApplicationProperties
{
	private boolean sendMail;
	private String recipient;
	private String mailProtocol;
	private String mailHost;
	private String mailPort;
	private String mailUserName;
	private String mailPassword;
	private String mailFrom;
	private String mailStarttlsEnable;
	private String mailSmtpAuth;
	
	
	/**
	 * We user GUICE to bind the properties loaded in our application module to our central properties singleton.
	 * 
	 * @param sendMail
	 * @param recipient
	 * @param mailProtocol
	 * @param mailHost
	 * @param mailPort
	 * @param mailUserName
	 * @param mailPassword
	 * @param mailFrom
	 */
	@Inject
	public ApplicationProperties(@Named("sendMail") boolean sendMail, 
								@Named("recipient") String recipient, 
								@Named("mail.protocol") String mailProtocol,
								@Named("mail.host") String mailHost,
								@Named("mail.port") String mailPort,
								@Named("mail.username") String mailUserName,
								@Named("mail.password") String mailPassword,
								@Named("mail.from") String mailFrom,
								@Named("mail.smtp.starttls.enable") String mailStarttlsEnable,
								@Named("mail.smtp.auth") String mailSmtpAuth)
	
	{
		this.sendMail = sendMail;
		this.recipient = recipient;
		this.mailProtocol = mailProtocol;
		this.mailHost = mailHost;
		this.mailPort = mailPort;
		this.mailUserName = mailUserName;
		this.mailPassword = mailPassword;
		this.mailFrom = mailFrom;
		this.mailStarttlsEnable = mailStarttlsEnable;
		this.mailSmtpAuth = mailSmtpAuth;
	}
	
	public boolean isSendMail()
	{
		return sendMail;
	}
	
	public String getRecipient()
	{
		return recipient;
	}
	
	public String getMailProtocol()
	{
		return mailProtocol;
	}

	public String getMailHost()
	{
		return mailHost;
	}

	public String getMailPort()
	{
		return mailPort;
	}

	public String getMailUserName()
	{
		return mailUserName;
	}

	public String getMailPassword()
	{
		return mailPassword;
	}

	public String getMailFrom()
	{
		return mailFrom;
	}

	public String getMailStarttlsEnable()
	{
		return mailStarttlsEnable;
	}

	public String getMailSmtpAuth()
	{
		return mailSmtpAuth;
	}
	
}
