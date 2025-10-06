

INSERT INTO pet_care_group (description) VALUES ('Banho');
INSERT INTO pet_care_group (description) VALUES ('Tosa');
INSERT INTO pet_care_group (description) VALUES ('Outros serviços');

INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Pequeno Porte', 1, 45.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Médio Porte', 1, 55.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Grande Porte', 1, 65.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Adicional - sujeira', 1, 15.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa higiênica', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa bebê', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Adicional - pelo embaraçado', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Corte de unha', 3, 15.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Escovação de dentes', 3, 15.00, 'ACTIVE');

INSERT INTO tutor_group (description) VALUES ('Plano Infinite');
INSERT INTO tutor_group (description) VALUES ('Permuta');

INSERT INTO role (description, label) VALUES ('ROLE_ADMIN', 'Administrador');
INSERT INTO role (description, label) VALUES ('ROLE_ATTENDANT', 'Atendente');
INSERT INTO role (description, label) VALUES ('ROLE_RECEPCIONIST', 'Recepcionista');
INSERT INTO users (name, password, status, role_id) VALUES ('Luan Carvalho de Souza', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 1);
INSERT INTO users (name, password, status, role_id) VALUES ('Franciel', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 2);
INSERT INTO users (name, password, status, role_id) VALUES ('Eduarda', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 3);

INSERT INTO payment_method (description) VALUES ('Dinheiro');
INSERT INTO payment_method (description) VALUES ('PIX');
INSERT INTO payment_method (description) VALUES ('Cartão de débito');
INSERT INTO payment_method (description) VALUES ('Cartão de crédito');
INSERT INTO payment_method (description) VALUES ('Permuta');

INSERT INTO tutor(name, phone, status) VALUES ('Luan Carvalho de Souza', '63992932615', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Dox', 1, 'ACTIVE');

INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (1, 1, CURRENT_DATE, 'COMPLETED', 'PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (1, 1, 45.00);
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (1, 5, 40.00);
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (1, 8, 15.00);

INSERT INTO payment (date, service_execution_id, tutor_name, pet_name, payment_method_id, amount, status) VALUES (CURRENT_DATE, 1, 'Luan Carvalho de Souza', 'Dox', 1, 100.00, 'FINAL');

-- 1. A recent PIX payment for a dog named Mel
INSERT INTO tutor(name, phone, status) VALUES ('Maria Silva', '11988776655', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Mel', 2, 'ACTIVE');
INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (2, 2, CURRENT_DATE - 1, 'COMPLETED', 'PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (2, 2, 45.00); -- Banho
INSERT INTO payment (date, service_execution_id, tutor_name, pet_name, payment_method_id, amount, status) VALUES (CURRENT_DATE - 1, 2, 'Maria Silva', 'Mel', 2, 45.00, 'FINAL');

-- 2. A larger credit card payment from last week for two pets from the same tutor
INSERT INTO tutor(name, phone, status) VALUES ('João Santos', '21999887766', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Thor', 3, 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Luna', 3, 'ACTIVE');
INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (3, 3, CURRENT_DATE - 7, 'COMPLETED', 'PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (3, 1, 60.00); -- Tosa Higiênica
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (3, 3, 25.00); -- Hidratação
INSERT INTO payment (date, service_execution_id, tutor_name, pet_name, payment_method_id, amount, status) VALUES (CURRENT_DATE - 7, 3, 'João Santos', 'Thor', 4, 85.00, 'FINAL');

INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (3, 4, CURRENT_DATE - 7, 'COMPLETED', 'PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (4, 2, 50.00); -- Banho (preço diferente para porte maior)
INSERT INTO payment (date, service_execution_id, tutor_name, pet_name, payment_method_id, amount, status) VALUES (CURRENT_DATE - 7, 4, 'João Santos', 'Luna', 4, 50.00, 'FINAL');

-- 3. A Debit Card payment from a new customer
INSERT INTO tutor(name, phone, status) VALUES ('Ana Claudia', '47991234567', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Bob', 4, 'ACTIVE');
INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (4, 5, '2024-05-20', 'COMPLETED', 'PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (5, 1, 60.00);
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (5, 5, 40.00); -- Corte de Unhas
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (5, 8, 15.00); -- Limpeza de Ouvidos
INSERT INTO payment (date, service_execution_id, tutor_name, pet_name, payment_method_id, amount, status) VALUES ('2024-05-20', 5, 'Ana Claudia', 'Bob', 3, 115.00, 'FINAL');

INSERT INTO tutor(name, phone, status) VALUES ('Carlos Eduardo', '81997654321', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Nina', 5, 'ACTIVE');
INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (5, 6, CURRENT_DATE, 'DONE', 'NOT_PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (6, 2, 45.00);

INSERT INTO tutor(name, phone, status) VALUES ('Fernanda Lima', '61993458721', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Rex', 6, 'ACTIVE');
INSERT INTO service_execution (tutor_id, pet_id, date, service_status, payment_status) VALUES (6, 7, '2024-04-15', 'DONE', 'NOT_PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (7, 1, 60.00);
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (7, 6, 30.00);