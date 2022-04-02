DELETE FROM dimensions_fares;

INSERT INTO dimensions_fares VALUES
(1, 0, 4999, 10),
(2, 5000, 19999, 20),
(3, 20000, 99999, 35),
(4, 100000, 999999, 60),
(5, 1000000, 1000000, 80);

ALTER SEQUENCE dimensions_fares_id_seq RESTART WITH 6;