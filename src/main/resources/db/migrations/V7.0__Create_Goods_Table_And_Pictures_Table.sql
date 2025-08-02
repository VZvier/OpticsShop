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
    Id         CHARACTER(20) PRIMARY KEY,
    Name       CHARACTER(40),
    Type       CHARACTER(40),
    Content    bytea
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS PICTURES
    OWNER TO "Admin";


CREATE TABLE IF NOT EXISTS GOODS_PICTURES
(
    Product_Id CHARACTER(12) REFERENCES GOODS(Id),
    Picture_Id CHARACTER(12) REFERENCES PICTURES(Id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS GOODS_PICTURES
    OWNER TO "Admin";

