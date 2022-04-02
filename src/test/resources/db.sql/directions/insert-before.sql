DELETE FROM directions;
DELETE FROM cities;

INSERT INTO public.cities VALUES (2, 'Uman', '20301');
INSERT INTO public.cities VALUES (1, 'Vinnytsia', '21012');

ALTER SEQUENCE cities_id_seq RESTART 3;