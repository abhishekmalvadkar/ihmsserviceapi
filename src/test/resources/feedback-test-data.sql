delete from feedback;
delete from users where id > 1;
insert into users(id,name, email, created_on)
values (2, "Test" ,"it@test.com", utc_timestamp());
