package airport.model.database

import java.sql.Date

import airport.model.PassengerId

case class PassengerRow(
  id: PassengerId,
  firstName: String,
  middleName: Option[String],
  lastName: String,
  birthday: Date)
