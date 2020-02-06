package airport.repositories

import java.sql.Date

import airport.model.PassengerId
import airport.model.database.PassengerRow

import scala.concurrent.Future

trait PassengersRepository {
  def getById(id: PassengerId): Future[Option[PassengerRow]]


  def getAll: Future[Seq[PassengerRow]]

  def create(firstName: String, middleName: Option[String] = None, lastName: String, birthday: Date): Future[PassengerRow]

  def upsert(passenger: PassengerRow): Future[Int]

  def delete(id: PassengerId): Future[Int]
}

