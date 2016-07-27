#! /usr/bin/env bash
SO_PATH=/home/mp/workspace/git/em4so-java
JAR_SOM="$SO_PATH/somanager/build/libs/somanager-all-0.1.jar"
JAR_SO="$SO_PATH/examples/$1/build/libs/$1-0.1.jar"
java -cp $JAR_SO:$JAR_SOM org.mp.em4so.launcher.SOMLauncher $SO_PATH/examples/$1/$1.yaml

