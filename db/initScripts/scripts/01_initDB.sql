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
  cpy_id                        serial,
  name                      varchar(255),
  constraint pk_company primary key (cpy_id))
;

create SEQUENCE company_seq INCREMENT BY 1 START 43 OWNED BY company.cpy_id;

create table computer (
  cpt_id                        serial,
  name                      varchar(255),
  introduced                timestamp NULL,
  discontinued              timestamp NULL,
  company_cpy_id                bigint default NULL,
  constraint pk_computer primary key (cpt_id))
;

create SEQUENCE computer_seq INCREMENT BY 1 START 574 OWNED BY computer.cpt_id;

alter table computer add constraint fk_computer_company_1 foreign key (company_cpy_id) references company (cpy_id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_cpy_id);