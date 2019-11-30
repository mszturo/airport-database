package airport.repositories

import java.sql.Date

import airport.database.AirportDatabase.db
import airport.model.BookingStatus.BookingStatus
import airport.model.{Booking, BookingId, FlightId, PassengerId}
import airport.repositories.BookingsRepositoryImpl.bookings
import airport.repositories.FlightsRepositoryImpl.flights
import airport.repositories.PassengersRepositoryImpl.passengers
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class BookingsRepositoryImpl extends BookingsRepository {
  def getById(id: BookingId): Future[Option[Booking]] = db.run{
    bookings.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[Booking]] = db.run{
    bookings.result
  }

  def create(bookedBy: PassengerId, status: BookingStatus, bookedAt: Date, flight: FlightId): Future[Booking] = db.run {
    (bookings.map(b => (b.bookedBy, b.status, b.bookedAt, b.flight))
      returning bookings.map(_.id)
      into ((bInfo, id) => Booking(id, bInfo._1, bInfo._2, bInfo._3, bInfo._4))
      ) += (bookedBy, status, bookedAt, flight)
  }

  def upsert(booking: Booking): Future[Int] = db.run{
    bookings.insertOrUpdate(booking)
  }

  def delete(id: BookingId): Future[Int] = db.run{
    bookings.filter(_.id === id).delete
  }
}

object BookingsRepositoryImpl {

  private[repositories] class BookingsTable(tag: Tag) extends Table[Booking](tag, Some("airport"), "bookings") {
    def id = column[BookingId]("id", O.PrimaryKey, O.AutoInc)

    def bookedBy = column[PassengerId]("booked_by")

    def bookedByKey = foreignKey("booked_by_fk", bookedBy, passengers)(_.id, onDelete = ForeignKeyAction.Cascade)

    def status = column[BookingStatus]("status")

    def bookedAt = column[Date]("booked_at")

    def flight = column[FlightId]("flight")

    def flightKey = foreignKey("flight_fk", flight, flights)(_.id, onDelete = ForeignKeyAction.Cascade)

    def * = (id, bookedBy, status, bookedAt, flight) <> (Booking.tupled, Booking.unapply)
  }

  private[repositories] val bookings = TableQuery[BookingsTable]
}