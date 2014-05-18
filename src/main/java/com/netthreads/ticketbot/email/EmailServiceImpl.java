package com.netthreads.ticketbot.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.netthreads.ticketbot.properties.ApplicationProperties;

/**
 * Email service.
 * 
 */
@Singleton
public class EmailServiceImpl implements EmailService
{
	private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Inject
	private ApplicationProperties applicationProperties;

	/**
	 * Email service.
	 * 
	 * We wire in beans from the context.
	 * 
	 * @param mailSender
	 * @param templateSimpleMailMessage
	 */
	public EmailServiceImpl()
	{
	}

	/**
	 * Send an email using the supplied object.
	 * 
	 * @param emailData
	 * 
	 * @return True if successful.
	 */
	public boolean sendMail(EmailData emailData)
	{
		boolean status = false;

		String recipient = applicationProperties.getRecipient();

		try
		{
			// Recipient list is list of addresses separated by semi-colon.
			String[] recipients = recipient.split(";");

			if (recipients != null && recipients.length > 0)
			{
				// We can suppress the sending of mails.
				if (applicationProperties.isSendMail())
				{
					// Sender's email ID needs to be mentioned
					String from = applicationProperties.getMailFrom();

					// Get system properties
					Properties properties = System.getProperties();

					String host = applicationProperties.getMailHost();
					String user = applicationProperties.getMailUserName();
					String password = applicationProperties.getMailPassword();

					// Setup mail server
					properties.setProperty("mail.smtp.host", host);
					properties.setProperty("mail.smtp.port", applicationProperties.getMailPort());

					// Are we using authentication?
					if (user != null && !user.isEmpty())
					{
						properties.setProperty("mail.smtp.starttls.enable", applicationProperties.getMailStarttlsEnable());
						properties.put("mail.smtp.auth", applicationProperties.getMailSmtpAuth());

						properties.put("mail.smtp.user", applicationProperties.getMailFrom());
						properties.put("mail.smtp.password", password);
					}

					// -------------------------------------------------------
					// Send email
					// -------------------------------------------------------

					try
					{
						// Get the default Session object.
						Session session = Session.getDefaultInstance(properties);

						// Create a default MimeMessage object.
						MimeMessage message = new MimeMessage(session);

						// Set From: header field of the header.
						message.setFrom(new InternetAddress(from));

						// Set To: header field of the header.
						Address[] addresses = buildRecipientAddress(recipients);

						message.addRecipients(Message.RecipientType.TO, addresses);

						// Set Subject: header field
						message.setSubject(emailData.getSubject());

						// Now set the actual message
						message.setText(emailData.getEmailContents());

						// Send message
						Transport transport = session.getTransport(applicationProperties.getMailProtocol());
						if (user != null && !user.isEmpty())
						{
							transport.connect(host, user, password);
						}
						else
						{
							transport.connect();
						}

						// What's done can't be undone..
						transport.sendMessage(message, message.getAllRecipients());

						// Close
						transport.close();

						logger.info("Sent message successfully....");
					}
					catch (MessagingException mex)
					{
						logger.error("Error: " + mex);
					}
				}
				else
				{
					logger.info("Debug mode enabled, not sent.");
				}

				// If we get this far then no errors.
				status = true;
			}
			else
			{
				logger.warn("No recpients assigned...check the configuruation.");
			}
		}
		catch (Throwable t)
		{
			logger.error("Email failed to send " + ", " + recipient + ", " + emailData.getSubject(), t);
		}

		return status;
	}

	/**
	 * Build recipient address structure.
	 * 
	 * @param recipients
	 * 
	 * @return Array of addresses.
	 */
	private Address[] buildRecipientAddress(String[] recipients)
	{
		Address[] addresses = new Address[recipients.length];

		int index = 0;
		for (String recipient : recipients)
		{
			try
			{
				Address address = new InternetAddress(recipient);
				addresses[index++] = address;
			}
			catch (AddressException e)
			{
				logger.error(e.getMessage());
			}
		}

		return addresses;
	}

}
