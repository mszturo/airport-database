package airport.repositories

import java.sql.Date

import airport.model.BookingStatus.BookingStatus
import airport.model.{Booking, BookingId, FlightId, PassengerId}

import scala.concurrent.Future

trait BookingsRepository {
  def getById(id: BookingId): Future[Option[Booking]]

  def getAll: Future[Seq[Booking]]

  def create(bookedBy: PassengerId, status: BookingStatus, bookedAt: Date, flight: FlightId): Future[Booking]

  def upsert(booking: Booking): Future[Int]

  def delete(id: BookingId): Future[Int]
}

