SELECT d.id AS distance_id, d.distance,
       sc.id AS sender_city_id, sc.name AS sender_city_name, sc.zipcode AS sender_city_zipcode,
       rc.id AS receiver_city_id, rc.name AS receiver_city_name, rc.zipcode AS receiver_city_zipcode
FROM directions d
    LEFT JOIN cities sc ON d.sender_city_id = sc.id
    LEFT JOIN cities rc ON d.receiver_city_id = rc.id;