#! /usr/bin/env bash

# Credentials
HOST=$1
USERNAME=$2
PASSWORD=$3
PORT=$4
DBNAME=kbl_saf_agent
DATA_FOLDER=data

echo "running setup"
for f in `find $DATA_FOLDER -type f`
do
  DOC_PATH=$f
  curl -X POST -d "@$DOC_PATH" -H "Content-Type: application/json" http://$USERNAME:$PASSWORD@$HOST:$PORT/$DBNAME/
done
