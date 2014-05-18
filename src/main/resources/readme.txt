This resources folder is overridden by the standalone folder. 

Build the eclipse project with "-D eclipse" to bring the assembly folder into the project. E.g.

call %MAVEN_HOME%\bin\mvn -Declipse eclipse:clean eclipse:eclipse
