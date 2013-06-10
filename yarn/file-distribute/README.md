Spring Yarn File Distribute Example
=================================

To run this example, open a command window, go to the the spring-yarn-examples root directory, and type:

		./gradlew -q run-file-distribute -Dfiles=file:///C:/home/repos/git/spring-yarn-examples/basic/file-distribute/build/libs/file-distribute-1.0.0.M1.jar

To run it remotely, add parameters hdfs and resource manager:

		./gradlew -q run-file-distribute -Dhd.fs=hdfs://192.168.223.139:9000 -Dhd.rm=192.168.223.139:8032 -Dlocalresources.remote=hdfs://192.168.223.139:9000

Or to run from your IDE, run one of the following commands once.

		./gradlew eclipse
		./gradlew idea

Then import the project into your IDE and run Main.java

# Details

XXX
