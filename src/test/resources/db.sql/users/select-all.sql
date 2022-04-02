SELECT u.id AS user_id, u.name AS user_name, u.surname, u.login, u.password,
       u.phone, u.email, u.cash, c.id AS city_id, c.name AS city_name, c.zipcode, a.id AS address_id, a.street,
       a.house_number
FROM users u
         LEFT JOIN addresses a ON (u.address_id = a.id)
         LEFT JOIN cities c ON (a.city_id = c.id);