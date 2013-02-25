Spring Yarn Examples
====================

This project provides a number of examples to get you started using Spring Yarn. These examples are designed to work with [Spring Yarn] (http://www.springsource.org/spring-yarn) 1.0 or higher and are organized into the following sub projects:

# Basic

These examples are focused more on configuring GemFire components such as Caches and Regions to address various scenarios. This currently includes

* list-applications - A simple example of listing applications known to resource manager
* simple-command - A simple example of running a command with containers
* multi-context - A simple example of running a spring application context on multiple containers

# Running The Examples

This project is built with gradle and each example may be run with gradle or within your Java IDE. If you are using Eclipse or SpringSource Tool Suite, go to the directory where you downloaded this project and type:

        ./gradlew eclipse

If you are using IDEA, 

        ./gradlew idea

Detailed instructions for each example may be found in its own README file.
