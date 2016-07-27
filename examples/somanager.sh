#! /usr/bin/env bash
#This script will work if you pass as arguments: (1) name of the SO to run (2) name of the linux user for home directory. The folder git project root should be in the SO_PATH.
SO_PATH=/home/$2/workspace/git/em4so-java
JAR_SOM="$SO_PATH/somanager/build/libs/somanager-all-0.1.jar"
JAR_SO="$SO_PATH/examples/$1/build/libs/$1-0.1.jar"
java -cp $JAR_SO:$JAR_SOM org.mp.em4so.launcher.SOMLauncher $SO_PATH/examples/$1/$1.yaml

