#! /usr/bin/env bash

# Credentials
HOST=$1
USERNAME=$2
PASSWORD=$3
PORT=$4
DBNAME=kbl_"$5"_agent
DESIGN_FOLDER=_design
cd $DESIGN_FOLDER

#Delete current DB and create a brand new
curl -X DELETE http://$USERNAME:$PASSWORD@$HOST:$PORT/$DBNAME/
curl -X PUT http://$USERNAME:$PASSWORD@$HOST:$PORT/$DBNAME/

for d in `ls`
do
  #DOC_PATH=$d

  # echo "$DOC_PATH"
  # cat "$DOC_PATH"

 couchapp push $d http://$USERNAME:$PASSWORD@$HOST:$PORT/$DBNAME/
 
done
cd ..
