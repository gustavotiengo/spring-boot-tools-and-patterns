CREATE EXTENSION "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user" (
	id serial4 NOT NULL,
	external_id uuid NOT NULL DEFAULT uuid_generate_v4(),
	"name" text NOT NULL,
	phone text NOT NULL,
	email text NOT NULL,
	address text NULL,
	region text NULL,
	city text NULL,
	state text NULL,
	created_at timestamp NOT NULL,
	last_update timestamp NULL,
	CONSTRAINT users_external_id_key UNIQUE (external_id),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

INSERT INTO public."user"
("name", phone, email, address, region, city, state, created_at, last_update)
VALUES ('Test 1', '5551234', 'test1@email.com', 'Test Street', 'Test Region', 'Test City', 'TS', current_timestamp,
current_timestamp);

INSERT INTO public."user"
("name", phone, email, address, region, city, state, created_at, last_update)
VALUES ('Test 2', '5558899', 'test2@email.com', 'Test2 Street', 'Test2 Region', 'Test2 City', 'TS', current_timestamp,
current_timestamp);