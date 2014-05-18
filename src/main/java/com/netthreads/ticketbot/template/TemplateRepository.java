package com.netthreads.ticketbot.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

/**
 * Repository of template definitions.
 * 
 */
@Singleton
public class TemplateRepository
{
	private static Logger log = LoggerFactory.getLogger(TemplateRepository.class);

	public static final String DEFAULT_FILE_NAME = "templates.xml";

	private Map<String, TemplateDefinition> definitions = null;

	/**
	 * Constructor.
	 * 
	 * @throws Exception
	 */
	public TemplateRepository()
	{
		definitions = new HashMap<String, TemplateDefinition>();
	}

	/**
	 * Load labels lists.
	 * 
	 * @param name
	 *            The resource file name.
	 * @throws Exception
	 */
	public void load(String name) throws Exception
	{
		log.debug("Load template definitions..");

		Serializer serializer = new Persister();

		InputStream inputStream = getClass().getResourceAsStream("/" + name);

		TemplateDefinitions items = serializer.read(TemplateDefinitions.class, inputStream);

		if (items == null)
		{
			throw new IOException("Can't read template definitions.");
		}

		populate(items);
	}

	/**
	 * Populate structure from list.
	 * 
	 * @param items
	 *            The data items.
	 */
	private void populate(TemplateDefinitions items)
	{
		List<TemplateDefinition> definitionItems = items.getList();
		
		for (TemplateDefinition definition : definitionItems)
		{
			String name = definition.getName();

			definitions.put(name, definition);
		}
	}

	/**
	 * Fetch repository map.
	 * 
	 * @param name
	 *            Target name.
	 * 
	 * @return The repository map
	 */
	public TemplateDefinition get(String name)
	{
		TemplateDefinition definition = definitions.get(name);

		return definition;
	}

	/**
	 * Return master definition map.
	 * 
	 * @return The definitions map.
	 */
	public Map<String, TemplateDefinition> getDefinitions()
	{
		return definitions;
	}

}
