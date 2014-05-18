package com.netthreads.ticketbot.template;

import org.apache.velocity.Template;

/**
 * Service which returns named velocity template definition or objects.
 *
 */
public interface TemplateService
{
	public Template getTemplate(String name);
	
	public TemplateDefinition getDefinition(String name);
}
