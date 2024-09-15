-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-training_video_category
create table  training_video_category
(
    id varchar(50) primary key,
    name varchar(100) not null unique ,
    code varchar(100) not null unique,
    description varchar(500),
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:2-create-foreign-key-constraints-for-create-training_video_category
alter table training_video_category add constraint fk_training_video_category_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table files_metadata add constraint fk_training_video_category_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);


--changeset Abhishek Malvadkar:3-Insert_new-training_video-categories
insert into training_video_category(id, name,code,created_by, created_on) values
('01J14DAAM7EHGMQ3W0JWRW5KHY', 'java', 'JAVA', 1,utc_timestamp()),
('01J14DAAM72T5Q9P66H2YJQ9TJ', '.net','.NET',1,utc_timestamp())
