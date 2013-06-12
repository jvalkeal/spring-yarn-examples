Spring Yarn Batch Application Master Job Example
================================================

This example demonstrates the use of Spring Yarn functionality to run Spring Batch jobs utilising Yarn cluster resources by partitioning tasks to run on different containers. 

To run this example, open a command window, go to the the spring-yarn-examples root directory, and type:

		./gradlew -q run-yarn-examples-batch-amjob
		
To run it remotely, add parameters hdfs and resource manager:
		
		./gradlew -q run-yarn-examples-batch-amjob -Dhd.fs=hdfs://192.168.223.139:9000 -Dhd.rm=192.168.223.139:8032 -Dlocalresources.remote=hdfs://192.168.223.139:9000

Or to run from your IDE, run one of the following commands once.

		./gradlew eclipse
		./gradlew idea 

Then import the project into your IDE and run Main.java

# Details

