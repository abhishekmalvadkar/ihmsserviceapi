-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-Created-liquibase-poc-table
CREATE TABLE liquibase_poc(
   id varchar(255) primary key ,
   status varchar(50) not null
);