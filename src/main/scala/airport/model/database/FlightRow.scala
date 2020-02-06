package airport.model.database

import java.sql.Date

import airport.model.{AircraftId, CityId, FlightId}

case class FlightRow(
  id: FlightId,
  from: CityId,
  departureDate: Date,
  to: CityId,
  arrivalDate: Date,
  aircraft: AircraftId)
