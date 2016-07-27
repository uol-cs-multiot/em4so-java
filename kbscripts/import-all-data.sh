#! /usr/bin/env bash

DATA_DIR="$HOME/workspace/git/em4so-java/examples/"

cd $DATA_DIR/saf/src/main
sh import-data.sh
cd $DATA_DIR/shc/src/main
sh import-data.sh
cd $DATA_DIR/scc/src/main
sh import-data.sh
cd $DATA_DIR/stv/src/main
sh import-data.sh
#cd $DATA_DIR/tab1/src/main
#sh import-data.sh
cd ~
