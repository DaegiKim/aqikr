# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table station (
  name                          varchar(255),
  lat                           varchar(255),
  lng                           varchar(255),
  polygon                       TEXT
);


# --- !Downs

drop table if exists station;

