CREATE TABLE IF NOT EXISTS public.t_transaction
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    created_on timestamp without time zone,
    invoice_number character varying(20) COLLATE pg_catalog."default",
    profile_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    service_id character varying(36) COLLATE pg_catalog."default",
    total_amount numeric,
    transaction_type character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT t_transaction_pkey PRIMARY KEY (id),
    CONSTRAINT m_profile_fk FOREIGN KEY (profile_id)
        REFERENCES public.m_profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT m_service_fk FOREIGN KEY (service_id)
        REFERENCES public.m_service (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT t_transaction_transaction_type_check CHECK (transaction_type::text = ANY (ARRAY['TOPUP'::character varying, 'PAYMENT'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.t_transaction
    OWNER to postgres;