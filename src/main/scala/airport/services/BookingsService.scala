package airport.services

import java.sql.Date

import airport.model.BookingStatus.BookingStatus
import airport.model.{Booking, BookingId, FlightId, PassengerId}
import airport.repositories.BookingsRepository

import scala.concurrent.Future

class BookingsService(repo: BookingsRepository) {
  def getById(id: BookingId): Future[Option[Booking]] = repo.getById(id)

  def getAll: Future[Seq[Booking]] = repo.getAll

  def create(bookedBy: PassengerId, status: BookingStatus, bookedAt: Date, flight: FlightId): Future[Booking] =
    repo.create(bookedBy, status, bookedAt, flight)

  def upsert(booking: Booking): Future[Int] = repo.upsert(booking)

  def delete(id: BookingId): Future[Int] = repo.delete(id)
}
