package airport.services

import java.sql.Date

import airport.model.{Passenger, PassengerId}
import airport.repositories.PassengersRepository

import scala.concurrent.Future

class PassengersService(repo: PassengersRepository) {
  def getById(id: PassengerId): Future[Option[Passenger]] = repo.getById(id)

  def getAll: Future[Seq[Passenger]] = repo.getAll

  def create(firstName: String, middleName: Option[String] = None, lastName: String, birthday: Date): Future[Passenger] =
    repo.create(firstName, middleName, lastName, birthday)

  def upsert(passenger: Passenger): Future[Int] = repo.upsert(passenger)

  def delete(id: PassengerId): Future[Int] = repo.delete(id)
}
