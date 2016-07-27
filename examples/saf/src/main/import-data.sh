#! /usr/bin/env bash

# HOWTO
#
# 0. Try this - wget https://gist.githubusercontent.com/korczis/2be371aedfb2575f6b9c/raw/82dd096034ab7a67371bb3aab4b1c3be97477df5/import-data.sh && chmod 755 ./import-data.sh && ./import-data.sh
# 
# If the step "0" not works for you try following these steps
#
# 1. download this bash script to firmy.cz
# 2. make sure all data are in subfolder "data"
# 3. make sure you have couchdb up and running
# 4. make sure you have created database "firmy"
# 5. modify credentials if required
# 6. run this script "./import-data.sh"

# Credentials
USERNAME=admin
PASSWORD=password
HOST=localhost
PORT=5984
DBNAME=kbl_saf_agent
DATA_FOLDER=data

for f in `find $DATA_FOLDER -type f`
do
  DOC_PATH=$f

  # echo "$DOC_PATH"
  # cat "$DOC_PATH"

 curl -X POST -d "@$DOC_PATH" -H "Content-Type: application/json" http://$USERNAME:$PASSWORD@$HOST:$PORT/$DBNAME/
done
