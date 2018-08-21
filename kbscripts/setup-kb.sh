#! /usr/bin/env bash

HOST=$1
USERNAME=$2
PASSWORD=$3
PORT=$4
echo "starting setup..."
./import-all-design.sh $HOST $USERNAME $PASSWORD $PORT
echo "starting data load..."
./import-all-data.sh $HOST $USERNAME $PASSWORD $PORT
