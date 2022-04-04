SELECT da.id AS application_id, da.sending_date, da.receiving_date, da.price, da.state,
       db.id AS baggage_id, db.weight, db.volume, db.type, db.description,
       sa.id AS sender_address_id, sa.street AS sender_street, sa.house_number AS sender_house_number,
       sc.id AS sender_city_id, sc.name AS sender_city_name, sc.zipcode AS sender_zipcode,
       ra.id AS receiver_address_id, ra.street AS receiver_street, ra.house_number AS receiver_house_number,
       rc.id AS receiver_city_id, rc.name AS receiver_city_name, rc.zipcode AS receiver_zipcode,
       u.id AS user_id, u.name AS user_name, u.surname AS user_surname, u.login, u.password, u.phone,u.email, u.cash,
       ua.id AS user_address_id, ua.street AS user_street, ua.house_number AS user_house_number,
       uc.id AS user_city_id, uc.name AS user_city_name, uc.zipcode AS user_city_zipcode
FROM delivery_applications da
    LEFT JOIN delivered_baggage db ON da.baggage_id = db.id
    LEFT JOIN addresses sa ON da.sender_address_id = sa.id
    LEFT JOIN cities sc ON sa.city_id = sc.id
    LEFT JOIN addresses ra ON da.receiver_address_id = ra.id
    LEFT JOIN cities rc ON ra.city_id = rc.id
    LEFT JOIN users u ON da.user_id = u.id
    LEFT JOIN addresses ua ON u.address_id = ua.id
    LEFT JOIN cities uc ON ua.city_id = uc.id;