package com.netthreads.ticketbot.template;

import org.simpleframework.xml.Attribute;

/**
 * Template definition.
 * 
 */
public class TemplateDefinition
{
	@Attribute(name = "name")
	private String name;
	
	@Attribute(name = "path")
	private String path;
	
	@Attribute(name = "description")
	private String description;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
}
