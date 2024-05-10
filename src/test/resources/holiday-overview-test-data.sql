SET FOREIGN_KEY_CHECKS = 0;

delete from holiday_overview;
delete from country;

-- insert-countries
insert into country(id, name, iso_code, created_by, created_on) values
(1, 'India', 'IND', 1, utc_timestamp()),
(2, 'United States of America', 'USA', 1, utc_timestamp());

-- insert-holidays-for-india
insert into holiday_overview(id, title, holiday_date, is_optional , country_id , created_by, created_on) values
(1, 'Makar Sankranti', '2024-01-14', 'No',1 , 1 , utc_timestamp()),
(2, 'Vasi Utarayan', '2024-01-15', 'No',1 , 1 , utc_timestamp());

-- insert-holidays-for-USA
insert into holiday_overview(id, title, holiday_date, is_optional , country_id , created_by, created_on) values
(3, "New Year's Day", '2024-01-01', 'No', 2 , 1 , utc_timestamp()),
(4, 'Memorial Day', '2024-05-27', 'No', 2 , 1 , utc_timestamp());

SET FOREIGN_KEY_CHECKS = 1;
