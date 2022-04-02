DELETE FROM distance_fares;

INSERT INTO public.distance_fares VALUES
(1, 0, 19, 30),
(2, 20, 49, 50),
(3, 50, 199, 80),
(4, 200, 999, 150),
(5, 1000, 1000, 200);

ALTER SEQUENCE dimensions_fares_id_seq RESTART WITH 6;