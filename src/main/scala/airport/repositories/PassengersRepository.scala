package airport.repositories

import java.sql.Date

import airport.model.{Passenger, PassengerId}

import scala.concurrent.Future

trait PassengersRepository {
  def getById(id: PassengerId): Future[Option[Passenger]]


  def getAll: Future[Seq[Passenger]]

  def create(firstName: String, middleName: Option[String] = None, lastName: String, birthday: Date): Future[Passenger]

  def upsert(passenger: Passenger): Future[Int]

  def delete(id: PassengerId): Future[Int]
}

