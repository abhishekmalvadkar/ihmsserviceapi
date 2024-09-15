-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-feedback-table
create table feedback
(
    id bigint auto_increment primary key,
    title varchar(150) not null,
    description varchar(5000) not null,
    status varchar(45) not null,
    review_comment varchar(1000) null,
    reviewed_by bigint null,
    reviewed_on datetime null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:2-create-foreign-key-constraints-for-feedback-table
alter table feedback add constraint fk_feedback_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table feedback add constraint fk_feedback_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

alter table feedback add constraint fk_feedback_col_reviewed_by_tbl_users_col_id
foreign key(reviewed_by) references users(id);
