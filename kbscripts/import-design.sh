#! /usr/bin/env bash

# HOWTO
#
#run from outside the DESIGN_FOLDER directory, passing the name of the SO (not the full database)

# Credentials
USERNAME=admin
PASSWORD=password
HOST=localhost
PORT=5984
DBNAME=kbl_"$1"_agent
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