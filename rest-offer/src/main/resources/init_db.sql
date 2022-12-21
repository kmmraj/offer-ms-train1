create database offerdb;
\c offerdb;
create user offeruser with encrypted password 'offeruser';
grant all privileges on database offerdb to offeruser;

CREATE TABLE public.offer (
	id varchar(255) NOT NULL,
	cabinclass int4 NULL,
	destination varchar(255) NULL,
	departuredate timestamp NULL,
	flightid varchar(255) NULL,
	origin varchar(255) NULL,
	CONSTRAINT offer_pkey PRIMARY KEY (id)
);

INSERT INTO public.offer
(id, cabinclass, departuredate, destination, flightid, origin)
VALUES('f602f151', 0, '2023-10-24T09:42', 'MAD', '500ba', 'BCN');
commit;
INSERT INTO public.offer
(id, cabinclass, departuredate, destination, flightid, origin)
VALUES('f603f152', 0, '2023-10-24T11:42', 'MAD', '501ba', 'BCN');
commit;
INSERT INTO public.offer
(id, cabinclass, departuredate, destination, flightid, origin)
VALUES('f604f153', 0, '2023-10-24T13:42', 'MAD', '502ba', 'BCN');
commit;
