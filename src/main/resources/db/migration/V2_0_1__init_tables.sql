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
    add_time timestamp without time zone NOT NULL,
    age integer NOT NULL,
    image_url character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role integer NOT NULL,
    user_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )
                       WITH (
                           OIDS = FALSE
                           )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.rating
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    add_time timestamp without time zone NOT NULL,
    comment character varying(255) COLLATE pg_catalog."default",
    rate integer NOT NULL,
    title character varying(255) COLLATE pg_catalog."default",
    tea_id character varying(255) COLLATE pg_catalog."default",
    user_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT rating_pkey PRIMARY KEY (id),
    CONSTRAINT fkf68lgbsbxl310n0jifwpfqgfh FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
                       ON UPDATE NO ACTION
                       ON DELETE NO ACTION,
    CONSTRAINT fko89k9uy4pjpxis65e0ug82n1s FOREIGN KEY (tea_id)
    REFERENCES public.tea (id) MATCH SIMPLE
                       ON UPDATE NO ACTION
                       ON DELETE NO ACTION
    )
                       WITH (
                           OIDS = FALSE
                           )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.rating
    OWNER to postgres;