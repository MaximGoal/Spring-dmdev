ALTER TABLE users
ADD COLUMN created_at timestamp;

ALTER TABLE users
ADD COLUMN modified_at timestamp;

ALTER TABLE users
ADD COLUMN created_by varchar(32);

ALTER TABLE users
ADD COLUMN modified_by varchar(32);