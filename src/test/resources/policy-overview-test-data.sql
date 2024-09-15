SET FOREIGN_KEY_CHECKS = 0;

delete from policy_document;
delete from policy_category;

insert into policy_category(id, name, display_order, created_by, created_on, updated_by, updated_on) values
(1, 'General', 1, 1, utc_timestamp(), 1, utc_timestamp()),
(2, 'Providend Fund', 2, 1, utc_timestamp(), 1, utc_timestamp());

insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on,updated_by, updated_on) values
(1, 'Join Policy', 1 , 'https://docs.google.com/document/d/1hIJ9ZdXktdLtSEQx3i66v3oyO4a9bid3My5ihopKEXg/preview', 1, 1, utc_timestamp(), 1, utc_timestamp()),
(2, 'Leave Policy',1,  'https://docs.google.com/document/d/1bsVP1l2cQJliOLXx2AdWhEVPXC2VB0AVCx1PXy-d070/preview', 2, 1, utc_timestamp(), 1, utc_timestamp());

insert into policy_document(id, title,policy_category_id, path, display_order, created_by, created_on, updated_by, updated_on) values
(3, 'PF Policy', 2 , 'https://docs.google.com/document/d/16Wn89kFN9PDaLaJLrQysr2bR-ZVRyMhJ6MTeEm-7dpM/preview', 1, 1, utc_timestamp(), 1, utc_timestamp());

SET FOREIGN_KEY_CHECKS = 1;
