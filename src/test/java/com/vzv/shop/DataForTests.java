package com.vzv.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.dto.PictureDto;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import com.vzv.shop.entity.order.ConstructorOrderLine;
import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.order.OrderLine;
import com.vzv.shop.entity.product.*;
import com.vzv.shop.entity.sattlemants.City;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.sattlemants.Street;
import com.vzv.shop.entity.user.*;
import com.vzv.shop.enumerated.*;
import com.vzv.shop.request.ConstructorRequest;
import com.vzv.shop.request.OrderLineRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataForTests {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<Order> getOrderList() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        List<Customer> customer = getCustomerList();
        List<OrderLine> orderLines = getOLines();
        List<ConstructorOrderLine> constructorOLs = getConstrOLines();
        return List.of(
                new Order("K323klkm43NN43", customer.get(0),
                        orderLines.subList(0, 3), constructorOLs.subList(0, 2), customer.get(0).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING),
                new Order("JUojlNKnLNuk334", customer.get(1),
                        orderLines.subList(3, 5), constructorOLs.subList(2, 4), customer.get(1).getAddress().toInlineOrEmpty(), date, date, Status.ACCEPTED),
                new Order("mUKNKi76YGJhfd3", customer.get(2),
                        orderLines.subList(5, 8), constructorOLs.subList(4, 6), customer.get(2).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING),
                new Order("lMLIlinUyauwy87", customer.get(3),
                        orderLines.subList(8, 10), constructorOLs.subList(6, 8), customer.get(3).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING),
                new Order("jOAUW323uhiilna", customer.get(4),
                        orderLines.subList(10, 13), new ArrayList<>(), customer.get(4).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING),
                new Order("makmoUuw689klk", customer.get(4),
                        orderLines.subList(13, 14), new ArrayList<>(), customer.get(4).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING),
                new Order("JOUHVjygybJY87", customer.get(0),
                        orderLines.subList(0, 6), new ArrayList<>(), customer.get(0).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING),
                new Order("khHgygauw667Fa", customer.get(1),
                        orderLines.subList(6, 14), new ArrayList<>(), customer.get(1).getAddress().toInlineOrEmpty(), date, date, Status.PROCESSING)
        );
    }

    public List<ConstructorOrderLine> getConstrOLines() {
        List<ConstructorRequest> requests = getConstructorRequestList();
        List<ProductDTO> products = getProductDTOList();
        return List.of(
                new ConstructorOrderLine("K323klkm43NN43", requests.get(0).toConstructor(products), requests.get(0).getAmount(), requests.get(0).getTotal()),
                new ConstructorOrderLine("K323klkm43NN43", requests.get(1).toConstructor(products), requests.get(1).getAmount(), requests.get(1).getTotal()),
                new ConstructorOrderLine("K323klkm43NN43", requests.get(2).toConstructor(products), requests.get(2).getAmount(), requests.get(2).getTotal()),
                new ConstructorOrderLine("JUojlNKnLNuk334", requests.get(3).toConstructor(products), requests.get(3).getAmount(), requests.get(3).getTotal()),
                new ConstructorOrderLine("JUojlNKnLNuk334", requests.get(4).toConstructor(products), requests.get(4).getAmount(), requests.get(4).getTotal()),
                new ConstructorOrderLine("mUKNKi76YGJhfd3", requests.get(5).toConstructor(products), requests.get(5).getAmount(), requests.get(5).getTotal()),
                new ConstructorOrderLine("mUKNKi76YGJhfd3", requests.get(6).toConstructor(products), requests.get(6).getAmount(), requests.get(6).getTotal()),
                new ConstructorOrderLine("lMLIlinUyauwy87", requests.get(7).toConstructor(products), requests.get(7).getAmount(), requests.get(7).getTotal()),
                new ConstructorOrderLine("lMLIlinUyauwy87", requests.get(8).toConstructor(products), requests.get(8).getAmount(), requests.get(8).getTotal())
        );
    }

    public List<OrderLine> getOLines() {
        List<ProductDTO> products = getProductDTOList();
        return List.of(
                new OrderLine("fhkekf8wy3huwfni8sjrnwkm3u79o9ijnka", products.get(0), "5",
                        products.get(0).getPrice()),
                new OrderLine("fjlkajelfjs8ydvsgijnlsfoeisv08hu32o", products.get(1), "2",
                        products.get(1).getPrice()),
                new OrderLine("kadliwhjfksvufyb8d6teuqjp0qfuyhi323", products.get(2), "3",
                        products.get(2).getPrice()),
                new OrderLine("lkpialefwo74tojwv439utn9280dap9dy72", products.get(3), "6",
                        products.get(3).getPrice()),
                new OrderLine("oahfkuanefuyw3u7rq38r16t31313rw4i7y", products.get(0), "1",
                        products.get(0).getPrice()),
                new OrderLine("aie8ofhw73btr29328352KOKIHH7j8v338n", products.get(1), "4",
                        products.get(1).getPrice()),
                new OrderLine("akfnakwhf8Iy99bBIBIG797HJhhU879976j", products.get(2), "6",
                        products.get(2).getPrice()),
                new OrderLine("JKLlkhuUUHi989KJlIlnLdKBHJGNVHy787t", products.get(3), "10",
                        products.get(3).getPrice()),
                new OrderLine("khukaUhkHKUkBYJVTYJUKknK78jBHJ66hjH", products.get(4), "7",
                        products.get(4).getPrice()),
                new OrderLine("kPILlhiNIUknG777iHHHJyg6680uHJBNKln", products.get(5), "8",
                        products.get(5).getPrice()),
                new OrderLine("kmoNKYBTYAJNkjsuaBBIUDgsid66878JHjb", products.get(1), "1",
                        products.get(1).getPrice()),
                new OrderLine("piJOonU779HBjhTJKnjnk7969gkBky8ghbm", products.get(3), "7",
                        products.get(3).getPrice()),
                new OrderLine("kojkNKuKfhTHVVjbk876677JBjb6IBK78nb", products.get(0), "6",
                        products.get(0).getPrice()),
                new OrderLine("klonNKUknBYft68G8ggV6u9bibh8bNBnjln", products.get(1), "1",
                        products.get(1).getPrice())
        );
    }

    public List<OrderLineRequest> getOLRtList() {
        List<ProductDTO> products = getProductDTOList();
        return List.of(
                new OrderLineRequest(products.get(0).getId(), 5),
                new OrderLineRequest(products.get(1).getId(), 2),
                new OrderLineRequest(products.get(2).getId(), 3),
                new OrderLineRequest(products.get(3).getId(), 6),
                new OrderLineRequest(products.get(4).getId(), 1),
                new OrderLineRequest(products.get(5).getId(), 4),
                new OrderLineRequest(products.get(6).getId(), 6),
                new OrderLineRequest(products.get(7).getId(), 10),
                new OrderLineRequest(products.get(8).getId(), 7),
                new OrderLineRequest(products.get(9).getId(), 8),
                new OrderLineRequest(products.get(10).getId(), 1),
                new OrderLineRequest(products.get(11).getId(), 7),
                new OrderLineRequest(products.get(12).getId(), 6),
                new OrderLineRequest(products.get(11).getId(), 2),
                new OrderLineRequest(products.get(0).getId(), 4)
        );
    }

    public List<ConstructorRequest> getConstructorRequestList() {
        List<ProductDTO> products = getProductDTOList();
        return List.of(
                new ConstructorRequest(products.get(0).getId(), products.get(5).getId(), null, products.get(5).getId(),
                        null, "64", "250,00", "4",
                        calculate("4", products.get(0).getPrice(), products.get(5).getPrice(), products.get(5).getPrice())),

                new ConstructorRequest(products.get(1).getId(), products.get(6).getId(), "45", products.get(6).getId(),
                        "90", "58", "250,00", "1",
                        calculate("1", products.get(1).getPrice(), products.get(6).getPrice(), products.get(6).getPrice())),

                new ConstructorRequest(products.get(2).getId(), products.get(7).getId(), null, products.get(7).getId(),
                        null, "62", "250,00", "5",
                        calculate("5", products.get(2).getPrice(), products.get(7).getPrice(), products.get(7).getPrice())),

                new ConstructorRequest(products.get(3).getId(), products.get(8).getId(), "200", products.get(8).getId(),
                        "100", "70", "250,00", "3",
                        calculate("3", products.get(3).getPrice(), products.get(8).getPrice(), products.get(8).getPrice())),

                new ConstructorRequest(products.get(4).getId(), products.get(9).getId(), null, products.get(9).getId(),
                        null, "54", "250,00", "2",
                        calculate("2", products.get(4).getPrice(), products.get(5).getPrice(), products.get(9).getPrice())),

                new ConstructorRequest(products.get(0).getId(), products.get(5).getId(), null, products.get(7).getId(),
                        null, "60", "250,00", "4",
                        calculate("4", products.get(4).getPrice(), products.get(5).getPrice(), products.get(9).getPrice())),

                new ConstructorRequest(products.get(1).getId(), products.get(8).getId(), "180", products.get(6).getId(),
                        "210", "68", "250,00", "5",
                        calculate("5", products.get(1).getPrice(), products.get(6).getPrice(), products.get(6).getPrice())),

                new ConstructorRequest(products.get(0).getId(), products.get(8).getId(), "180", products.get(4).getId(),
                        "210", "68", "250,00", "5",
                        calculate("5", products.get(0).getPrice(), products.get(8).getPrice(), products.get(4).getPrice())),

                new ConstructorRequest(products.get(2).getId(), products.get(9).getId(), null, products.get(9).getId(),
                        null, "54", "250,00", "2",
                        calculate("2", products.get(2).getPrice(), products.get(9).getPrice(), products.get(9).getPrice()))

        );
    }

    public List<GlassesConstructor> getConstructorList() {
        List<ProductDTO> products = getProductDTOList();
        return List.of(
                new GlassesConstructor("jIJDoaOA42G", products.get(0), products.get(5), "0",
                        products.get(5), "0", "58", "250", "2", "4100"),
                new GlassesConstructor("MlklMLnBk5k", products.get(1), products.get(6), "310",
                        products.get(6), "130", "60", "250", "3", "3500"),
                new GlassesConstructor("JALjlawj3do", products.get(2), products.get(5), "0",
                        products.get(5), "0", "64", "250", "1", "1920")
        );
    }

    public List<ProductDTO> getProductDTOList() {
        return List.of(
                new ProductDTO("TestDtoFrame1", "Mien", "ready-glasses",
                        new ArrayList<>(), "Mi-132-12", FrameType.PLASTIC.getLabel(), FrameSize.ADULT.getLabel(),
                        Gender.WOMAN.getLabel(), LensType.COMPUTER.getLabel(), null, null, "+1,25",
                        "0", "62", null, null, "850,00", true),
                new ProductDTO("TestDtoFrame2", "Amshar", "frame",
                        new ArrayList<>(), "Amr-4232 Coral", FrameType.METAL_SEMI_RIMLESS.getLabel(),
                        FrameSize.CHILDISH.getLabel(), Gender.WOMAN.getLabel(), null, null, null,
                        null, null, null, null, null, "780,00", true),
                new ProductDTO("TestDtoFrame3", "Morel", "frame",
                        new ArrayList<>(), "Mo-43-41-2 blue", FrameType.PLASTIC.getLabel(),
                        FrameSize.ADULT.getLabel(), Gender.WOMAN.getLabel(), null, null, null,
                        null, null, null, null, null, "900,00", true),
                new ProductDTO("TestDtoFrame4", "Mien", "frame",
                        new ArrayList<>(), "Mi-31313 Dark", FrameType.METAL.getLabel(),
                        FrameSize.ADULT.getLabel(), Gender.MAN.getLabel(), null, null, null,
                        null, null, null, null, null, "900,00", true),
                new ProductDTO("TestDtoFrame5", "CityCode", "frame",
                        new ArrayList<>(), "SC-654-22", FrameType.METAL.getLabel(),
                        FrameSize.ADULT.getLabel(), Gender.UNIVERSAL.getLabel(), null, null, null,
                        null, null, null, null, null, "2000,00", true),
                new ProductDTO("TestLensDto1", "BioMax", "Lens",
                        new ArrayList<>(), null, null, null, null,
                        LensType.STIGMATIC.getLabel(), Country.ITALY.getLabel(), "1,64", "+1,00", "0",
                        null, null, null, "450,00", true),
                new ProductDTO("TestLensDto2", "BioMax", "Lens",
                        new ArrayList<>(), null, null, null, null,
                        LensType.ASTIGMATIC.getLabel(), Country.ITALY.getLabel(), "1,64", "+1,00", "+1,00",
                        null, null, null, "450,00", true),
                new ProductDTO("TestLensDto3", "BioMax", "Lens",
                        new ArrayList<>(), null, null, null, null,
                        LensType.STIGMATIC.getLabel(), Country.ITALY.getLabel(), "1,64", "+1,00", "0",
                        null, null, null, "450,00", true),
                new ProductDTO("TestLensDto4", "BioMax", "Lens",
                        new ArrayList<>(), null, null, null, null,
                        LensType.ASTIGMATIC.getLabel(), Country.ITALY.getLabel(), "1,64", "-0,25", "-1,00",
                        null, null, null, "450,00", true),
                new ProductDTO("TestLensDto5", "BioMax", "Lens",
                        new ArrayList<>(), null, null, null, null,
                        LensType.COMPUTER.getLabel(), Country.ITALY.getLabel(), "1,64", "+1,00", "0",
                        null, null, null, "450,00", true),
                new ProductDTO("TestProdDto4", "OptyFree", "liquid",
                        new ArrayList<>(), null, null, null, null, null,
                        null, null, null, null, null, "300", null,
                        "300,00", true),
                new ProductDTO("5", "Tomotatsu", "Medical equipment",
                        new ArrayList<>(), "MSu-31-5432", null, null, null, null,
                        null, null, null, null, null, null, "description",
                        "2100,00", true),
                new ProductDTO("TestLensDto6", "BioLife", "Lens", new ArrayList<>(),
                        null, null, null, null,
                        LensType.ORANGE.getLabel(), Country.ITALY.getLabel(), "1,53", "0", "0",
                        null, null, null, "450,00", true)
        );
    }

    public List<Product> getProductList() {
        List<Product> products = new ArrayList<>();
        products.addAll(getFrameList());
        products.addAll(getLensList());
        products.addAll(getLiquidList());
        products.addAll(getMedicalEquipmentList());
        return products;
    }

    public List<Lens> getLensList() {
        return List.of(
                new Lens("TestLens1", "lens", "BioLife", new ArrayList<>(),
                        "450,00", LensType.STIGMATIC, Country.POLAND, "1,49", "+1,00", "+1,00", true),
                new Lens("TestLens2", "lens", "BioLife", new ArrayList<>(),
                        "450,00", LensType.STIGMATIC, Country.POLAND, "1,49", "+2,00", "0", true),
                new Lens("TestLens3", "lens", "BioMax", new ArrayList<>(),
                        "450,00", LensType.ASTIGMATIC, Country.KOREA, "1,49", "0", "+1,00", true),
                new Lens("TestLens4", "lens", "BioMax", new ArrayList<>(),
                        "450,00", LensType.ASTIGMATIC, Country.KOREA, "1,49", "-1,00", "-0,75", true),
                new Lens("TestLens5", "lens", "BioLife", new ArrayList<>(),
                        "450,00", LensType.PHOTOCHROM_GRAY, Country.GERMANY, "1,49", "0", "0", true),
                new Lens("TestLens6", "lens", "BioLife", new ArrayList<>(),
                        "450,00", LensType.PHOTOCHROM_BROWN, Country.GERMANY, "1,49", "0", "0", true)
        );
    }

    public List<Frame> getFrameList() {
        return List.of(
                new Frame("TestFrame1", "Mien", new ArrayList<>(), "500,00",
                        "Mx-131-12", FrameType.METAL, FrameSize.ADULT, Gender.MAN, true),
                new Frame("TestFrame2", "Tress", new ArrayList<>(), "750,00",
                        "Ts-43-122", FrameType.PLASTIC, FrameSize.CHILDISH, Gender.WOMAN, true),
                new Frame("TestFrame3", "Amshar", new ArrayList<>(), "900,00",
                        "AR-459-90", FrameType.METAL_SEMI_RIMLESS, FrameSize.ADULT, Gender.WOMAN, true),
                new Frame("TestFrame4", "Tress", new ArrayList<>(), "600,00",
                        "Tt-534-22 black", FrameType.PLASTIC_SEMI_RIMLESS, FrameSize.CHILDISH, Gender.MAN, true),
                new Frame("TestFrame5", "Amshar", new ArrayList<>(), "1300,00",
                        "As-4325 blue", FrameType.RIMLESS, FrameSize.ADULT, Gender.WOMAN, true)
        );
    }

    public List<ReadyGlasses> getReadyGlassesList() {
        return List.of(
                new ReadyGlasses("TestRGlasses1", "ready-glasses", "Mien",
                        new ArrayList<>(), "850,00", "MN-1100-32", FrameType.METAL, FrameSize.ADULT, Gender.WOMAN, LensType.COMPUTER,
                        "+3,00", "", true),
                new ReadyGlasses("TestRGlasses2", "ready-glasses", "Mien",
                        new ArrayList<>(), "500,00", "Mx-131-12", FrameType.METAL_SEMI_RIMLESS, FrameSize.ADULT,
                        Gender.WOMAN, LensType.STIGMATIC, "+1,00", "60", true),
                new ReadyGlasses("TestRGlasses3", "ready-glasses", "Tress",
                        new ArrayList<>(), "750,00", "Ts-43-122", FrameType.PLASTIC, FrameSize.CHILDISH,
                        Gender.WOMAN, LensType.COMPUTER_BB, "-1,00", "54", true),
                new ReadyGlasses("TestRGlasses4", "ready-glasses", "Amshar",
                        new ArrayList<>(), "900,00", "AR-459-90", FrameType.METAL_SEMI_RIMLESS, FrameSize.ADULT,
                        Gender.WOMAN, LensType.PHOTOCHROM_GRAY, "0", "0", true),
                new ReadyGlasses("TestRGlasses5", "ready-glasses", "Tress",
                        new ArrayList<>(), "600,00", "Tt-534-22 black", FrameType.PLASTIC_SEMI_RIMLESS,
                        FrameSize.ADULT, Gender.MAN, LensType.COMPUTER, "-2,50", "68", true),
                new ReadyGlasses("TestRGlasses6", "ready-glasses", "Amshar",
                        new ArrayList<>(), "1300,00", "As-4325 blue", FrameType.RIMLESS, FrameSize.ADULT,
                        Gender.WOMAN, LensType.STIGMATIC, "+1,75", "68", true)
        );
    }

    public List<MedicalEquipment> getMedicalEquipmentList() {
        return List.of(
                new MedicalEquipment("TestMedEquip1", "medical equipment", "Oberon",
                        new ArrayList<>(), "1250,00", "XrY-432-112",
                        "Best tonometer. Manufactured at Japan for personal use and for medicine!", true),
                new MedicalEquipment("TestMedEquip2", "medical equipment", "Asimut",
                        new ArrayList<>(), "1800,00", "Ozg-4312-21",
                        "Stethoscope. Manufactured at Korea for use at medicine and for personal usage!", true),
                new MedicalEquipment("TestMedEquip3", "medical equipment", "TenSu",
                        new ArrayList<>(), "2400,00", "OiK4-321",
                        "Stethoscope. Manufactured at Korea for use at medicine and for personal usage!", true),
                new MedicalEquipment("TestMedEquip4", "medical equipment", "SerTroph",
                        new ArrayList<>(), "1000,00", "Ki-123-2",
                        "Tonometer. Manufactured at Korea for use at medicine and for personal usage!", true),
                new MedicalEquipment("TestMedEquip5", "medical equipment", "Sorona",
                        new ArrayList<>(), "950,00", "Kio-315-1",
                        "Stethoscope. Manufactured at Korea for use at medicine and for personal usage!", true)
        );
    }

    public List<Liquid> getLiquidList() {
        return List.of(
                new Liquid("TestLiquid1", "liquid", "OptiFree",
                        new ArrayList<>(), "300,00", "300",
                        "Special liquid for contact lenses care.", true),
                new Liquid("TestLiquid2", "liquid", "OptiFree",
                        new ArrayList<>(), "200,00", "150",
                        "Special liquid for contact lenses care.", true),
                new Liquid("TestLiquid3", "liquid", "ReNu",
                        new ArrayList<>(), "350,00", "150",
                        "Special liquid for contact lenses care.", true),
                new Liquid("TestLiquid4", "liquid", "ReNu",
                        new ArrayList<>(), "200,00", "150",
                        "Special liquid for contact lenses care.", true),
                new Liquid("TestLiquid5", "liquid", "ReNu",
                        new ArrayList<>(), "100,00", "70",
                        "Special liquid for contact lenses care.", true)
        );
    }

    public List<Customer> getCustomerList() {
        List<User> users = getUserList();
        List<Address> addresses = getAddressList();
        List<Contact> contacts = getContactList();
        return List.of(
                new Customer(users.get(0).getId(), "Иван", "Иванов", "Иванович", "13.12.1982", contacts.get(0), addresses.get(0), users.get(0), null),
                new Customer(users.get(1).getId(), "Петр", "Петров", "Петрович", "13.12.1982", contacts.get(1), addresses.get(1), users.get(1), null),
                new Customer(users.get(2).getId(), "Сидр", "Сидоров", "Сидорович", "30.11.1978", contacts.get(2), addresses.get(2), users.get(2), null),
                new Customer(users.get(3).getId(), "Юрий", "Юрьев", "Юрьевич", "01.01.1990", contacts.get(3), addresses.get(3), users.get(3), null),
                new Customer(users.get(4).getId(), "Антон", "Антонов", "Антонович", "15.06.2010", contacts.get(4), addresses.get(4), users.get(4), null)
        );
    }

    public List<Address> getAddressList() {
        List<User> users = getUserList();
        return List.of(
                new Address(users.get(0).getId(), "Новая Почта", "Курьером", "Украина", "обл. Херсонская", "г. Херсон", "Новая Каховка", "ул. Тимирязева", "22", null, null),
                new Address(users.get(1).getId(), "УкрПошта", "Курьером", "Украина", "обл. Одесская", "г. Болград", "Лоза", "ул. Фрунзе", "13", null, null),
                new Address(users.get(2).getId(), "Новая Почта", "В отделение", "Украина", "обл. Львовска", "р-н. Львов", "пгт. Львят", null, null, "1", null),
                new Address(users.get(3).getId(), "Новая Почта", "В отделение", "Украина", "обл. Черниговская", "г. Прилуки", null, null, null, "4", null),
                new Address(users.get(4).getId(), "Новая Почта", "В терминал", "Украина", "обл. Житомирская", "г. Житомир", null, null, null, null, "14")
        );
    }

    public List<Contact> getContactList() {
        List<User> users = getUserList();
        List<Contact> result = new ArrayList<>();
        result.add(new Contact(users.get(0).getId(), users.get(0).getLogin() + "@gmail.com", "+38(097)440-92-85"));
        result.add(new Contact(users.get(1).getId(), users.get(1).getLogin() + "@gmail.com", "+38(063)735-83-98"));
        result.add(new Contact(users.get(2).getId(), users.get(2).getLogin() + "@gmail.com", "+38(068)456-22-43"));
        result.add(new Contact(users.get(3).getId(), users.get(3).getLogin() + "@gmail.com", "+38(099)948-96-23"));
        result.add(new Contact(users.get(4).getId(), users.get(4).getLogin() + "@gmail.com", "+38(066)435-37-64"));
        return result;
    }

    public List<User> getUserList() {
        List<User> result = new ArrayList<>();
        List<String> names = List.of("Ivanov", "Petrov", "Sidorov", "Yuriev", "Antonov");
        result.add(new User("TestUser1", names.get(0) + "00",
                encoder.encode(names.get(0) + "00"), Set.of(Role.getByLabel("ROLE_USER"))));
        result.add(new User("TestUser2", names.get(1) + "11",
                encoder.encode(names.get(1) + "11"), Set.of(Role.getByLabel("ROLE_STAFF"))));
        result.add(new User("TestUser3", names.get(2) + "22",
                encoder.encode(names.get(2) + "22"), Set.of(Role.getByLabel("ROLE_STAFF"))));
        result.add(new User("TestUser4", names.get(3) + "33",
                encoder.encode(names.get(3) + "33"), Set.of(Role.getByLabel("ROLE_USER"))));
        result.add(new User("TestUser5", names.get(4) + "44",
                encoder.encode(names.get(4) + "44"), Set.of(Role.getByLabel("ROLE_STAFF"))));
        return result;
    }

    public List<Region> getRegionList() {
        return List.of(
                new Region("od", "обл. Одесская", "обл. Одеська", "reg. Odessa"),
                new Region("lv", "обл. Львовская", "обл. Львівська", "reg. L'viv"),
                new Region("kh", "обл. Херсонская", "обл. Херсонська", "reg. Kherson")
        );
    }

    public List<City> getCityList() {
        return List.of(
                new City("bolg", "c. Bolgrad", "г. Болград", "м. Болград"),
                new City("Odes", "c. Odessa", "г. Одесса", "м. Одеса"),
                new City("kher", "c. Kherson", "г. Херсон", "м. Херсон")
        );
    }

    public List<Street> getStreetList() {
        return List.of(
                new Street("wjdalwda", "odes", "str. Lesi Ukrainki", "ул. Леси Украинки", "вул. Лесі Українки"),
                new Street("jlANW", "kher", "str. Hkmelnits'kogo", "ул. Богдана Хмельницкого", "вул. Богдана Хмельницького"),
                new Street("JiUOG", "bolg", "str. Ukrains'ka", "ул. Украинская", "вул. Укрїнська")

        );
    }

    private String calculate(String amount, String... strs) {
        int result = 0;
        for (String s : strs) {
            result += Integer.parseInt(s.substring(0, s.length() - 4));
        }
        result = result * Integer.parseInt(amount);
        return String.valueOf(result) + ",00";
    }

    public <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(json, clazz);
    }

    public <T> T mapFromJson(String json, TypeReference<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(json, type);
    }

    public boolean isMethodExists(Object obj, String name) throws IOException {
        try {
            obj.getClass().getMethod(name);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public List<FullUser> getFullUserList() {
        List<Customer> customers = getCustomerList();
        return List.of(
                new FullUser(customers.get(0).getId(), customers.get(0).getUser().getLogin(),
                        customers.get(0).getUser().getPassword(), customers.get(0).getUser().getRoles(),
                        customers.get(0)),
                new FullUser(customers.get(1).getId(), customers.get(1).getUser().getLogin(),
                        customers.get(1).getUser().getPassword(), customers.get(1).getUser().getRoles(),
                        customers.get(1)),
                new FullUser(customers.get(2).getId(), customers.get(2).getUser().getLogin(),
                        customers.get(2).getUser().getPassword(), customers.get(2).getUser().getRoles(),
                        customers.get(2)),
                new FullUser(customers.get(3).getId(), customers.get(3).getUser().getLogin(),
                        customers.get(3).getUser().getPassword(), customers.get(3).getUser().getRoles(),
                        customers.get(3)),
                new FullUser(customers.get(4).getId(), customers.get(4).getUser().getLogin(),
                        customers.get(4).getUser().getPassword(), customers.get(4).getUser().getRoles(),
                        customers.get(4))
        );
    }

    public List<Picture> getPicturesList() {
        return List.of(
                new Picture("TestImg1", "TestImage1", "img/png", "Test Image 1 Content".getBytes()),
                new Picture("TestImg2", "TestImage2", "img/jpg", "Test Image 2 Content".getBytes()),
                new Picture("TestImg3", "TestImage3", "img/jpg", "Test Image 3 Content".getBytes()),
                new Picture("TestImg4", "TestImage4", "img/jpeg", "Test Image 4 Content".getBytes())
        );
    }

    public List<PictureDto> getPicturesDtoList() {
        List<Product> products = getProductList();
        return List.of(
                new PictureDto("TestImg1", List.of(products.get(0), products.get(2)), "TestImage1",
                        "img/png", "Test Image 1 Content".getBytes()),
                new PictureDto("TestImg2", List.of(products.get(0), products.get(1)), "TestImage2",
                        "img/jpg", "Test Image 2 Content".getBytes()),
                new PictureDto("TestImg3", List.of(products.get(1), products.get(2)), "TestImage3",
                        "img/jpg", "Test Image 3 Content".getBytes()),
                new PictureDto("TestImg4", List.of(products.get(1), products.get(0)), "TestImage4",
                        "img/jpeg", "Test Image 4 Content".getBytes())
        );
    }
}
