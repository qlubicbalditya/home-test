CREATE TABLE IF NOT EXISTS public.t_balance
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    amount numeric DEFAULT 0,
    profile_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    top_up_date timestamp without time zone,
    CONSTRAINT t_balance_pkey PRIMARY KEY (id),
    CONSTRAINT m_profile_fk FOREIGN KEY (profile_id)
        REFERENCES public.m_profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.t_balance
    OWNER to postgres;