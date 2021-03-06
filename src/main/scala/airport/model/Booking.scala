package airport.model

import java.sql.Date

import airport.model.BookingStatus.BookingStatus

case class Booking(
  id: BookingId,
  bookedBy: Passenger,
  status: BookingStatus,
  bookedAt: Date,
  flight: Flight
)
