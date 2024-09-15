-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-menu-table
CREATE TABLE menu (
    id bigint auto_increment,
    name VARCHAR(100) NOT NULL unique ,
    code VARCHAR(100) NOT NULL unique ,
    route VARCHAR(1000) NULL,
    description VARCHAR(500) NULL,
    parent_id bigint  NULL,
    menu_order DECIMAL(5,2) NOT NULL,
    device VARCHAR(20) NULL DEFAULT 'web',
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL,
    PRIMARY KEY (id)
);

--changeset Abhishek Malvadkar:2-create-foreign-key-constraints-for-menu-table
alter table menu add constraint fk_menu_created_by
foreign key(created_by) references users(id);

alter table menu add constraint fk_menu_updated_by
foreign key(updated_by) references users(id);

alter table menu add constraint fk_menu_parent_id
foreign key(parent_id) references menu(id);

--changeset Abhishek Malvadkar:3-insert-menu-table
insert
	into
	menu (id,name,code,route,description,parent_id,menu_order,created_on,created_by,updated_on,updated_by
) values
  (1,'Home','Home','/home','Home',null,1,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (2,'Holiday','Holiday','/holiday','Holiday',null,2,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (3,'Feedback','Feedback','/feedback','Feedback',null,3,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (4,'Policy','Policy','/policy','Policy',null,4,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (5,'Training','Training','/training','Training',null,5,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (6,'Holiday Overview','Holiday_Overview','/Overview','Overview',2,1,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (7,'Add Holiday','AddHoliday','/addHoliday','Add Holiday ',2,2,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (8,'Import Holidays','ImportHolidays','/importHolidays','Import Holidays',2,3,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (9,'All Feedbacks','All Feedbacks','/allFeedbacks','All Feedbacks',3,1,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (10,'Give Feedback','Give Feedback','/giveFeedback','Give Feedback',3,2,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (11,'Check Feedback Status','Check Feedback Status','/checkFeedbackStatus','Check Feedback Status',3,3,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (12,'Policy Overview','Policy_Overview','/overview','Overview',4,1,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1),
  (13,'Videos','Videos','/videos','Videos',5,1,UTC_TIMESTAMP(),1,UTC_TIMESTAMP(),1);

--changeset Abhishek Malvadkar:4-create-role-menu-table
CREATE TABLE role_menu (
    id bigint primary key auto_increment,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    delete_flag BIT(1) NOT NULL DEFAULT 0,
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL
);

--changeset Abhishek Malvadkar:5-create-foreign-key-constraints-for-role-menu-table
alter table role_menu add constraint fk_role_menu_created_by
foreign key(created_by) references users(id);

alter table role_menu add constraint fk_role_menu_updated_by
foreign key(updated_by) references users(id);

alter table role_menu add constraint fk_role_menu_menu_id
foreign key(menu_id) references menu(id);

alter table role_menu add constraint fk_role_menu_role_id
foreign key(role_id) references role(id);


--changeset Abhishek Malvadkar:6-add-role-menu-for-home
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 1 , utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

--changeset Abhishek Malvadkar:7-add-role-menu-for-home
-- holiday : 2
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 2 , utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- feedback : 3
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 3, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- policy : 4
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 4, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- traning : 5
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 5, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- Holiday Overview : 6
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 6, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);


-- Add Holiday : 7
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 7, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (3,13);

-- Import Holiday : 8
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id ,8 , utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (3,13);


-- All Feedbacks : 9
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 9, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (2,3,13);

-- Give Feedback : 10
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id , 10, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- check feedback status  : 11
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id ,11, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- policy overview  : 12
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id ,12, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);

-- Videos: 13
insert into role_menu(role_id , menu_id, created_on, created_by, updated_on, updated_by)
select r.id ,13, utc_timestamp , 1, utc_timestamp , 1 from role r WHERE r.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13);















