DELETE FROM user_role;
DELETE FROM users;
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

INSERT INTO users (id, name, surname, login, password, phone, email, cash, address_id) VALUES
(1, 'Roman', 'Kovalchuk', 'romanko_03', 'pass123', '+380986378007', 'my@mail.com', 2000.00, 1);

ALTER SEQUENCE users_id_seq RESTART WITH 2;

INSERT INTO user_role (user_id, roles) VALUES
(1, 'MANAGER'),
(1, 'USER');
