INSERT INTO payment_method (description, type) VALUES ('Dinheiro', 'A_VISTA');
INSERT INTO payment_method (description, type) VALUES ('PIX', 'A_VISTA');
INSERT INTO payment_method (description, type) VALUES ('Cartão de débito', 'A_VISTA');
INSERT INTO payment_method (description, type) VALUES ('Cartão de crédito', 'A_PRAZO');
INSERT INTO payment_method (description, type) VALUES ('Permuta', 'A_PRAZO');

INSERT INTO pet_care_group (description) VALUES ('Banho');
INSERT INTO pet_care_group (description) VALUES ('Tosa');
INSERT INTO pet_care_group (description) VALUES ('Outros serviços');

INSERT INTO tutor (name, phone, status) VALUES ('Luan Carvalho de Souza', '63992932615', 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Dox', 1, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Billy', 1, 'ACTIVE');

INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Pequeno Porte', 1, 45.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Médio Porte', 1, 55.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho - Grande Porte', 1, 65.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Adicional - sujeira', 1, 15.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa higiênica', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa bebê', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Adicional - pelo embaraçado', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Corte de unha', 3, 15.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Escovação de dentes', 3, 15.00, 'ACTIVE');

INSERT INTO role (description) VALUES ('ROLE_ADMIN');
INSERT INTO users (username, password, status) VALUES ('luan', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE');
INSERT INTO user_role (role_id, user_id) VALUES (1, 1);

INSERT INTO tutor (name, phone, status) VALUES ('Ana Paula Ferreira', '11987654321', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Carlos Eduardo Gomes', '21991234567', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Beatriz Oliveira Lima', '31999887766', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('João Pedro Santos', '71988776655', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Mariana Costa Silva', '61977665544', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Fernando Almeida Rocha', '81966554433', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Patrícia Ribeiro Mendes', '51955443322', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Ricardo Lopes Martins', '41944332211', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Gabriela Souza Torres', '31933221100', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Thiago Henrique Ramos', '21922113344', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Juliana Barbosa Pinto', '11911002233', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Felipe Moura Andrade', '62988776655', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Camila Nogueira Freitas', '81999887744', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Bruno Cardoso Pires', '71988776644', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Vanessa Gomes Duarte', '51977665533', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Daniela Ferreira Costa', '61966554422', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Marcelo Rodrigues Lima', '41955443311', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Sofia Almeida Barbosa', '31944332299', 'ACTIVE');
INSERT INTO tutor (name, phone, status) VALUES ('Gustavo Pereira Souza', '21933221188', 'ACTIVE');

-- Tutor 2
INSERT INTO pet (name, tutor_id, status) VALUES ('Luna', 1, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Thor', 1, 'ACTIVE');

-- Tutor 3
INSERT INTO pet (name, tutor_id, status) VALUES ('Mel', 3, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Rex', 3, 'ACTIVE');

-- Tutor 4
INSERT INTO pet (name, tutor_id, status) VALUES ('Nina', 4, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Fred', 4, 'ACTIVE');

-- Tutor 5
INSERT INTO pet (name, tutor_id, status) VALUES ('Bela', 5, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Max', 5, 'ACTIVE');

-- Tutor 6
INSERT INTO pet (name, tutor_id, status) VALUES ('Simba', 6, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Maya', 6, 'ACTIVE');

-- Tutor 7
INSERT INTO pet (name, tutor_id, status) VALUES ('Toby', 7, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Lola', 7, 'ACTIVE');

-- Tutor 8
INSERT INTO pet (name, tutor_id, status) VALUES ('Cookie', 8, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Zeus', 8, 'ACTIVE');

-- Tutor 9
INSERT INTO pet (name, tutor_id, status) VALUES ('Pipoca', 9, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Bob', 9, 'ACTIVE');

-- Tutor 10
INSERT INTO pet (name, tutor_id, status) VALUES ('Milo', 10, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Amora', 10, 'ACTIVE');

-- Tutor 11
INSERT INTO pet (name, tutor_id, status) VALUES ('Pandora', 11, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Nick', 11, 'ACTIVE');

-- Tutor 12
INSERT INTO pet (name, tutor_id, status) VALUES ('Belinha', 12, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Spike', 12, 'ACTIVE');

-- Tutor 13
INSERT INTO pet (name, tutor_id, status) VALUES ('Nico', 13, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Maggie', 13, 'ACTIVE');

-- Tutor 14
INSERT INTO pet (name, tutor_id, status) VALUES ('Apollo', 14, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Dolly', 14, 'ACTIVE');

-- Tutor 15
INSERT INTO pet (name, tutor_id, status) VALUES ('Tom', 15, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Kira', 15, 'ACTIVE');

-- Tutor 16
INSERT INTO pet (name, tutor_id, status) VALUES ('Meg', 16, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Bruce', 16, 'ACTIVE');

-- Tutor 17
INSERT INTO pet (name, tutor_id, status) VALUES ('Sasha', 17, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Bolt', 17, 'ACTIVE');

-- Tutor 18
INSERT INTO pet (name, tutor_id, status) VALUES ('Lady', 18, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Ozzy', 18, 'ACTIVE');

-- Tutor 19
INSERT INTO pet (name, tutor_id, status) VALUES ('Zara', 19, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Chico', 19, 'ACTIVE');

-- Tutor 20
INSERT INTO pet (name, tutor_id, status) VALUES ('Fiona', 20, 'ACTIVE');
INSERT INTO pet (name, tutor_id, status) VALUES ('Marley', 20, 'ACTIVE');
