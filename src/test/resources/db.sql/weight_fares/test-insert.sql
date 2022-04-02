DELETE FROM weight_fares;

INSERT INTO weight_fares VALUES
(1, 20, 0, 2),
(2, 30, 3, 9),
(3, 60, 10, 29),
(4, 120, 30, 99),
(5, 150, 100, 100);

ALTER SEQUENCE weight_fares_id_seq RESTART WITH 6;