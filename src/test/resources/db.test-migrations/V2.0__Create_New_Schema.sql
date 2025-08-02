CREATE SCHEMA IF NOT EXISTS bdoptica;

ALTER DATABASE optics SET search_path TO bdoptica,topology,public;

ALTER USER "Admin" SET search_path TO bdoptica,topology,public;
