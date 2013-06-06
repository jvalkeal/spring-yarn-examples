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

# General Prerequisites

You can collect maven dependencies for example running gradle task *dep-complex*.
Dependant libs are then collected under *build/dependency-libs/*. Not all files are
needed so check below files which should be copied into HDFS.
```
# gradlew dep-complex
```
There is one jar file per example. To build all examples use gradle build task.
```
# gradlew clean build
```

Every example jar file has its own directory location in HDFS.

```
[root@centos hadoop]# hadoop/bin/hdfs dfs -ls /lib
/lib/aopalliance-1.0.jar
/lib/jackson-annotations-2.1.4.jar
/lib/jackson-core-2.1.4.jar
/lib/jackson-databind-2.1.4.jar
/lib/spring-aop-3.2.1.RELEASE.jar
/lib/spring-batch-core-2.1.9.RELEASE.jar
/lib/spring-batch-infrastructure-2.1.9.RELEASE.jar
/lib/spring-beans-3.2.1.RELEASE.jar
/lib/spring-context-3.2.1.RELEASE.jar
/lib/spring-context-support-3.2.1.RELEASE.jar
/lib/spring-core-3.2.1.RELEASE.jar
/lib/spring-data-hadoop-1.0.0.RELEASE.jar
/lib/spring-expression-3.2.1.RELEASE.jar
/lib/spring-integration-core-2.2.1.RELEASE.jar
/lib/spring-integration-ip-2.2.1.RELEASE.jar
/lib/spring-integration-stream-2.2.1.RELEASE.jar
/lib/spring-jdbc-3.2.1.RELEASE.jar
/lib/spring-tx-3.2.1.RELEASE.jar
/app/spring-yarn-batch-1.0.0.BUILD-SNAPSHOT.jar
/app/spring-yarn-core-1.0.0.BUILD-SNAPSHOT.jar
/app/spring-yarn-integration-1.0.0.BUILD-SNAPSHOT.jar
/app/batch-files/batch-files-1.0.0.M1.jar
...
```
