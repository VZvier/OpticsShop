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
    Id          CHARACTER(20) PRIMARY KEY,
    EMail       CHARACTER(20) UNIQUE,
    Phone       CHARACTER(20) UNIQUE
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