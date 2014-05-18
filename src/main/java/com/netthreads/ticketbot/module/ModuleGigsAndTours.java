package com.netthreads.ticketbot.module;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Module to scan "gigs and tours" web-site for available tickets.
 *
 */
public class ModuleGigsAndTours implements BotModule
{
	private static final Logger LOG = LoggerFactory.getLogger(ModuleGigsAndTours.class);
	
	private static final String URL_LIST =	 "http://www.gigsandtours.com/tour/prince";
	private static final String URL_LINK = "/event/prince";
		
	// private static final String URL_LIST = "http://www.gigsandtours.com/tour/kate-bush";
	// private static final String URL_LINK = "/event/kate-bush";
	
	//	private static final String URL_LIST =	 "http://www.gigsandtours.com/tour/little-mix";
	//	private static final String URL_LINK = "/event/little-mix";
	
	private static final String URL_HTTP = "http://";
	private static final String ID_BUTTON_BUY_TICKETS = "buyTickets";
	
	private String baseURL;
	private WebClient webClient;
	private List<String> links;
	private List<String> linkStatus;
	
	/**
	 * Construct site module.
	 * 
	 * @param webClient
	 */
	public ModuleGigsAndTours(WebClient webClient)
	{
		this.webClient = webClient;
		
		this.links = null;
		
		this.baseURL = "";
		
		try
		{
			URL base = new URL(URL_LIST);
			
			baseURL = base.getHost();
		}
		catch (MalformedURLException e)
		{
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * Process target site.
	 * 
	 */
	@Override
	public void run()
	{
		try
		{
			// Switch off java-script.
			webClient.getOptions().setJavaScriptEnabled(false);
			
			HtmlPage page = webClient.getPage(URL_LIST);
			
			LOG.info("Fetch links");
			
			links = fetchLinks(page);
			
			for (String link : links)
			{
				LOG.info("Link: " + link);
			}
			
			LOG.info("Examine availability");
			
			linkStatus = fetchLinkStatus(links);
			
			for (String element : linkStatus)
			{
				LOG.info("Tickets available at: " + element);
			}
		}
		catch (FailingHttpStatusCodeException e)
		{
			LOG.error(e.getMessage());
		}
		catch (MalformedURLException e)
		{
			LOG.error(e.getMessage());
		}
		catch (IOException e)
		{
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * Fetch links.
	 * 
	 * @return list of links.
	 */
	private List<String> fetchLinks(HtmlPage page)
	{
		List<String> validLinks = new LinkedList<>();
		
		try
		{
			// final List<?> links = page.getByXPath("//a");
			final List<?> links = page.getAnchors();
			
			for (Object element : links)
			{
				HtmlAnchor anchor = (HtmlAnchor) element;
				
				String path = anchor.getHrefAttribute();
				
				if (path.contains(URL_LINK))
				{
					String url = URL_HTTP + baseURL + path;
					
					validLinks.add(url);
				}
			}
		}
		catch (Throwable t)
		{
			LOG.error(t.getMessage());
		}
		
		return validLinks;
	}
	
	/**
	 * Find valid links.
	 * 
	 * @param pageLinks
	 * @param homeURL
	 * 
	 * @return Map of URLS with target criteria.
	 */
	private List<String> fetchLinkStatus(List<String> pageLinks)
	{
		List<String> validLinks = new LinkedList<>();
		
		// For each link check status.
		for (String link : pageLinks)
		{
			// Navigate to details page.
			try
			{
				HtmlPage page = webClient.getPage(link);
				
				// Object button = page.getByXPath("//id='" +
				// ID_BUTTON_BUY_TICKETS + "'");
				Object button = page.getElementById(ID_BUTTON_BUY_TICKETS);
				
				if (button != null)
				{
					validLinks.add(link);
				}
				
			}
			catch (FailingHttpStatusCodeException e)
			{
				LOG.error(e.getMessage());
			}
			catch (MalformedURLException e)
			{
				LOG.error(e.getMessage());
			}
			catch (IOException e)
			{
				LOG.error(e.getMessage());
			}
		}
		
		return validLinks;
	}
	
	/**
	 * Loaded links.
	 * 
	 * @return List of links.
	 */
	public List<String> getLinks()
	{
		return links;
	}
	
	/**
	 * Return valid links.
	 * 
	 */
	public List<String> getLinkStatus()
	{
		return linkStatus;
	}
}
