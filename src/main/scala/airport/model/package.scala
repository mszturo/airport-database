package airport

import airport.model.BookingStatus.BookingStatus
import slick.lifted.MappedTo
import slick.jdbc.PostgresProfile.api._

package object model {
  case class AircraftId(value: Long) extends AnyVal with MappedTo[Long]
  case class AircraftModelId(value: Long) extends AnyVal with MappedTo[Long]
  case class BookingId(value: Long) extends AnyVal with MappedTo[Long]
  case class CityId(value: Long) extends AnyVal with MappedTo[Long]
  case class FlightId(value: Long) extends AnyVal with MappedTo[Long]
  case class PassengerId(value: Long) extends AnyVal with MappedTo[Long]

  object BookingStatus extends Enumeration {
    type BookingStatus = Value
    val booked: BookingStatus = Value("booked")
    val canceled: BookingStatus = Value("canceled")
    val paid: BookingStatus = Value("paid")
  }

  implicit val bookingStatusMapper = MappedColumnType.base[BookingStatus, String](
    e => e.toString,
    s => BookingStatus.withName(s)
  )
}
