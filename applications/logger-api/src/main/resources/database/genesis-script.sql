create table log (
	id int primary key,
	start_date bigint,
	end_date bigint
);

CREATE SEQUENCE log_id_seq
INCREMENT 1
START 1;

ALTER TABLE log ALTER COLUMN id SET DEFAULT nextval('log_id_seq');