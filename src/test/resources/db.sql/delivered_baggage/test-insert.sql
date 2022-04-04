DELETE FROM delivered_baggage;

INSERT INTO delivered_baggage (id, type, volume, weight, description) VALUES
(1, 0, 10000, 10, 'testBaggage');

ALTER SEQUENCE delivered_baggage_id_seq RESTART WITH 2;