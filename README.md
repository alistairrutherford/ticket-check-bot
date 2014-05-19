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

A module is added to run by the application by adding it to the loadModules method in the TicketBot class i.e.

	/**
	* Load site modules.
	* 
	* @param modules
	* @param client
	*/
	private void loadModules(List<BotModule> modules, WebClient client)
	{
		modules.add(new ModuleGigsAndTours(client));
	}

This is the current definition which takes the custom module I wrote for checking the gigsandtours website.


Email
-----

You will need to fill in your gmail credentials in the application-live.properties file. I have put dummy placemarkers in for the moment. Since this will be running on your own machine noone is going to be able to access it. If they can access your machine to look at your gmail password in this file you are already stuffed!

Build And Run
-------------
The component uses Maven.

To build issue the command line:

  mvn install
  
This will create a zip file of the component distribution. Unzip this file to your target machine and modify the properties to match your email etc.


For Windows there is a batch file for Linux a shell file. For Windows create a periodic task to run the batch file and for Linux setup a cron job.


