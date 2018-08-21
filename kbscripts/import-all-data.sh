#! /usr/bin/env bash

DATA_DIR="/couchapp/examples"
#echo $(pwd)
cd $DATA_DIR/saf/src/main
sh import-data.sh $1 $2 $3 $4
#cd $DATA_DIR/shc/src/main
#sh import-data.sh $1 $2 $3 $4
#cd $DATA_DIR/scc/src/main
#sh import-data.sh $1 $2 $3 $4
#cd $DATA_DIR/stv/src/main
#sh import-data.sh $1 $2 $3 $4
#cd $DATA_DIR/tab1/src/main
#sh import-data.sh
cd ~
