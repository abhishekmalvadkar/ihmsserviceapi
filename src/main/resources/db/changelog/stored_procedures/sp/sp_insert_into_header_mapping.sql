-- liquibase formatted sql

--changeset Abhishek Malvadkar:3-Insert-Header-Mapping

drop procedure if exists sp_insert_header_mapping;

CREATE PROCEDURE sp_insert_header_mapping(
    IN p_menu_id BIGINT,
    IN p_header_config_id BIGINT,
    IN p_edit_role_ids VARCHAR(255),
    IN p_read_only_role_ids VARCHAR(255),
    IN p_display_order DECIMAL(5,2),
    IN p_is_default BIT
)
BEGIN
    -- Insert into header_mapping
    INSERT INTO header_mapping (
        header_config_id,
        role_menu_id,
        editable,
        display_order,
        is_default,
        delete_flag,
        created_on,
        created_by,
        updated_on,
        updated_by
    )
    SELECT
        p_header_config_id,
        rm.id AS role_menu_id,
        IF(FIND_IN_SET(rm.role_id, p_edit_role_ids) > 0, 1, 0) AS editable,
        p_display_order,
        p_is_default,
        0,
        NOW(),
        1, -- assuming created_by is 1
        NOW(),
        1  -- assuming updated_by is 1
    FROM
        role_menu rm
    WHERE
        rm.menu_id = p_menu_id
    AND
        (
            FIND_IN_SET(rm.role_id, p_edit_role_ids) > 0
            OR
            FIND_IN_SET(rm.role_id, p_read_only_role_ids) > 0
        );
END;

