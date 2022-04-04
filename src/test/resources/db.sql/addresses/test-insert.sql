DELETE FROM addresses;
DELETE FROM cities;

INSERT INTO cities (id, name, zipcode) VALUES
(1, 'testCity1', '111'),
(2, 'testCity2', '222');

ALTER SEQUENCE cities_id_seq RESTART WITH 3;

INSERT INTO addresses (id, city_id, street, house_number) VALUES
(1, 1, 'testStreet1', 'testHouse1'),
(2, 2, 'testStreet2', 'testHouse2');

ALTER SEQUENCE addresses_id_seq RESTART WITH 3;
