SET FOREIGN_KEY_CHECKS = 0;

delete from users where id > 1;
insert into users(id,name, email,role_id, created_on)
values (2, "Test" ,"it@test.com","01HXXBNZSEKH1RD14TTVDCZG4K", utc_timestamp());

SET FOREIGN_KEY_CHECKS = 1;