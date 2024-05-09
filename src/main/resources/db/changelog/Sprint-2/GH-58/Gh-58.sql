-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-files-meta-data-table
create table files_metadata
(
    id varchar(50) primary key,
    category varchar(50) not null,
    record_id varchar(50) not null,
    file_name varchar(255) not null,
    path varchar(255) not null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:2-create-foreign-key-constraints-for-files-meta-data-table
alter table files_metadata add constraint fk_files_metadata_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table files_metadata add constraint fk_files_metadata_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);
