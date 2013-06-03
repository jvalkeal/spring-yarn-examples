Spring Yarn Custom Application Master Service Example
=====================================================

This example demonstrates the use of Spring Yarn functionality to create a custom application master service utilised by containers.

To run this example, open a command window, go to the the spring-yarn-examples root directory, and type:

		./gradlew -q run-custom-amservice

To run it remotely, add parameters hdfs and resource manager:

		./gradlew -q run-custom-amservice -Dhd.fs=hdfs://192.168.223.139:9000 -Dhd.rm=192.168.223.139:8032 -Dlocalresources.remote=hdfs://192.168.223.139:9000

Adding parameters for job count and average count of containers

		./gradlew -q run-custom-amservice -Dca.jb=10 -Dca.cc=2

Or to run from your IDE, run one of the following commands once.

		./gradlew eclipse
		./gradlew idea

Then import the project into your IDE and run Main.java

# Details

Majority of other examples are just launching containers and possibly passing some extra information
either using environment variables or command line parameters. This is perfectly suiteable if task or
job container is responsible is known prior the container launch operation.

This example is using customised container, application master and application master service order to
run simple dummy jobs. Application master is setup to execute a number of jobs on number of containers.
Communication between application master and container is done via customised application master service.
Containers are homing back to application master for instruction which can either be job run requests,
requests to wait or requests to die. Container also tries to simulate error conditions by just randomly
exiting itself.
