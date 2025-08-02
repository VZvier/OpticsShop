INSERT INTO glasses_constructors (id, frame_id, od_lens_id, od_angle, os_lens_id, os_angle, distance, work_price,
                                  amount, total_price)
VALUES ('k2cG6jp5b4Yc', 'TestFrame1', 'TestLens1', null, 'TestLens1', null, '64', '250', '4', '700,00'),
       ('n8quImyfc0Bv', 'TestFrame2', 'TestLens2', '45', 'TestLens2', '90', '58', '250', '1', '168,00'),
       ('WzKfKfSC7mwD', 'TestFrame3', 'TestLens3', null, 'TestLens3', null, '62', '250', '1', '900,00'),
       ('xGJUEAx50H0W', 'TestFrame4', 'TestLens4', '200', 'TestLens4', '100', '70', '250', '3', '540,00'),
       ('eCinU0tDzKzH', 'TestFrame5', 'TestLens5', null, 'TestLens5', null, '54', '250', '2', '580,00'),
       ('hCJhzCWCDiOC', 'TestFrame1', 'TestLens1', null, 'TestLens3', null, '60', '250', '2', '1160,00'),
       ('pwPnVP1L44ut', 'TestFrame2', 'TestLens4', '180', 'TestLens2', '210', '68', '250', '2', '840,00'),
       ('eqbDwOiyNfc4', 'TestFrame1', 'TestLens4', '180', 'TestLens5', '210', '68', '250', '2', '1650,00');

INSERT INTO usrs (id, login, password, created, last_visit)
VALUES ('TestUser1', 'Ivanov00', '$2a$10$jOxNUIGSxaQ.NoFIfdJrFu4.OO2OOUS0PPX8lbtUq40mIXPIywzXy', '21.05.2025',
        '21.05.2025'),
       ('TestUser2', 'Petrov11', '$2a$10$oJqm7Mu3/Z8yvJK2Vwnn3eqbHZRipFFRFQjzVxWY7f80cHWpUUNKK', '11.07.2025',
        '11.07.2025'),
       ('TestUser3', 'Sidorov22', '$2a$10$H4fm.ltV3P4avF2OoZhqIeBx9fAG7.7Kpo7Tk8Hsz9aPU0qYj1pz.', '13.07.2025',
        '13.07.2025'),
       ('TestUser4', 'Yuriev33', '$2a$10$kkvN4TjkrYzWt7yx6AvU..h/SfBuFfL3kOtywt/J3BqQOzx3WTUQy', '18.06.2025',
        '18.06.2025'),
       ('TestUser5', 'Antonov44', '$2a$10$K0xUoMxRGurAISKrxTEKlOrBrH.YuyBIlrubZDEeu5wdDhEXZLAje', '01.07.2025',
        '01.07.2025');

INSERT INTO customers (id, first_name, last_name, fathers_name, born)
VALUES ('TestUser1', 'Ivan', 'Ivanov', 'Ivanovich', '20.13.1980'),
       ('TestUser2', 'Petr', 'Petrov', 'Petrovich', '20.13.1980'),
       ('TestUser3', 'Sidor', 'Sidorov', 'Sidorovich', '20.13.1980'),
       ('TestUser4', 'Yuriy', 'Yuriev', 'Yurievich', '20.13.1980'),
       ('TestUser5', 'Anton', 'Antonov', 'Antonovich', '20.13.1980');

INSERT INTO contacts (id, email, phone)
VALUES ('TestUser1', 'ivanov00@gmail.com', '+38(097)440-92-85'),
       ('TestUser2', 'petrov11@gmail.com', '+38(063)735-83-98'),
       ('TestUser3', 'sidorov22@gmail.com', '+38(068)456-22-43'),
       ('TestUser4', 'yuriev33@gmail.com', '+38(099)948-96-23'),
       ('TestUser5', 'antonov44@gmail.com', '+38(066)435-37-64');

INSERT INTO addresses (id, delivery_service, delivery_type, country, region, city, settlement, street,
                       apartment, delivery_department, parcel_terminal)
VALUES ('TestUser1', 'УкрПочта', 'Курьером', 'Украина', 'обл. Одесская', 'р-н. Болград', 'с. Лопухов', 'ул. Кирова',
        '13/б', null, null),
       ('TestUser2', 'Новая Почта', 'Курьером', 'Украина', 'обл. Одесская', 'г. Измаил', null, 'ул. Бирюзова', '22',
        null, null),
       ('TestUser3', 'Новая Почта', 'В отделение', 'Украина', 'обл. Одесская', 'г. Рени', null, null, null, '4', null),
       ('TestUser4', 'Новая Почта', 'В почтомат', 'Украина', 'обл. Одесская', 'г. Белгород-Днестровский', null, null,
        null, null, '5'),
       ('TestUser5', 'УкрПочта', 'Курьером', 'Украина', 'обл. Херсонская', 'р-н Херсонский', 'с. Новая Каховка',
        'ул. Леси Украинки', '4', null, null);

INSERT INTO roles (user_id, role)
VALUES ('TestUser1', 'USER'),
       ('TestUser2', 'STAFF'),
       ('TestUser3', 'STAFF'),
       ('TestUser4', 'USER'),
       ('TestUser5', 'STAFF');

INSERT INTO orders (id, customer_id, address, created, updated, status)
VALUES ('K323klkm43NN43', 'TestUser1',
        'Сервис: УкрПочта, Доставка: Курьером. Адресс: Украина, обл. Одесская, р-н. Болград, с. Лопухов, ул. Кирова, 13/б',
        '23.07.2025', '24.07.2025', 'PROCESSING'),
       ('JUojlNKnLNuk334', 'TestUser2',
        'Сервис: Новая Почта, Доставка: Курьером. Адресс: Украина, обл. Одесская, г. Измаил, ул. Бирюзова, 22',
        '20.07.2015', '20.07.2015', 'ACCEPTED'),
       ('mUKNKi76YGJhfd3', 'TestUser3',
        'Сервис: Новая Почта, Доставка: В отделение. Адресс: Украина, обл. Одесская, г. Рени, 4", "11.06.2025',
        '12.06.2025', '12.06.2025', 'CONFIRMED'),
       ('lMLIlinUyauwy87', 'TestUser4',
        'Сервис: Новая Почта, Доставка: В почтомат. Адресс: Украина, обл. Одесская, г. Белгород-Днестровский, 5',
        '12.06.2025', '12.06.2025', 'PROCESSING'),
       ('jOAUW323uhiilna', 'TestUser5',
        'Сервис: УкрПочта, Доставка: Курьером. Адресс: Украина, обл. Херсонская, р-н Херсонский, с. Новая Каховка, ул. Леси Украинки, 4',
        '01.07.2025', '02.07.2025', 'CONFIRMED'),
       ('makmoUuw689klk', 'TestUser5',
        'Сервис: УкрПочта, Доставка: Курьером. Адресс: Украина, обл. Херсонская, р-н Херсонский, с. Новая Каховка, ул. Леси Украинки, 4',
        '12.06.2025', '12.06.2025', 'SENT'),
       ('JOUHVjygybJY87', 'TestUser1',
        'Сервис: УкрПочта, Доставка: Курьером. Адресс: Украина, обл. Одесская, р-н. Болград, с. Лопухов, ул. Кирова, 13/б',
        '12.07.2025', '12.07.2025', 'CANCELED'),
       ('khHgygauw667Fa', 'TestUser2',
        'Сервис: Новая Почта, Доставка: Курьером. Адресс: Украина, обл. Одесская, г. Измаил, ул. Бирюзова, 22',
        '01.08.2025', '01.08.2025', 'DELIVERED');

INSERT INTO order_lines (id, order_id, product_id, quantity, price)
VALUES ('fhkekf8wy3huwfni8sjrnwkm3u79o9ijnka', 'K323klkm43NN43', 'TestFrame1', '5', '850,00'),
       ('fjlkajelfjs8ydvsgijnlsfoeisv08hu32o', 'JUojlNKnLNuk334', 'TestFrame2', '2', '780,00'),
       ('kadliwhjfksvufyb8d6teuqjp0qfuyhi323', 'mUKNKi76YGJhfd3', 'TestFrame3', '2', '900,00'),
       ('lkpialefwo74tojwv439utn9280dap9dy72', 'lMLIlinUyauwy87', 'TestFrame4', '6', '900,00'),
       ('oahfkuanefuyw3u7rq38r16t31313rw4i7y', 'jOAUW323uhiilna', 'TestFrame1', '1', '850,00'),
       ('aie8ofhw73btr29328352KOKIHH7j8v338n', 'JUojlNKnLNuk334', 'TestFrame2', '4', '780,00'),
       ('akfnakwhf8Iy99bBIBIG797HJhhU879976j', 'mUKNKi76YGJhfd3', 'TestFrame3', '6', '900,00'),
       ('JKLlkhuUUHi989KJlIlnLdKBHJGNVHy787t', 'lMLIlinUyauwy87', 'TestFrame4', '10', '900,00'),
       ('khukaUhkHKUkBYJVTYJUKknK78jBHJ66hjH', 'jOAUW323uhiilna', 'TestFrame5', '7', '2000,00'),
       ('kPILlhiNIUknG777iHHHJyg6680uHJBNKln', 'makmoUuw689klk', 'TestLens1', '8', '450,00'),
       ('kmoNKYBTYAJNkjsuaBBIUDgsid66878JHjb', 'K323klkm43NN43', 'TestFrame2', '1', '780,00'),
       ('piJOonU779HBjhTJKnjnk7969gkBky8ghbm', 'lMLIlinUyauwy87', 'TestFrame4', '7', '900,00'),
       ('kojkNKuKfhTHVVjbk876677JBjb6IBK78nb', 'K323klkm43NN43', 'TestFrame1', '6', '850,00'),
       ('klonNKUknBYft68G8ggV6u9bibh8bNBnjln', 'JUojlNKnLNuk334', 'TestFrame2', '1', '780,00');

INSERT INTO constructors_order_lines (id, order_id, constructor_id, quantity, price)
VALUES ('fhkekf8wy3huwfni8sjrnwkm3gesgeaGjnka', 'K323klkm43NN43', 'k2cG6jp5b4Yc', '4', '700,00'),
       ('fjlkajelfjs8ydvsgijnlsfoeisv86hkKawd', 'K323klkm43NN43', 'n8quImyfc0Bv', '1', '168,00'),
       ('faAef21hjfksvufyb8d6teuqjp0qfuyhiD43', 'K323klkm43NN43', 'WzKfKfSC7mwD', '5', '900,00'),
       ('gDwialefwo74tojwv439utn9280dap9d8K9e', 'JUojlNKnLNuk334', 'xGJUEAx50H0W', '3', '540,00'),
       ('aie8ofhw73btr29328352KOKIHH7j8v338nT', 'JUojlNKnLNuk334', 'eCinU0tDzKzH', '2', '580,00'),
       ('klonNKUknBYft68G8ggV6u9bibh8bNBLonMd', 'mUKNKi76YGJhfd3', 'hCJhzCWCDiOC', '4', '1160,00'),
       ('gSqLlhiNIUknG777iHHHJyg6680ujLKlan42', 'mUKNKi76YGJhfd3', 'pwPnVP1L44ut', '5', '840,00'),
       ('LlaWakwhf8Iy99bBIBIG797HJhhU8JaF23Fw', 'lMLIlinUyauwy87', 'eqbDwOiyNfc4', '5', '1650,00');

INSERT INTO goods (id, brand, nomination, model, frame_type, size, gender, lens_type, country, coefficient, sphere,
                   cylinder, distance, volume, description, price, available, dtype)
VALUES ('TestFrame1', 'Mien', 'ready-glasses', 'Mi-132-12', 'plastic', 'adult', 'woman', 'computer', null, null,
        '+1,25', '0', '62', null, null, '850,00', true, 'ReadyGlasses'),
       ('TestFrame2', 'Amshar', 'frame', 'Amr-4232 Coral', 'metal semi-rimless', 'childish', 'woman', null, null, null,
        null, null, null, null, null, '780,00', true, 'Frame'),
       ('TestFrame3', 'Morel', 'frame', 'Mo-43-41-2 blue', 'plastic', 'adult', 'woman', null, null, null, null, null,
        null, null, null, '900,00', true, 'Frame'),
       ('TestFrame4', 'Mien', 'frame', 'Mi-31313 Dark', 'metal', 'adult', 'man', null, null, null, null, null, null,
        null, null, '900,00', true, 'Frame'),
       ('TestFrame5', 'CityCode', 'frame', 'SC-654-22', 'metal', 'adult', 'universal', null, null, null, null, null,
        null, null, null, '2000,00', true, 'Frame'),
       ('TestLens1', 'BioMax', 'lens', null, null, null, null, 'stigmatic', 'italy', '1,64', '+1,00', '0', null, null,
        null, '450,00', true, 'Lens'),
       ('TestLens2', 'BioMax', 'lens', null, null, null, null, 'astigmatic', 'italy', '1,64', '+1,00', '+1,00', null,
        null, null, '450,00', true, 'Lens'),
       ('TestLens3', 'BioMax', 'lens', null, null, null, null, 'stigmatic', 'italy', '1,64', '+1,00', '0', null, null,
        null, '450,00', true, 'Lens'),
       ('TestLens4', 'BioMax', 'lens', null, null, null, null, 'astigmatic', 'italy', '1,64', '-0,25', '-1,00', null,
        null, null, '450,00', true, 'Lens'),
       ('TestLens5', 'BioMax', 'lens', null, null, null, null, 'computer', 'italy', '1,64', '+1,00', '0', null, null,
        null, '450,00', true, 'Lens'),
       ('TestProd1', 'OptyFree', 'liquid', null, null, null, null, null, null, null, null, null, null, '300', null,
        '300,00', true, 'Liquid'),
       ('TestMedEq5', 'Tomotatsu', 'medical equipment', 'MSu-31-5432', null, null, null, null, null, null, null, null,
        null, null, 'description', '2100,00', true, 'MedicalEquipment'),
       ('TestLens6', 'BioLife', 'lens', null, null, null, null, 'orange', 'italy', '1,53', '0', '0', null, null, null,
        '450,00', true, 'Lens');

INSERT INTO regions (id, name_en, name_ru, name_ua)
VALUES ('od', 'reg. Odessa', 'обл. Одесская', 'обл. Одеська'),
       ('kh', 'reg. Kherson', 'Обл. Херсонская', 'обл. Херсонська'),
       ('nk', 'reg. Nikolaev', 'обл. Николаевская', 'обл. Миколаївська'),
       ('lv', 'reg. L`vov', 'обл. Львовская', 'обл. Львівська'),
       ('kv', 'reg. Kiev', 'обл. Киевская', 'обл. Київська'),
       ('cv', 'reg. Chernigov', 'обл. Черниговская', 'обл. Чернігівська'),
       ('hv', 'reg. Khar`kov', 'обл. Харьковская', 'обл. Харківська');

INSERT INTO cities (id, name_en, name_ru, name_ua)
VALUES ('ods', 'od', 'Одесса', 'Одеса'),
       ('khn', 'kh', 'Херсон', 'Херсон'),
       ('nkv', 'nk', 'Николаев', 'Миколаїв'),
       ('lvv', 'lv', 'Львов', 'Львовская'),
       ('kie', 'kv', 'Киев', 'Київ'),
       ('chv', 'cv', 'Чернигов', 'Чернігів'),
       ('khv', 'hv', 'Харьков', 'Харків');

INSERT INTO streets (id, city_id, name_en, name_ru, name_ua)
VALUES ('hKAKUwdnanJA', 'ods', 'str. Lesi Ukrainki', 'ул. Леси Украинки', 'вул. лесі Українки'),
       ('KJKhadkwuKdk', 'khn', 'al. Khmelnitskoho', 'пер. Хмельницького', 'пров. Хмельницького'),
       ('AFAAWfAwr3Fa', 'lvv', 'av. Gagarina', 'пр-т. Гагарина', 'пр-т Гагаріна'),
       ('HJKuAI77y9Hd', 'nkv', 'str. Ukrains`ka', 'ул. Украинская', 'вул. Українска'),
       ('KlUKbHVVCA89', 'chv', 'al. Zaporojz`kiy', 'пер. Запорожский', 'пров. Запоріжська'),
       ('AwdwaAw33F2r', 'khv', 'str. Soborna', 'ул. Соборная', 'вул. Соборна'),
       ('RawWda323fsF', 'kie', 'al. Lesnoy', 'пер. Лесной', 'пров. Лісовий');
