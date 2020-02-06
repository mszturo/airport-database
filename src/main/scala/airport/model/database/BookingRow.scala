package airport.model.database

import java.sql.Date

import airport.model.BookingStatus.BookingStatus
import airport.model.{BookingId, FlightId, PassengerId}

case class BookingRow(
  id: BookingId,
  bookedBy: PassengerId,
  status: BookingStatus,
  bookedAt: Date,
  flight: FlightId
)
