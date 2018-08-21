#! /usr/bin/env bash

# Credentials
HOST=$1
USERNAME=$2
PASSWORD=$3
PORT=$4
DBNAME=kbl_shc_agent
DATA_FOLDER=data

for f in `find $DATA_FOLDER -type f`
do
  DOC_PATH=$f

  # echo "$DOC_PATH"
  # cat "$DOC_PATH"

 curl -X POST -d "@$DOC_PATH" -H "Content-Type: application/json" http://$USERNAME:$PASSWORD@$HOST:$PORT/$DBNAME/
done
