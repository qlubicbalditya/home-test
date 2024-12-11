CREATE TABLE IF NOT EXISTS public.m_service
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    service_code character varying(60) COLLATE pg_catalog."default",
    service_icon character varying(255) COLLATE pg_catalog."default",
    service_name character varying(255) COLLATE pg_catalog."default",
    service_tariff numeric,
    CONSTRAINT m_service_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_service
    OWNER to postgres;


-- Inject data service
INSERT INTO public.m_service(
	id, service_code, service_name, service_icon, service_tariff) VALUES 
	('1', 'PAJAK', 'Pajak PBB', 'https://nutech-integrasi.app/dummy.jpg', 40000),
	('2', 'PLN', 'Listrik', 'https://nutech-integrasi.app/dummy.jpg', 10000),
	('3', 'PDAM', 'PDAM Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 40000),
	('4', 'PULSA', 'Pulsa', 'https://nutech-integrasi.app/dummy.jpg', 40000),
	('5', 'PGN', 'PGN Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
	('6', 'MUSIK', 'MUSIK Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
	('7', 'TV', 'TV Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
	('8', 'PAKET_DATA', 'Paket Data', 'https://nutech-integrasi.app/dummy.jpg', 50000),
	('9', 'VOUCHER_GAME', 'Voucher Game', 'https://nutech-integrasi.app/dummy.jpg', 100000),
	('10', 'VOUCHER_MAKANAN', 'Voucher Makanan', 'https://nutech-integrasi.app/dummy.jpg', 100000),
	('11', 'QURBAN', 'Qurban', 'https://nutech-integrasi.app/dummy.jpg', 200000),
	('12', 'ZAKAT', 'Zakat', 'https://nutech-integrasi.app/dummy.jpg', 300000);
	