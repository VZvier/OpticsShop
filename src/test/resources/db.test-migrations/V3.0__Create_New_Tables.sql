CREATE TABLE IF NOT EXISTS GLASSES_CONSTRUCTORS
(
    Id          CHARACTER(20) NOT NULL PRIMARY KEY,
    Frame_Id    CHARACTER(20) NOT NULL,
    OD_Lens_Id  CHARACTER(20) NOT NULL,
    OD_Angle    CHARACTER(3),
    OS_Lens_Id  CHARACTER(20) NOT NULL,
    OS_Angle    CHARACTER(3),
    Distance    CHARACTER(3)  NOT NULL,
    Work_Price  CHARACTER(10) NOT NULL,
    Amount      CHARACTER(3)  NOT NULL,
    Total_Price CHARACTER(10) NOT NULL
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS GLASSES_CONSTRUCTORS
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS USRS
(
    Id         CHARACTER(20) PRIMARY KEY,
    Login      CHARACTER(20) UNIQUE,
    Password   CHARACTER(90),
    Created    CHARACTER(12),
    Last_Visit CHARACTER(12)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS USRS
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS CUSTOMERS
(
    Id           CHARACTER(20) PRIMARY KEY,
    First_Name   CHARACTER(20),
    Last_Name    CHARACTER(20),
    Fathers_Name CHARACTER(30),
    Born         CHARACTER(12)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS CUSTOMERS
    OWNER to "Admin";


CREATE TABLE IF NOT EXISTS CONTACTS
(
    Id    CHARACTER(20) PRIMARY KEY,
    EMail CHARACTER(20) UNIQUE,
    Phone CHARACTER(20) UNIQUE
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS CONTACTS
    OWNER to "Admin";


CREATE TABLE IF NOT EXISTS ADDRESSES
(
    Id                  CHARACTER(20) PRIMARY KEY,
    Delivery_Service    CHARACTER(30),
    Delivery_Type       CHARACTER(30),
    Country             CHARACTER(20),
    Region              CHARACTER(30),
    City                CHARACTER(30),
    Settlement          CHARACTER(30),
    Street              CHARACTER(30),
    Apartment           CHARACTER(30),
    Delivery_Department CHARACTER(30),
    Parcel_Terminal     CHARACTER(30)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS ADDRESSES
    OWNER to "Admin";


CREATE TABLE IF NOT EXISTS ROLES
(
    User_Id CHARACTER(20) NOT NULL,
    Role    CHARACTER VARYING(20),
    CONSTRAINT fkt4bslrt98m0jid4xj2neiwk8u FOREIGN KEY (User_Id) REFERENCES USRS (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT role_check CHECK (role::text = ANY
                                 (ARRAY ['USER'::character varying, 'SYS_ADMIN'::character varying,
                                     'STAFF'::character varying]::text[]))
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS ROLES
    OWNER to "Admin";


CREATE TABLE IF NOT EXISTS ORDERS
(
    Id          CHARACTER(40) PRIMARY KEY,
    Customer_Id CHARACTER(20) REFERENCES CUSTOMERS (Id),
    Address     CHARACTER(200),
    Created     CHARACTER(18),
    Updated     CHARACTER(18),
    Status      CHARACTER(11)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS ORDERS
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS ORDER_LINES
(
    Id         CHARACTER(40) PRIMARY KEY,
    Order_Id   CHARACTER(40),
    Product_Id CHARACTER(20),
    Quantity   CHARACTER(20),
    Price      CHARACTER(20)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS ORDER_LINES
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS CONSTRUCTORS_ORDER_LINES
(

    Id             CHARACTER(40) PRIMARY KEY,
    Order_Id       CHARACTER(40),
    Constructor_Id CHARACTER(20),
    Quantity       CHARACTER(20),
    Price          CHARACTER(20)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS CONSTRUCTORS_ORDER_LINES
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS GOODS
(
    Id          CHARACTER(12) PRIMARY KEY,
    Brand       CHARACTER(30),
    Nomination  CHARACTER(30),
    Model       CHARACTER(30),
    Frame_Type  CHARACTER(30),
    Size        CHARACTER(30),
    Gender      CHARACTER(30),
    Lens_Type   CHARACTER(40),
    Country     CHARACTER(30),
    Coefficient CHARACTER(6),
    Sphere      CHARACTER(10),
    Cylinder    CHARACTER(10),
    Distance    CHARACTER(10),
    Volume      CHARACTER(10),
    Description CHARACTER(500),
    Price       CHARACTER(10),
    Available   BOOLEAN,
    dtype       TEXT
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS GOODS
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS PICTURES
(
    Id      CHARACTER(20) PRIMARY KEY,
    Name    CHARACTER(40),
    Type    CHARACTER(40),
    Content bytea
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS PICTURES
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS GOODS_PICTURES
(
    Product_Id CHARACTER(12) REFERENCES GOODS (Id),
    Picture_Id CHARACTER(12) REFERENCES PICTURES (Id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS GOODS_PICTURES
    OWNER TO "Admin";



CREATE TABLE IF NOT EXISTS REGIONS
(
    Id      CHARACTER(30),
    Name_En CHARACTER(50),
    Name_Ru CHARACTER(50),
    Name_UA CHARACTER(50)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS REGIONS
    OWNER to "Admin";


CREATE TABLE IF NOT EXISTS CITIES
(
    Id        CHARACTER(30),
    Region_Id CHARACTER(30),
    Name_En   CHARACTER(50),
    Name_Ru   CHARACTER(50),
    Name_UA   CHARACTER(50)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS CITIES
    OWNER to "Admin";


CREATE TABLE IF NOT EXISTS STREETS
(
    Id      CHARACTER(30),
    City_Id CHARACTER(30),
    Name_En CHARACTER(50),
    Name_Ru CHARACTER(50),
    Name_UA CHARACTER(50)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS STREETS
    OWNER to "Admin";
