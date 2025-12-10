-- Test-Daten für Tag 3: JPA Relationships & Queries
-- Dieses Script kannst du manuell ausführen oder über Spring Boot laden lassen

-- Person 1 mit mehreren Adressen und Orders
INSERT INTO persons (firstname, lastname, email) VALUES ('Max', 'Mustermann', 'max@example.com');
INSERT INTO addresses (street, city, zip_code, country, type, person_id) 
VALUES ('Hauptstraße 1', 'Berlin', '10115', 'Deutschland', 'HOME', 1);
INSERT INTO addresses (street, city, zip_code, country, type, person_id) 
VALUES ('Geschäftsweg 42', 'München', '80333', 'Deutschland', 'WORK', 1);
INSERT INTO addresses (street, city, zip_code, country, type, person_id) 
VALUES ('Lieferadresse 7', 'Hamburg', '20095', 'Deutschland', 'SHIPPING', 1);

INSERT INTO orders (order_number, price, status, order_date, person_id) 
VALUES ('ORD-2025-001', 149.99, 'DELIVERED', NOW(), 1);
INSERT INTO orders (order_number, price, status, order_date, person_id) 
VALUES ('ORD-2025-002', 79.50, 'SHIPPED', NOW(), 1);

-- Person 2 mit Adressen
INSERT INTO persons (firstname, lastname, email) VALUES ('Erika', 'Musterfrau', 'erika@example.com');
INSERT INTO addresses (street, city, zip_code, country, type, person_id) 
VALUES ('Musterweg 10', 'Berlin', '10117', 'Deutschland', 'HOME', 2);
INSERT INTO addresses (street, city, zip_code, country, type, person_id) 
VALUES ('Bürostraße 5', 'Berlin', '10178', 'Deutschland', 'WORK', 2);

INSERT INTO orders (order_number, price, status, order_date, person_id) 
VALUES ('ORD-2025-003', 299.00, 'PENDING', NOW(), 2);

-- Person 3 mit nur einer Adresse
INSERT INTO persons (firstname, lastname, email) VALUES ('Hans', 'Schmidt', 'hans@example.com');
INSERT INTO addresses (street, city, zip_code, country, type, person_id) 
VALUES ('Beispielstraße 99', 'München', '80331', 'Deutschland', 'HOME', 3);

INSERT INTO orders (order_number, price, status, order_date, person_id) 
VALUES ('ORD-2025-004', 59.99, 'CONFIRMED', NOW(), 3);
INSERT INTO orders (order_number, price, status, order_date, person_id) 
VALUES ('ORD-2025-005', 129.99, 'DELIVERED', NOW(), 3);
