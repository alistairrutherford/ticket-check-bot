package com.netthreads.ticketbot.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Our template service will return a velocity template or information about the
 * template. This implementation fetches its template definitions from an xml
 * configuration file managed by the template repository.
 * 
 * So, we can have multiple named templates if we so require.
 * 
 */
@Singleton
public class TemplateServiceImpl implements TemplateService
{
	private VelocityEngine velocityEngine;
	
	private TemplateRepository templateRepository;
	
	/**
	 * Create instance of service.
	 * 
	 * @param templateRepository
	 *            The definition repository.
	 * 
	 * @throws Exception
	 */
	@Inject
	public TemplateServiceImpl(TemplateRepository templateRepository) throws Exception
	{
		this.templateRepository = templateRepository;
		
		this.templateRepository.load(TemplateRepository.DEFAULT_FILE_NAME);
		
		this.velocityEngine = new VelocityEngine();
		
		// We have to do the following to ensure the velocity engine can load a
		// stated template from the classpath.
		// http://stackoverflow.com/questions/9051413/unable-to-find-velocity-template-resources
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
	}
	
	/**
	 * Fetch template from class path.
	 * 
	 * @name The template name.
	 */
	public Template getTemplate(String name)
	{
		TemplateDefinition definition = getDefinition(name);
		
		String path = definition.getPath();
		
		// Load template from class path.
		Template target = velocityEngine.getTemplate(path);
		
		return target;
	}
	
	/**
	 * Return template definition record.
	 * 
	 * @param name
	 *            The name of template definition.
	 */
	public TemplateDefinition getDefinition(String name)
	{
		TemplateDefinition definition = templateRepository.get(name);
		
		return definition;
	}
	
	/**
	 * Return engine instance.
	 * 
	 * @return The engine instance.
	 */
	public VelocityEngine getVelocityEngine()
	{
		return velocityEngine;
	}
	
}
