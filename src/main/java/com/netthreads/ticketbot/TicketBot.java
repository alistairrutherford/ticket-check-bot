package com.netthreads.ticketbot;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.netthreads.ticketbot.email.EmailData;
import com.netthreads.ticketbot.email.EmailService;
import com.netthreads.ticketbot.module.BotModule;
import com.netthreads.ticketbot.module.ModuleGigsAndTours;
import com.netthreads.ticketbot.template.TemplateDefinition;
import com.netthreads.ticketbot.template.TemplateService;

/**
 * Main worker class.
 * 
 */
@Singleton
public class TicketBot
{
	private static final Logger LOG = LoggerFactory.getLogger(TicketBot.class);
	
	private List<BotModule> modules;
	
	private static WebClient webClient = null;
	
	@Inject
	private EmailService emailService;
	
	@Inject
	private TemplateService templateService;
	
	private VelocityContext context;
	
	private static final String TEMPLATE_VALID_LINKS = "links";
	
	private static final String TEMPATE_NAME = "availability";
	
	/**
	 * Construct main worker class.
	 * 
	 */
	public TicketBot()
	{
		context = new VelocityContext();
		
		modules = new LinkedList<>();
		
		webClient = new WebClient();
		
		loadModules(modules, webClient);
	}
	
	/**
	 * Run module commands in sequence.
	 * 
	 */
	public void go()
	{
		runModules(modules);
		
		generateEmail(modules);
	}
	
	/**
	 * Load site modules.
	 * 
	 * @param modules
	 * @param client
	 */
	private void loadModules(List<BotModule> modules, WebClient client)
	{
		modules.add(new ModuleGigsAndTours(webClient));
	}
	
	/**
	 * Run module tasks.
	 * 
	 * @param modules
	 */
	private void runModules(List<BotModule> modules)
	{
		for (BotModule module : modules)
		{
			module.run();
		}
	}
	
	/**
	 * Generate an email with successful modules.
	 * 
	 * @param modules
	 */
	private void generateEmail(List<BotModule> modules)
	{
		List<String> validLinks = new LinkedList<>();
		
		// Collect any valid links together.
		for (BotModule module : modules)
		{
			List<String> linkStatus = module.getLinkStatus();
			
			validLinks.addAll(linkStatus);
		}
		
		// If there are any valid links then we will send an email out the
		// configured recipients.
		if (validLinks.size() > 0)
		{
			LOG.info("Generating email with valid links");
			
			EmailData emailData = buildEmailData(validLinks);
			
			if (emailData != null)
			{
				emailService.sendMail(emailData);
			}
			else
			{
				LOG.error("Could not generate email.");
			}
			
		}
	}
	
	/**
	 * Build email data items.
	 * 
	 * @param validLinks
	 * 
	 * @return The email data record.
	 */
	private EmailData buildEmailData(List<String> validLinks)
	{
		EmailData emailData = null;
		
		TemplateDefinition definition = templateService.getDefinition(TEMPATE_NAME);
		
		Template template = templateService.getTemplate(TEMPATE_NAME);
		
		if (template != null)
		{
			emailData = generateEmailData(validLinks, definition, template);
		}
		
		return emailData;
	}
	
	/**
	 * Take links and generate email from template.
	 * 
	 * @param validLinks
	 * @param definition
	 * @param template
	 * 
	 * @return Populated email data.
	 */
	private EmailData generateEmailData(List<String> validLinks, TemplateDefinition definition, Template template)
	{
		EmailData emailData = new EmailData();
		
		clearContext();
		
		// Add valid links to email.
		context.put(TEMPLATE_VALID_LINKS, validLinks);
		
		// Assemble contents
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		// Render velocity template into text.
		String contents = writer.toString();
		emailData.setEmailContents(contents);
		
		// Set the subject to the defined template description.
		emailData.setSubject(definition.getDescription());
		
		return emailData;
	}
	
	/**
	 * Clear context of data.
	 * 
	 * @param key
	 */
	private void clearContext()
	{
		Object[] keys = context.getKeys();
		
		for (Object key : keys)
		{
			context.remove(key);
		}
	}
}
