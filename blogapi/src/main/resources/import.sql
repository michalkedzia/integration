--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'tom@domain.com', 'Tom', 'Smith')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')

insert into user (account_status, email, first_name, last_name) values ('REMOVED', 'user@removed.com', 'Bob', 'Wood')
insert into blog_post (id, entry, user_id) values (1, 'post post post', 4)
insert into blog_post (id, entry, user_id) values (2, 'post post post', 4)