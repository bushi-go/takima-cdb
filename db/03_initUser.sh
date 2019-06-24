#!/bin/sh
source /opt/install/db.properties
adduser -S $DB_USER
mv /opt/install/.pgpass /home/$DB_USER