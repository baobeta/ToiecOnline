ALTER TABLE listenguideline MODIFY COLUMN title varchar(255) not null;
ALTER TABLE listenguideline MODIFY COLUMN image varchar(255) not null;
ALTER TABLE listenguideline MODIFY COLUMN context text not null;
ALTER TABLE listenguideline ADD UNIQUE (title);