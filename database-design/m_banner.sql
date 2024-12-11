CREATE TABLE IF NOT EXISTS public.m_banner
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    banner_image character varying(255) COLLATE pg_catalog."default",
    banner_name character varying(60) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    CONSTRAINT m_banner_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_banner
    OWNER to postgres;


-- Inject Data Banner
INSERT INTO public.m_banner(
	id,  banner_name, banner_image, description) VALUES 
	('1', 'Banner 1', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
	('2', 'Banner 2', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
	('3', 'Banner 3', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
	('4', 'Banner 4', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
	('5', 'Banner 5', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
	('6', 'Banner 6', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet');
	
	