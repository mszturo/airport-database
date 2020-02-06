package airport.services

import java.sql.Date

import airport.model.BookingStatus.BookingStatus
import airport.model.database.BookingRow
import airport.model.{BookingId, FlightId, PassengerId}
import airport.repositories.BookingsRepository

import scala.concurrent.Future

class BookingsService(repo: BookingsRepository) {
  def getById(id: BookingId): Future[Option[BookingRow]] = repo.getById(id)

  def getAll: Future[Seq[BookingRow]] = repo.getAll

  def create(bookedBy: PassengerId, status: BookingStatus, bookedAt: Date, flight: FlightId): Future[BookingRow] =
    repo.create(bookedBy, status, bookedAt, flight)

  def upsert(booking: BookingRow): Future[Int] = repo.upsert(booking)

  def delete(id: BookingId): Future[Int] = repo.delete(id)
}
