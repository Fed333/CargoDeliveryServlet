DELETE FROM addresses;
DELETE FROM cities;

INSERT INTO cities (id, name, zipcode) VALUES
(1, 'testCity1', '111'),
(2, 'testCity2', '222');

ALTER SEQUENCE cities_id_seq RESTART WITH 3;