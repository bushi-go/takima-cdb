#!/bin/sh
source /opt/install/db.properties
psql -f $SQL_SCRIPTS_DIR/00_createDB.sql -v DB_NAME=$DB_NAME -v DB_USER=$DB_USER -v DB_USER_PASSWORD=$DB_USER_PASSWORD
mv /opt/install/db.properties /opt/install/db.properties_old
sed -n '/DB_USER_PASSWORD=.*/!p' /opt/install/db.properties_old > /opt/install/db.properties
rm /opt/install/db.properties_old
