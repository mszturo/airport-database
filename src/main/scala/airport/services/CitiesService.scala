package airport.services

import airport.model.{City, CityId}
import airport.repositories.CitiesRepository

import scala.concurrent.Future

class CitiesService(repo: CitiesRepository) {
  def getById(id: CityId): Future[Option[City]] = repo.getById(id)

  def getAll: Future[Seq[City]] = repo.getAll

  def create(name: String, abbreviation: String): Future[City] =
    repo.create(name, abbreviation)

  def upsert(city: City): Future[Int] = repo.upsert(city)

  def delete(id: CityId): Future[Int] = repo.delete(id)
}
