package airport.model

import java.sql.Date

case class Passenger(
  id: PassengerId,
  firstName: String,
  middleName: Option[String],
  lastName: String,
  birthday: Date)
