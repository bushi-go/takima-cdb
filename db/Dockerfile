FROM postgres:12-alpine

# Copying init shell scripts to initdb.d so they get run at container startup

WORKDIR /docker-entrypoint-initdb.d
COPY initScripts/system/* ./

# Copying pure sql scripts to a different folder so they don't get runned twice
WORKDIR /opt/install
COPY initScripts/scripts/* ./scripts/
COPY initUser.sh .
COPY --chown=postgres:postgres db.properties .
COPY --chown=postgres:postgres .pgpass .
RUN chown -R postgres:postgres /opt/install

ENTRYPOINT ["docker-entrypoint.sh"]

EXPOSE 5432
CMD ["postgres"]