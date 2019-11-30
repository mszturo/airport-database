CREATE TABLE IF NOT EXISTS airport_db.airport.aircraft_models (
  id                bigserial       primary key,
  name              varchar(128)    NOT NULL,
  production_year   smallint        NOT NULL,
  capacity          int             NOT NULL
);
CREATE INDEX aircraft_model_id ON airport.aircraft_models (id);

CREATE TABLE airport_db.airport.aircrafts (
  id                bigserial       primary key,
  operating         boolean         NOT NULL,
  model             bigint          NOT NULL        REFERENCES airport.aircraft_models(id)
);
CREATE INDEX aircraft_id ON airport.aircrafts (id);
CREATE INDEX aircraft_aircraft_model_fk ON airport.aircrafts (model);

CREATE TABLE IF NOT EXISTS airport_db.airport.cities (
  id                bigserial       primary key,
  name              varchar(128)    NOT NULL,
  abbreviation      varchar(5)      NOT NULL
);
CREATE INDEX city_id ON airport.cities (id);

CREATE TABLE IF NOT EXISTS airport_db.airport.flights (
  id                bigserial       primary key,
  operating         boolean         NOT NULL,
  from_city         bigint          NOT NULL        REFERENCES airport.cities (id),
  to_city           bigint          NOT NULL        REFERENCES airport.cities (id),
  departure_date    timestamptz     NOT NULL,
  arrival_date      timestamptz     NOT NULL,
  aircraft          bigint          NOT NULL        REFERENCES airport.aircrafts (id)
);
CREATE INDEX flight_id ON airport.flights (id);
CREATE INDEX flight_from_city_fk ON airport.flights (from_city);
CREATE INDEX flight_to_city_fk ON airport.flights (to_city);
CREATE INDEX flight_aircraft_fk ON airport.flights (aircraft);

CREATE TABLE IF NOT EXISTS airport.passengers (
  id                bigserial       primary key,
  first_name        varchar(128)    NOT NULL,
  middle_name       varchar(128)    NULL,
  last_name         varchar(128)    NOT NULL,
  birthday          timestamp       NOT NULL
);
CREATE INDEX passenger_id ON airport.passengers (id);

CREATE TYPE booking_status AS ENUM ('booked', 'canceled', 'paid');

CREATE TABLE IF NOT EXISTS airport_db.airport.bookings (
  id                bigserial       primary key,
  booked_by         bigint          NOT NULL        REFERENCES airport.passengers (id),
  status            booking_status  NOT NULL        DEFAULT 'booked',
  booked_at         timestamptz     NOT NULL,
  flight            bigint          NOT NULL        REFERENCES airport.flights (id)
);
CREATE INDEX booking_id ON airport.bookings (id);
CREATE INDEX booking_booked_by_fk ON airport.passengers (id);
CREATE INDEX booking_flight_fk ON airport.flights (id);