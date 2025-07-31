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

INSERT INTO role (description) VALUES ('ROLE_ADMIN');
INSERT INTO role (description) VALUES ('ROLE_USER');

INSERT INTO users (name, email, password, status) VALUES ('Luan Carvalho', 'ita.luan12@gmail.com', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE')
INSERT INTO users (name, email, password, status) VALUES ('Luan Carvalho', 'luan@gmail.com', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'INACTIVE')

INSERT INTO user_role (role_id, user_id) VALUES (1, 1)