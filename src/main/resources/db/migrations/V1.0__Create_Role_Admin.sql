DO $$
    BEGIN IF
        NOT EXISTS
            (SELECT 1 FROM pg_roles WHERE rolname = 'Admin')
        THEN
            CREATE ROLE "Admin" WITH
                LOGIN
                SUPERUSER
                INHERIT
                CREATEDB
                CREATEROLE
                REPLICATION
                PASSWORD '1234';
    END IF;
END $$