-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-department-table
CREATE TABLE department (
    id bigint auto_increment primary key,
    name VARCHAR(100) NOT NULL UNIQUE,
    code VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500) NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by BIGINT  NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT  NULL
);


--changeset Abhishek Malvadkar:2-create-role-table
CREATE TABLE role (
    id bigint auto_increment primary key,
    name VARCHAR(100) NOT NULL UNIQUE,
    code VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500) NULL,
    dept_id BIGINT  NOT NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by BIGINT ,
    updated_on DATETIME not NULL,
    updated_by BIGINT
);


--changeset Abhishek Malvadkar:3-create-users-table
create table users
(
    id bigint auto_increment primary key,
    name varchar(100) not null,
    email varchar(255) not null,
    auth_provider varchar(100),
    last_login_time datetime,
    photo_url varchar(600),
    active bit(1) not null default 1,
    role_id bigint not null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint,
    updated_on datetime,
    updated_by bigint
);


--changeset Abhishek Malvadkar:4-create-foreign-key-constraints-for-department-table
alter table department add constraint fk_department_created_by
foreign key(created_by) references users(id);

alter table department add constraint fk_department_updated_by
foreign key(updated_by) references users(id);



--changeset Abhishek Malvadkar:5-create-foreign-key-constraints-for-role-table
alter table role add constraint fk_tbl_role_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);


alter table role add constraint fk_tbl_role_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

alter table role add constraint fk_tbl_role_col_dept_id_tbl_department_col_id
foreign key(dept_id) references department(id);



--changeset Abhishek Malvadkar:6-create-foreign-key-constraints-for-users-table
alter table users add constraint fk_tbl_users_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table users add constraint fk_tbl_users_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

alter table users add constraint fk_tbl_users_col_updated_by_tbl_role_col_id
foreign key(role_id) references role(id);

--changeset Abhishek Malvadkar:7-insert-depsrtmrnt
INSERT INTO department (id,name, code, description, created_on,updated_on)
VALUES
(1,'System', 'SYSTEM', 'Administrative Department', UTC_TIMESTAMP(), UTC_TIMESTAMP()),
(2,'Engineering', 'ENG', 'Engineering Department', UTC_TIMESTAMP(),UTC_TIMESTAMP()),
(3,'Infrastructure', 'INFRA', 'Infrastructure Department', UTC_TIMESTAMP(),UTC_TIMESTAMP()),
(4,'Account', 'ACCOUNT', 'Accounting Department', UTC_TIMESTAMP(),  UTC_TIMESTAMP()),
(5,'Admin', 'ADMIN', 'Administrative Department', UTC_TIMESTAMP(),  UTC_TIMESTAMP()),
(6,'HR', 'HR', 'Human Resources Department', UTC_TIMESTAMP(),UTC_TIMESTAMP());

--changeset Abhishek Malvadkar:8-insert-system-role
insert into role(id,name, code,description,dept_id, created_on,updated_on)
values (1,'SYSTEM','SYS','SYSTEM',1,UTC_TIMESTAMP(),UTC_TIMESTAMP());

--changeset Abhishek Malvadkar:9-insert-system-user
insert into users(id,name, email,role_id ,created_on)
values (1, 'SYSTEM' ,'test@test.com' , 1 , utc_timestamp());


--changeset Abhishek Malvadkar:8-insert-role
insert into role(id,name, code,description,dept_id, created_on,created_by,updated_on,updated_by)
values (2,'Associate HR','AHR','Associate HR',6,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (3,'Head HR','HHR','Head HR',6,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (4,'Software Engineer','SE','Software Engineer',2,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (5,'Senior Software Engineer','SSE','Senior Software Engineer',6,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (6,'Team Lead','TL','Team Lead',6,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (7,'Associate QA','AQA','Associate QA',6,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (8,'QA Team Lead','QTL','QA Team Lead',6,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (9,'Associate Account Manager','AAM','Associate Account Manager',4,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (10,'Head Account Manager','HAM','Head Account Manager',4,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (11,'Associate IT Manage','AIM','Associate IT Manage',3,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (12,'Head IT Manage','HIM','Head IT Manage',3,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
      (13,'Admin','ADMIN','Admin',5,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1);


--changeset Abhishek Malvadkar:10-create-policy-category-table
create table policy_category
(
    id bigint auto_increment primary key,
    name varchar(255) not null,
    display_order decimal(5,2) not null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:11-create-foreign-key-constraints-for-policy-category-table
alter table policy_category add constraint fk_tbl_policy_category_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table policy_category add constraint fk_tbl_policy_category_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

--changeset Abhishek Malvadkar:12-create-policy-document-table
create table policy_document
(
    id bigint auto_increment primary key,
    title varchar(500) not null,
    policy_category_id bigint not null,
    path varchar(1000) not null, -- will store google doc URL with read only mode by adding suffix /preview to URL, will give access of this URL to all domain host users
    view_count bigint not null default 0,
    display_order decimal(5,2) not null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:13-create-foreign-key-constraints-for-policy-document-table
alter table policy_document add constraint fk_policy_document_created_by_users_id
foreign key(created_by) references users(id);

alter table policy_document add constraint fk_policy_document_updated_by_users_id
foreign key(updated_by) references users(id);

alter table policy_document add constraint fk_policy_document_policy_category_id_policy_category_id
foreign key(policy_category_id) references policy_category(id);

--changeset Abhishek Malvadkar:14-insert-policy-categories
insert into policy_category(id, name, display_order, created_by, created_on) values
(1, 'General', 1, 1, utc_timestamp()),
(2, 'Providend Fund', 2, 1, utc_timestamp()),
(3, 'Exit', 3, 1, utc_timestamp());

--changeset Abhishek Malvadkar:15-insert-policy-documents
insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on) values
(1, 'Join Policy', 1 , 'https://docs.google.com/document/d/1hIJ9ZdXktdLtSEQx3i66v3oyO4a9bid3My5ihopKEXg/preview', 1, 1, utc_timestamp()),
(2, 'Leave Policy',1,  'https://docs.google.com/document/d/1bsVP1l2cQJliOLXx2AdWhEVPXC2VB0AVCx1PXy-d070/preview', 2, 1, utc_timestamp());

insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on) values
(3, 'PF Policy', 2 , 'https://docs.google.com/document/d/16Wn89kFN9PDaLaJLrQysr2bR-ZVRyMhJ6MTeEm-7dpM/preview', 1, 1, utc_timestamp());

insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on) values
(4, 'Exit Policy', 3 , 'https://docs.google.com/document/d/1zUzXl400-iADLtIRloO1DPmFq6jrpHQPOVlbXNBXH14/preview', 1, 1, utc_timestamp());