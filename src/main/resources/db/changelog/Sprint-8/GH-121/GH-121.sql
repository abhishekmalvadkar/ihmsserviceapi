-- liquibase formatted sql

--changeset Abhishek Malvadkar:1-create-new-header-config-table
CREATE TABLE header_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    header_name VARCHAR(50) NULL,
    header_type VARCHAR(50) NOT NULL,
    mapping_name VARCHAR(50)  NULL,
    mapping_column VARCHAR(50)  NULL,
    mapping_table VARCHAR(50)  NULL,
    sort_by VARCHAR(50) DEFAULT NULL,
    sortable BIT(1) NOT NULL DEFAULT b'0',
    confirm_need BIT(1) NOT NULL DEFAULT b'0',
    blankable BIT(1) NOT NULL DEFAULT b'0',
    delete_flag BIT(1) NOT NULL DEFAULT b'0',
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL
);


--changeset Abhishek Malvadkar:2-create-new-foreign-key-constraints-for-header-config-table
alter table header_config add constraint fk_header_config_created_by
foreign key(created_by) references users(id);

alter table header_config add constraint fk_header_config_updated_by
foreign key(updated_by) references users(id);

--changeset Abhishek Malvadkar:3-insert-header-config-for-holiday-overview
INSERT INTO header_config (id, header_name, header_type, mapping_name, mapping_column, mapping_table,
sort_by, sortable, confirm_need, blankable, delete_flag,
created_on, created_by, updated_on, updated_by)
VALUES
(1, NULL, 'checkbox', NULL, NULL, NULL, NULL, 0, 0, 0, 0, NOW(), 1, NOW(), 1),
(2, NULL, 'bgColor', 'holidayStatusColor', NULL, NULL, NULL, 0, 0, 0, 0, NOW(), 1, NOW(), 1),
(3, 'Holiday Date', 'date', 'holidayDate', 'holiday_date', 'holiday_overview', 'holiday_date', 1, 0, 0, 0, NOW(), 1, NOW(), 1),
(4, 'Title', 'text', 'holidayTitle', 'title', 'holiday_overview', 'title', 1, 0, 0, 0, NOW(), 1, NOW(), 1),
(5, 'Is Optional', 'yes-no-drop-down', 'isOptional', 'is_optional', 'holiday_overview', NULL, 0, 0, 0, 0, NOW(), 1, NOW(), 1),
(6, 'Holiday Status', 'text', 'holidayStatus', NULL, NULL, NULL, 0, 0, 0, 0, NOW(), 1, NOW(), 1),
(7, 'Country', 'text', 'country', 'country_id', 'holiday_overview', 'c.name', 1, 0, 0, 0, NOW(), 1, NOW(), 1);

--changeset Abhishek Malvadkar:4-create-new-header-mapping-table
CREATE TABLE header_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    header_config_id BIGINT NOT NULL,
    role_menu_id BIGINT NOT NULL,
    editable BIT(1) NOT NULL DEFAULT b'0',
    display_order DECIMAL(5,2) NOT NULL,
    is_default BIT(1) NOT NULL DEFAULT b'0',
    delete_flag BIT(1) NOT NULL DEFAULT b'0',
    created_on DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    updated_on DATETIME NOT NULL,
    updated_by BIGINT NOT NULL
);

--changeset Abhishek Malvadkar:5-create-new-foreign-key-constraints-for-header-mapping-table
alter table header_mapping add constraint fk_header_mapping_created_by
foreign key(created_by) references users(id);

alter table header_mapping add constraint fk_header_mapping_updated_by
foreign key(updated_by) references users(id);

alter table header_mapping add constraint fk_header_mapping_header_config_id
foreign key(header_config_id) references header_config(id);

alter table header_mapping add constraint fk_header_mapping_role_menu_id
foreign key(role_menu_id) references role_menu(id);

--changeset Abhishek Malvadkar:9-insert-required_entries-in-header-mapping-for-holiday-overview

-- 1 : checkbox
call sp_insert_header_mapping(
    6, -- menu_id
    1, -- header_config_id
    NULL, -- edit_role_ids
    '1,2,3,4,5,6,7,8,9,10,11,12,13', -- read_only_role_ids
    1, -- display_order
    true -- is_default
);

-- 2 : holidayStatusColor
call sp_insert_header_mapping(
    6, -- menu_id
    2, -- header_config_id
    NULL, -- edit_role_ids
    '1,2,3,4,5,6,7,8,9,10,11,12,13', -- read_only_role_ids
    2, -- display_order
    true -- is_default
);

-- 3 : Holiday Date
call sp_insert_header_mapping(
    6, -- menu_id
    3, -- header_config_id
    '1,2,3,13', -- edit_role_ids
    '4,5,6,7,8,9,10,11,12', -- read_only_role_ids
    3, -- display_order
    true -- is_default
);

-- 4 : Title
call sp_insert_header_mapping(
    6, -- menu_id
    4, -- header_config_id
    '1,2,3,13', -- edit_role_ids
    '4,5,6,7,8,9,10,11,12', -- read_only_role_ids
    4, -- display_order
    true -- is_default
);

-- 5 : Is Optional
call sp_insert_header_mapping(
    6, -- menu_id
    5, -- header_config_id
    '1,2,3,13', -- edit_role_ids
    '4,5,6,7,8,9,10,11,12', -- read_only_role_ids
    5, -- display_order
    true -- is_default
);

-- 6 : Holiday Status
call sp_insert_header_mapping(
    6, -- menu_id
    6, -- header_config_id
    NULL, -- edit_role_ids
    '1,2,3,13,4,5,6,7,8,9,10,11,12', -- read_only_role_ids
    6, -- display_order
    true -- is_default
);

-- 7 : Country
call sp_insert_header_mapping(
    6, -- menu_id
    7, -- header_config_id
    '1,2,3,13', -- edit_role_ids
    '4,5,6,7,8,9,10,11,12', -- read_only_role_ids
    7, -- display_order
    true -- is_default
);


















