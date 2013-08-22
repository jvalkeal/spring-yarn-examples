Spring Yarn Examples
====================

This project provides a number of examples to get you started using Spring Yarn and Spring Hadoop. These examples are designed to work with [Spring Hadoop] (http://www.springsource.org/spring-hadoop) 2.0 or higher and are organized into the following sub projects:

# Yarn Examples

* list-applications - A simple example of listing applications known to resource manager
* simple-command - A simple example of running a command with containers
* multi-context - A simple example of running a spring application context on multiple containers

# Imporing Examples to IDE

This project is built with gradle and each example may be imported to your Java IDE. If you are using Eclipse or SpringSource Tool Suite, go to the directory where you downloaded this project and type:

        ./gradlew eclipse

If you are using IDEA, 

        ./gradlew idea

# Running Examples 

Detailed instructions for each example may be found in its own README file.

# General Prerequisites

You can collect maven dependencies for example running gradle task *copyLibDeps*
under *yarn-examples-common* project.
Dependant libs are then collected under yarn/build/dependency-libs/. 
```
# gradlew clean :yarn-examples-common:copyLibDeps
```
There is one jar file per example. To build all examples use gradle build task.
```
# gradlew clean build
```

Every example jar file has its own directory location in HDFS.

```
[root@centos hadoop]# hadoop/bin/hdfs dfs -ls /lib
/lib/aopalliance-1.0.jar
/lib/spring-aop-3.1.3.RELEASE.jar
/lib/spring-asm-3.1.3.RELEASE.jar
/lib/spring-batch-core-2.1.9.RELEASE.jar
/lib/spring-batch-infrastructure-2.1.9.RELEASE.jar
/lib/spring-beans-3.1.3.RELEASE.jar
/lib/spring-context-3.1.3.RELEASE.jar
/lib/spring-context-support-3.0.7.RELEASE.jar
/lib/spring-core-3.1.3.RELEASE.jar
/lib/spring-data-hadoop-2.0.0.BUILD-SNAPSHOT.jar
/lib/spring-data-hadoop-batch-2.0.0.BUILD-SNAPSHOT.jar
/lib/spring-data-hadoop-core-2.0.0.BUILD-SNAPSHOT.jar
/lib/spring-expression-3.1.3.RELEASE.jar
/lib/spring-integration-core-2.2.3.RELEASE.jar
/lib/spring-integration-ip-2.2.3.RELEASE.jar
/lib/spring-integration-stream-2.2.3.RELEASE.jar
/lib/spring-jdbc-3.0.7.RELEASE.jar
/lib/spring-retry-1.0.2.RELEASE.jar
/lib/spring-tx-3.1.3.RELEASE.jar
/lib/spring-yarn-batch-2.0.0.BUILD-SNAPSHOT.jar
/lib/spring-yarn-core-2.0.0.BUILD-SNAPSHOT.jar
/lib/spring-yarn-integration-2.0.0.BUILD-SNAPSHOT.jar
/app/multi-context/yarn-examples-multi-context-2.0.0.BUILD-SNAPSHOT.jar
/app/yarn-examples-common-2.0.0.BUILD-SNAPSHOT.jar
...
```
