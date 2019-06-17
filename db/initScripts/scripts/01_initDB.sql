drop schema if exists :DB_NAME;
create schema if not exists :DB_NAME AUTHORIZATION :DB_USER;
DROP schema if exists public;
ALTER DEFAULT PRIVILEGES FOR ROLE :DB_USER IN SCHEMA :DB_NAME GRANT ALL ON TABLES TO :DB_USER;
ALTER DEFAULT PRIVILEGES FOR ROLE :DB_USER IN SCHEMA :DB_NAME GRANT ALL ON SEQUENCES TO :DB_USER;
ALTER DEFAULT PRIVILEGES FOR ROLE :DB_USER IN SCHEMA :DB_NAME GRANT ALL ON FUNCTIONS TO :DB_USER;
ALTER DEFAULT PRIVILEGES FOR ROLE :DB_USER IN SCHEMA :DB_NAME GRANT ALL ON TYPES TO :DB_USER;

SET search_path TO :DB_NAME;
drop table if exists computer;
drop table if exists company;
create table company (
  id                        serial,
  name                      varchar(255),
  constraint pk_company primary key (id))
;

create table computer (
  id                        serial,
  name                      varchar(255),
  introduced                timestamp NULL,
  discontinued              timestamp NULL,
  company_id                bigint default NULL,
  constraint pk_computer primary key (id))
;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);