-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-country-table
create table country
(
    id bigint auto_increment primary key,
    name varchar(50) not null,
    iso_code varchar(10) not null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:2-create-foreign-key-constraints-for-country-table
alter table country add constraint fk_tbl_country_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table country add constraint fk_tbl_country_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

--changeset Abhishek Malvadkar:3-create-holiday-overview-table
create table holiday_overview
(
    id bigint auto_increment primary key,
    title varchar(100) not null,
    holiday_date date not null,
    is_optional varchar(10) not null,
    country_id bigint not null,
    delete_flag bit(1) not null default 0,
    created_on datetime not null,
    created_by bigint not null,
    updated_on datetime,
    updated_by bigint
);

--changeset Abhishek Malvadkar:4-create-foreign-key-constraints-for-holiday-overview-table
alter table holiday_overview add constraint fk_tbl_holiday_overview_col_created_by_tbl_users_col_id
foreign key(created_by) references users(id);

alter table holiday_overview add constraint fk_tbl_holiday_overview_col_updated_by_tbl_users_col_id
foreign key(updated_by) references users(id);

alter table holiday_overview add constraint fk_tbl_holiday_overview_col_updated_by_tbl_country_col_id
foreign key(country_id) references country(id);

--changeset Abhishek Malvadkar:5-insert-countries
insert into country(id, name, iso_code, created_by, created_on) values
(1, 'India', 'IND', 1, utc_timestamp()),
(2, 'United States of America', 'USA', 1, utc_timestamp());

--changeset Abhishek Malvadkar:6-insert-holidays-for-india
insert into holiday_overview(id, title, holiday_date, is_optional , country_id , created_by, created_on) values
(1, 'Makar Sankranti', '2024-01-14', 'No',1 , 1 , utc_timestamp()),
(2, 'Vasi Utarayan', '2024-01-15', 'No',1 , 1 , utc_timestamp()),
(3, 'Republic Day', '2024-01-26', 'No',1 , 1 , utc_timestamp()),
(4, 'Holi(Dhuleti)', '2024-02-25', 'No',1 , 1 , utc_timestamp()),
(5, 'Independence Day', '2024-08-15', 'No',1 , 1 , utc_timestamp());

--changeset Abhishek Malvadkar:7-insert-holidays-for-USA
insert into holiday_overview(id, title, holiday_date, is_optional , country_id , created_by, created_on) values
(6, "New Year's Day", '2024-01-01', 'No', 2 , 1 , utc_timestamp()),
(7, 'Memorial Day', '2024-05-27', 'No', 2 , 1 , utc_timestamp()),
(8, 'Independence Day', '2024-07-04', 'No', 2 , 1 , utc_timestamp());

