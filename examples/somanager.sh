#! /usr/bin/env bash
#This script requires this arguments: 
#(1) name of the SO to run 
#(2) name of the linux user for home directory where the git project is installed. The repository root should be in the SO_PATH.
#(3) [OPTIONAL] Additional jar files needed to run the app.


SO_PATH=/home/$2/workspace/git/em4so-java

JAR_SOM="$SO_PATH/somanager/build/libs/somanager-all-0.1.jar"
JAR_SO="$SO_PATH/examples/$1/build/libs/$1-0.1.jar"


if [ -z "$1" ]
  then
    echo "No Smart Object name supplied"
  else
	if [ -z "$2" ]
  	then
    	echo "No installation directory supplied"
	else
	if [ -z "$3" ]
  	then
	SO_CP=$JAR_SO:$JAR_SOM
	else
	SO_CP=$JAR_SO:$JAR_SOM:$3
	fi
	java -cp $SO_CP org.mp.em4so.launcher.SOMLauncher $SO_PATH/examples/$1/$1.yaml	
   fi

fi



