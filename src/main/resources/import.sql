INSERT INTO public.pet_coat_colors (description) VALUES ('Branco');
INSERT INTO public.pet_coat_colors (description) VALUES ('Preto');
INSERT INTO public.pet_coat_colors (description) VALUES ('Cinza');
INSERT INTO public.pet_coat_colors (description) VALUES ('Caramelo');
INSERT INTO public.pet_coat_colors (description) VALUES ('Marrom');
INSERT INTO public.pet_coat_colors (description) VALUES ('Bege');
INSERT INTO public.pet_coat_colors (description) VALUES ('Dourado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Creme');
INSERT INTO public.pet_coat_colors (description) VALUES ('Amarelo');
INSERT INTO public.pet_coat_colors (description) VALUES ('Tigrado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Rajado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Malhado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Mesclado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Preto e Branco');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Marrom');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Caramelo');
INSERT INTO public.pet_coat_colors (description) VALUES ('Tricolor');
INSERT INTO public.pet_coat_colors (description) VALUES ('Azul Acinzentado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Preto e Dourado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Preto e Caramelo');
INSERT INTO public.pet_coat_colors (description) VALUES ('Preto e Cinza');
INSERT INTO public.pet_coat_colors (description) VALUES ('Preto e Bege');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Preto');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Cinza');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Dourado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Bege');
INSERT INTO public.pet_coat_colors (description) VALUES ('Branco e Creme');
INSERT INTO public.pet_coat_colors (description) VALUES ('Marrom e Branco');
INSERT INTO public.pet_coat_colors (description) VALUES ('Marrom e Preto');
INSERT INTO public.pet_coat_colors (description) VALUES ('Cinza e Branco');
INSERT INTO public.pet_coat_colors (description) VALUES ('Caramelo e Branco');
INSERT INTO public.pet_coat_colors (description) VALUES ('Creme e Cinza');
INSERT INTO public.pet_coat_colors (description) VALUES ('Dourado e Preto');
INSERT INTO public.pet_coat_colors (description) VALUES ('Cinza Mesclado');
INSERT INTO public.pet_coat_colors (description) VALUES ('Bege Mesclado');

INSERT INTO public.pet_species (description) VALUES ('Canina');
INSERT INTO public.pet_species (description) VALUES ('Felina');

INSERT INTO public.pet_breeds (description, specie_id) VALUES ('SRD', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('SRD', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Labrador Retriever', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Poodle', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Bulldog Francês', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Shih Tzu', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Pastor Alemão', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Golden Retriever', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Yorkshire Terrier', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Dachshund (Salsicha)', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Pit Bull', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Chihuahua', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Beagle', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Boxer', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Rottweiler', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Border Collie', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Cocker Spaniel', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Maltês', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Lhasa Apso', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Pug', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Doberman', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Husky Siberiano', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Akita Inu', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Bull Terrier', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Weimaraner', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('São Bernardo', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Chow Chow', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Australian Cattle Dog', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Cane Corso', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Basenji', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Shiba Inu', 1);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Whippet', 1);

INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Persa', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Siamês', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Maine Coon', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Sphynx', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Ragdoll', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('British Shorthair', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Bengal', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Himalaio', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Scottish Fold', 2);
INSERT INTO public.pet_breeds (description, specie_id) VALUES ('Angorá', 2);

INSERT INTO payment_method (description, type) VALUES ('Dinheiro', 'A_VISTA');
INSERT INTO payment_method (description, type) VALUES ('PIX', 'A_VISTA');
INSERT INTO payment_method (description, type) VALUES ('Cartão de débito', 'A_VISTA');
INSERT INTO payment_method (description, type) VALUES ('Cartão de crédito', 'A_PRAZO');
INSERT INTO payment_method (description, type) VALUES ('Permuta', 'A_PRAZO');

INSERT INTO pet_care_group (description) VALUES ('Banho');
INSERT INTO pet_care_group (description) VALUES ('Tosa');
INSERT INTO pet_care_group (description) VALUES ('Outros serviços');

INSERT INTO tutor (name, phone, status) VALUES ('Luan Carvalho de Souza', '63992932615', 'ACTIVE');
INSERT INTO pet (name, gender, specie_id, breed_id, coat_color_id, tutor_id, status) VALUES ('Dox', 'Macho', 1, 5, 19, 1, 'ACTIVE');

INSERT INTO pet_care (description, group_id, price, status) VALUES ('Banho', 1, 45.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Tosa higiênica', 2, 40.00, 'ACTIVE');
INSERT INTO pet_care (description, group_id, price, status) VALUES ('Corte de unha', 3, 15.00, 'ACTIVE');

INSERT INTO service_execution (tutor_id, pet_id, service_status, date, payment_status) VALUES (1, 1, 'IN_PROGRESS', CURRENT_DATE, 'NOT_PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (1, 1, 45.00);

INSERT INTO service_execution (tutor_id, pet_id, service_status, date, payment_status) VALUES (1, 1, 'COMPLETED', CURRENT_DATE - INTERVAL '1 day', 'PAID');
INSERT INTO service_execution_item (service_execution_id, pet_care_id, unit_price) VALUES (2, 1, 45.00);

INSERT INTO role (description) VALUES ('ROLE_ADMIN');
INSERT INTO users (username, password, status) VALUES ('luan', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE');
INSERT INTO user_role (role_id, user_id) VALUES (1, 1);
