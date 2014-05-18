ticket-check-bot
================

This application was created so I could automatically check if more tickets became available after missing out on the intial batch for sale.

I wanted something I could set to run periodically and notify me if more tickets became available.

The application does not require a browser it uses htmlunit to navigate throug a target website and make a 'check' for something (in the sample given the presence of a 'buy tickets' button). The check could really be anything. Once found a note it made of the url and this will be assembled into an email sent to a number of recipients.

It's totally over-engineered of course (you could probably do it in about 10 lines of perl no doubt) but that's not the point is it? 

- Uses GUICE.
- Loads properties file into properties singleton using GUICE.
- Uses velocity to define any number of email templates and loads their definitions. You can technically extend the application to have different emails for different scenarios.
- Mavenized.
- Builds to a command line application for Windows or Linux which can be set to run periodically.

