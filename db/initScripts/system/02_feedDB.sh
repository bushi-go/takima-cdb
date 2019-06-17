#!/bin/sh
source /opt/install/db.properties
psql -a -U $DB_USER -d $DB_NAME -f $SQL_SCRIPTS_DIR/02_feedDB.sql -v DB_NAME=$DB_NAME