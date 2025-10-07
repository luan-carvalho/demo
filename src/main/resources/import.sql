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
