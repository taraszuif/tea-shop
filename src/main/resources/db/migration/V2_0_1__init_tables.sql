CREATE TABLE IF NOT EXISTS public.tea
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    add_time timestamp without time zone,
    count integer NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    image_url character varying(255) COLLATE pg_catalog."default",
    manufacturer character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price numeric(19,2),
    tea_type integer,
    CONSTRAINT tea_pkey PRIMARY KEY (id)
    )
                       WITH (
                           OIDS = FALSE
                           )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tea
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.users
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    user_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )
    WITH (
        OIDS = FALSE
        )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;