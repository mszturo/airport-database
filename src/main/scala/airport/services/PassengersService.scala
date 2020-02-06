package airport.services

import java.sql.Date

import airport.model.PassengerId
import airport.model.database.PassengerRow
import airport.repositories.PassengersRepository

import scala.concurrent.Future

class PassengersService(repo: PassengersRepository) {
  def getById(id: PassengerId): Future[Option[PassengerRow]] = repo.getById(id)

  def getAll: Future[Seq[PassengerRow]] = repo.getAll

  def create(firstName: String, middleName: Option[String] = None, lastName: String, birthday: Date): Future[PassengerRow] =
    repo.create(firstName, middleName, lastName, birthday)

  def upsert(passenger: PassengerRow): Future[Int] = repo.upsert(passenger)

  def delete(id: PassengerId): Future[Int] = repo.delete(id)
}
