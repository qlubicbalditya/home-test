CREATE TABLE IF NOT EXISTS public.m_profile
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    email character varying(60) COLLATE pg_catalog."default",
    first_name character varying(60) COLLATE pg_catalog."default",
    last_name character varying(60) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    profile_image character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT m_profile_pkey PRIMARY KEY (id),
    CONSTRAINT m_profile_un_email UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_profile
    OWNER to postgres;
-- Index: m_profile_idx1

-- DROP INDEX IF EXISTS public.m_profile_idx1;

CREATE INDEX IF NOT EXISTS m_profile_idx1
    ON public.m_profile USING btree
    (email COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;