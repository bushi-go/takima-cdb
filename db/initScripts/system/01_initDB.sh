#!/bin/sh
source /opt/install/db.properties
psql -a -U $DB_USER -d $DB_NAME -f $SQL_SCRIPTS_DIR/01_initDB.sql -v DB_NAME=$DB_NAME -v DB_USER=$DB_USER