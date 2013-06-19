JBehave Support for IntelliJ IDEA
=================================

IntelliJ IDEA Plugin for JBehave

This plugin provides some support for JBehave.
It is a fork of IntelliJBehave, originally created by Aman Kumar.

See https://github.com/kumaraman21/IntelliJBehave/wiki
    
Most of the original code has been retained, but several improvements have been incorporated by various contributors:
* https://github.com/jarosite
* https://github.com/Arnauld
* https://github.com/harley84
    
Features
--------
The plugin provides the following features:
* Basic syntax highlighting for JBehave story files
* Jump to step definition in Java or Groovy
* Error Highlighting in story if step was not defined
* Create new story files from a configurable story template
* Comment/uncomment lines in story files
* Code inspections to report unused steps definitions and undefined step usages
* Run *.story files

Known limitations
-----------------
* Searches complete module classpath, no configuration available to limit scope
* Does not take into account any custom JBehave configuration
