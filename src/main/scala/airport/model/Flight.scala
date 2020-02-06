package airport.model

import java.sql.Date

case class Flight(
  id: FlightId,
  from: City,
  departureDate: Date,
  to: City,
  arrivalDate: Date,
  aircraft: Aircraft)
