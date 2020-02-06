package airport.repositories

import java.sql.Date

import airport.model.BookingStatus.BookingStatus
import airport.model.database.BookingRow
import airport.model.{BookingId, FlightId, PassengerId}

import scala.concurrent.Future

trait BookingsRepository {
  def getById(id: BookingId): Future[Option[BookingRow]]

  def getAll: Future[Seq[BookingRow]]

  def create(bookedBy: PassengerId, status: BookingStatus, bookedAt: Date, flight: FlightId): Future[BookingRow]

  def upsert(booking: BookingRow): Future[Int]

  def delete(id: BookingId): Future[Int]
}

