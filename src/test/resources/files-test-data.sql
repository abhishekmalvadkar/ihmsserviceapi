SET FOREIGN_KEY_CHECKS = 0;

delete from files_metadata;

insert into files_metadata (id, category, record_id, file_name, path, created_on, created_by, updated_on, updated_by) values
(1, 'FEEDBACK', 1, 'img_1.png', 'feedback-images/1', "2024-05-11 12:23:33", 1, "2024-05-11 12:23:33", 1),
(2, 'FEEDBACK', 1, 'img_2.png', 'feedback-images/1', "2024-05-11 12:23:33", 1, "2024-05-11 12:23:33", 1);

SET FOREIGN_KEY_CHECKS = 1;


