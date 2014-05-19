ticket-check-bot
================

This application was created so I could automatically check a target website if more tickets became available after missing out on the intial batch for sale.

I wanted something I could set to run periodically and which would notify me by email.

I initially started using selenium but soon realised that I didn't need a browser to navigate and scan a target sote so settled on htmlunit instead.

The email component is setup to use gmail as a smtp gateway so you will need an gmail account with two-step validation switched off.

It's totally over-engineered of course (you could probably do it in about 10 lines of perl no doubt) but that's not the point is it? 

- Uses GUICE.
- Loads properties file into properties singleton using GUICE.
- Uses velocity to define any number of email templates and loads their definitions. You can technically extend the application to have different emails for different scenarios.
- Concept of modules which return a list of links (the condition under which the links are selected is custom to the module).
- Mavenized.
- Builds to a command line application for Windows or Linux which can be set to run periodically.

Modules
-------
A module subclasses from the ModuleBot class and implements the interface:

public interface BotModule
{
	/**
	 * Run module.
	 * 
	 */
	public void run();

	/**
	 * Return list of valid links.
	 * 
	 * @return links.
	 */
	public List<String> getLinkStatus();
}

It is expected that the 'run' method will populate a list of URL's which are returned in the call to 'getLinkStatus'. The check to do this can be anything. In my sample I navigate to the gigsandtours website and look for the presence of a 'buy ticket' button. You could make it anything your were watching for.

