CREATE SCHEMA IF NOT EXISTS airport;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'admin') THEN
      CREATE ROLE admin
          WITH CREATEDB CREATEROLE;
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'inventory_employee') THEN
      CREATE ROLE inventory_employee
          WITH LOGIN;
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'airline_employee') THEN
      CREATE ROLE airline_employee
          WITH LOGIN;
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'passenger') THEN
      CREATE ROLE passenger
          WITH LOGIN;
   END IF;
END
$do$;

GRANT ALL
    ON ALL TABLES IN SCHEMA airport
    TO admin;

GRANT ALL
    ON ALL TABLES IN SCHEMA airport
    TO postgres;
