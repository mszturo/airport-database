package airport.repositories

import java.sql.Date

import airport.database.AirportDatabase.db
import airport.model.BookingStatus.BookingStatus
import airport.model.database.BookingRow
import airport.model.{BookingId, FlightId, PassengerId, database}
import airport.repositories.BookingsRepositorySlick.bookings
import airport.repositories.FlightsRepositorySlick.flights
import airport.repositories.PassengersRepositorySlick.passengers
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class BookingsRepositorySlick extends BookingsRepository {
  def getById(id: BookingId): Future[Option[BookingRow]] = db.run{
    bookings.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[BookingRow]] = db.run{
    bookings.result
  }

  def create(bookedBy: PassengerId, status: BookingStatus, bookedAt: Date, flight: FlightId): Future[BookingRow] = db.run {
    (bookings.map(b => (b.bookedBy, b.status, b.bookedAt, b.flight))
      returning bookings.map(_.id)
      into ((bInfo, id) => database.BookingRow(id, bInfo._1, bInfo._2, bInfo._3, bInfo._4))
      ) += (bookedBy, status, bookedAt, flight)
  }

  def upsert(booking: BookingRow): Future[Int] = db.run{
    bookings.insertOrUpdate(booking)
  }

  def delete(id: BookingId): Future[Int] = db.run{
    bookings.filter(_.id === id).delete
  }
}

object BookingsRepositorySlick {

  private[repositories] class BookingsTable(tag: Tag) extends Table[BookingRow](tag, Some("airport"), "bookings") {
    def id = column[BookingId]("id", O.PrimaryKey, O.AutoInc)

    def bookedBy = column[PassengerId]("booked_by")

    def bookedByKey = foreignKey("booked_by_fk", bookedBy, passengers)(_.id, onDelete = ForeignKeyAction.Cascade)

    def status = column[BookingStatus]("status")

    def bookedAt = column[Date]("booked_at")

    def flight = column[FlightId]("flight")

    def flightKey = foreignKey("flight_fk", flight, flights)(_.id, onDelete = ForeignKeyAction.Cascade)

    def * = (id, bookedBy, status, bookedAt, flight) <> (BookingRow.tupled, BookingRow.unapply)
  }

  private[repositories] val bookings = TableQuery[BookingsTable]
}