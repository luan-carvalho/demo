INSERT INTO pet_care_group (description) VALUES ('Banho');
INSERT INTO pet_care_group (description) VALUES ('Tosa');
INSERT INTO pet_care_group (description) VALUES ('Outros serviços');

INSERT INTO tutor_group (description) VALUES ('Plano Infinite');
INSERT INTO tutor_group (description) VALUES ('Permuta');

INSERT INTO role (description, label) VALUES ('ROLE_ADMIN', 'Administrador');
INSERT INTO role (description, label) VALUES ('ROLE_ATTENDANT', 'Atendente');
INSERT INTO role (description, label) VALUES ('ROLE_RECEPCIONIST', 'Recepcionista');

INSERT INTO users (name, password, status, role_id) VALUES ('sysadmin', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 1);

INSERT INTO payment_method (description) VALUES ('Dinheiro');
INSERT INTO payment_method (description) VALUES ('PIX');
INSERT INTO payment_method (description) VALUES ('Cartão de débito');
INSERT INTO payment_method (description) VALUES ('Cartão de crédito');
INSERT INTO payment_method (description) VALUES ('Permuta');

INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Pequeno Porte', 1, 45.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Médio Porte', 1, 55.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Grande Porte', 1, 65.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Adicional - sujeira', 1, 15.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa higiênica', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa bebê', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Adicional - pelo embaraçado', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Corte de unha', 3, 15.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Escovação de dentes', 3, 15.00, 'ACTIVE');

INSERT INTO tutor(name, phone, status) VALUES ('Luan Carvalho de Souza', '63992932615', 'ACTIVE');
INSERT INTO pet(name, tutor_id, status) VALUES ('Dox', 1, 'ACTIVE');