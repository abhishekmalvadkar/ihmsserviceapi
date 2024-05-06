-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-users-table
create table users
(
    id bigint auto_increment primary key,
    name varchar(100) not null,
    email varchar(255) not null,
    delete_flag bit(1) not null default 0,
    active bit(1) not null default 1,
    created_on datetime not null,
    created_by bigint,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:2-create-foreign-key-constraints-for-users-table
alter table users add constraint fk_tbl_users_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table users add constraint fk_tbl_users_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

--changeset Abhishek Malvadkar:3-insert-system-user
insert into users(id,name, email, created_on)
values (1, "SYSTEM" ,"test@test.com", utc_timestamp());

--changeset Abhishek Malvadkar:4-create-policy-category-table
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

--changeset Abhishek Malvadkar:5-create-foreign-key-constraints-for-policy-category-table
alter table policy_category add constraint fk_tbl_policy_category_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table policy_category add constraint fk_tbl_policy_category_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

--changeset Abhishek Malvadkar:6-create-policy-document-table
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

--changeset Abhishek Malvadkar:7-create-foreign-key-constraints-for-policy-document-table
alter table policy_document add constraint fk_policy_document_created_by_users_id
foreign key(created_by) references users(id);

alter table policy_document add constraint fk_policy_document_updated_by_users_id
foreign key(updated_by) references users(id);

alter table policy_document add constraint fk_policy_document_policy_category_id_policy_category_id
foreign key(policy_category_id) references policy_category(id);

--changeset Abhishek Malvadkar:8-insert-policy-categories
insert into policy_category(id, name, display_order, created_by, created_on) values
(1, 'General', 1, 1, utc_timestamp()),
(2, 'Providend Fund', 2, 1, utc_timestamp()),
(3, 'Exit', 3, 1, utc_timestamp());

--changeset Abhishek Malvadkar:9-insert-policy-documents
insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on) values
(1, 'Join Policy', 1 , 'https://docs.google.com/document/d/1hIJ9ZdXktdLtSEQx3i66v3oyO4a9bid3My5ihopKEXg/preview', 1, 1, utc_timestamp()),
(2, 'Leave Policy',1,  'https://docs.google.com/document/d/1bsVP1l2cQJliOLXx2AdWhEVPXC2VB0AVCx1PXy-d070/preview', 2, 1, utc_timestamp());

insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on) values
(3, 'PF Policy', 2 , 'https://docs.google.com/document/d/16Wn89kFN9PDaLaJLrQysr2bR-ZVRyMhJ6MTeEm-7dpM/preview', 1, 1, utc_timestamp());

insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on) values
(4, 'Exit Policy', 3 , 'https://docs.google.com/document/d/1zUzXl400-iADLtIRloO1DPmFq6jrpHQPOVlbXNBXH14/preview', 1, 1, utc_timestamp());