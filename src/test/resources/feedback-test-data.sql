SET FOREIGN_KEY_CHECKS = 0;

delete from feedback;
delete from files_metadata;

insert into feedback
(
    id,
    title,
    description,
    review_comment,
    reviewed_by,
    reviewed_on,
    status,
    created_on,
    created_by,
    updated_on,
    updated_by
)
values
(
    1,
    'Ticket search issue',
    '<p>I am facing ticket seach issue</p>',
    '<p>We will fix soon, do not worry</p>',
     1,
     '2024-06-08 06:53:09',
    'ACCEPTED',
    '2024-06-08 05:53:09',
    1,
    '2024-06-08 06:53:09',
    1
);


SET FOREIGN_KEY_CHECKS = 1;



