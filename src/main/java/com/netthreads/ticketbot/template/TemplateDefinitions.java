package com.netthreads.ticketbot.template;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Velocity Templates.
 * 
 */
@Root
public class TemplateDefinitions
{
	@ElementList
	private List<TemplateDefinition> list;
	
	public List<TemplateDefinition> getList()
	{
		return list;
	}
	
}
