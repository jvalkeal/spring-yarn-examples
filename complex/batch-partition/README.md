Spring Yarn Batch Partition Example
===================================

This example demonstrates the use of Spring Yarn functionality to run Spring Batch jobs utilising Yarn cluster resources by partitioning tasks to run on different containers. 

To run this example, open a command window, go to the the spring-yarn-examples root directory, and type:

		./gradlew -q run-batch-partition
		
To run it remotely, add parameters hdfs and resource manager:
		
		./gradlew -q run-batch-partition -Dhd.fs=hdfs://192.168.223.139:9000 -Dhd.rm=192.168.223.139:8032 -Dlocalresources.remote=hdfs://192.168.223.139:9000

Or to run from your IDE, run one of the following commands once.

		./gradlew eclipse
		./gradlew idea 

Then import the project into your IDE and run Main.java

# Details

This example demonstrates a simple batch process by running a *PrintTasklet* on
partitioned steps on two different stages. In *appmaster-context.xml* we define
*StaticBatchPartitionHandler* to use 2 containers and the actual job is run with
two steps.
```
<bean id="partitionHandler" class="org.springframework.yarn.batch.partition.StaticBatchPartitionHandler">
  <constructor-arg index="0"><ref bean="yarnAppmaster"/></constructor-arg>
  <constructor-arg index="1" value="2"/>
  <property name="stepName" value="remoteStep"/>
</bean>

<batch:job id="job">
  <batch:step id="master1" next="master2">
    <batch:partition partitioner="partitioner" handler="partitionHandler"/>
  </batch:step>
  <batch:step id="master2">
    <batch:partition partitioner="partitioner" handler="partitionHandler"/>
  </batch:step>
</batch:job>
```

What happens on a background is execution of two steps *master1* and *master2*
respectively where latter is dependent on former. We have 3 nodes, two steps each
partitioned in 2 containers. This should result 4 containers to be run. 
```
centos:
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000002/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000007/Container.stdout
centos1:
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000001/Appmaster.stdout
centos2:
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000006/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000003/Container.stdout
```

We can then verify result by grepping *Hello* from container logs.
```
[root@centos hadoop]# grep Hello hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_0000*/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000002/Container.stdout:INFO [main] [2013-05-23 05:21:08] [PrintTasklet] - execute2: Hello
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000007/Container.stdout:INFO [main] [2013-05-23 05:21:22] [PrintTasklet] - execute2: Hello
[root@centos2 hadoop]# grep Hello hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_0000*/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000003/Container.stdout:INFO [main] [2013-05-23 05:17:18] [PrintTasklet] - execute2: Hello
hadoop/logs/userlogs/application_1368793613271_0064/container_1368793613271_0064_01_000006/Container.stdout:INFO [main] [2013-05-23 05:17:32] [PrintTasklet] - execute2: Hello
```

On its own this example is not very useful but demonstrates the basics of batch partitioning.
User then have a total control what those partitioned steps should actually do. Other batch
example *batch-files* will then take this concept much further by passing more
job parameters into containers and do more real life data processing.
