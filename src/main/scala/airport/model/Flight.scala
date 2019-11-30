package airport.model

import java.sql.Date

case class Flight(
  id: FlightId,
  from: CityId,
  departureDate: Date,
  to: CityId,
  arrivalDate: Date,
  aircraft: AircraftId)
