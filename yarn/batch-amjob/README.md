Spring Yarn Batch Application Master Job Example
================================================

To test this example:

		./gradlew clean :yarn-examples-common:yarn-examples-batch-amjob:build

To run this example against local Hadoop cluster:

		./gradlew -q run-yarn-examples-batch-amjob
		./gradlew -q run-yarn-examples-batch-amjob -DjobName=job2

To run this example against remote Hadoop cluster:

		./gradlew -q run-yarn-examples-batch-amjob -Dhd.fs=hdfs://192.168.223.139:8020 -Dhd.rm=192.168.223.139:8032 -Dlocalresources.remote=hdfs://192.168.223.139:8020

# Details

This example demonstrates the use of Spring Yarn functionality to run
Spring Batch jobs on an Application Master without starting
any Containers.
