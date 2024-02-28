CREATE EXTENSION "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user" (
	id serial4 NOT NULL,
	external_id uuid NOT NULL DEFAULT uuid_generate_v4(),
	"name" text NULL,
	phone text NULL,
	email text NULL,
	address text NULL,
	region text NULL,
	city text NULL,
	state text NULL,
	created_at timestamp NULL,
	last_update timestamp NULL,
	CONSTRAINT users_external_id_key UNIQUE (external_id),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);