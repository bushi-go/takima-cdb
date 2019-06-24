FROM postgres:12-alpine

# Copying init shell scripts to initdb.d so they get run at container startup

WORKDIR /docker-entrypoint-initdb.d
COPY initScripts/system/* ./

# Copying pure sql scripts to a different folder so they don't get runned twice
WORKDIR /opt/install
COPY initScripts/scripts/* ./scripts/
COPY --chown=postgres:postgres configuration/db.properties .
COPY --chown=postgres:postgres configuration/.pgpass .
COPY 03_initUser.sh .
RUN chmod +x 03_initUser.sh
RUN chown -R postgres:postgres /opt/install

WORKDIR /etc/postgresql
COPY --chown=postgres:postgres configuration/pg_hba.conf .
COPY --chown=postgres:postgres configuration/postgresql.conf .
RUN chmod 0600 /etc/postgresql/pg_hba.conf
RUN chmod 0600 /etc/postgresql/postgresql.conf

ENTRYPOINT ["docker-entrypoint.sh"]

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf", "-c","hba_file=/etc/postgresql/pg_hba.conf"]