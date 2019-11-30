GRANT SELECT, INSERT, UPDATE, DELETE
    ON airport.aircraft_models, airport.aircrafts
    TO inventory_employee;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON airport.flights, airport.bookings, airport.passengers
    TO airline_employee;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON airport.bookings, airport.passengers
    TO passenger;