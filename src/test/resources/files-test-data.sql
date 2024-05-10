SET FOREIGN_KEY_CHECKS = 0;

delete from files_metadata;

insert into files_metadata (id, category, record_id, file_name, path, created_on, created_by) values
('01HXG3VPFKKZAZ5134DYBTPDPF', 'FEEDBACK', '01HXG3VPD756QV0456BV2NW1B2', 'img_1.png', 'feedback-images/01HXG3VPD756QV0456BV2NW1B2', now(), 2),
('01HXG3VPFMFPC0WP5WK01CSH3H', 'FEEDBACK', '01HXG3VPD756QV0456BV2NW1B2', 'img_2.png', 'feedback-images/01HXG3VPD756QV0456BV2NW1B2', now(), 2);

SET FOREIGN_KEY_CHECKS = 1;


