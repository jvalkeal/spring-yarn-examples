Spring Yarn Simple Command Example
==================================

To test this example:

		./gradlew clean :yarn-examples-common:yarn-examples-simple-command:build

To run this example against local Hadoop cluster:

		./gradlew -q run-yarn-examples-simple-command

To run this example against remote Hadoop cluster:

		./gradlew -q run-yarn-examples-simple-command -Dhd.fs=hdfs://192.168.223.139:9000 -Dhd.rm=192.168.223.139:8032 -Dlocalresources.remote=hdfs://192.168.223.139:9000

# Details

XXX
