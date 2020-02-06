package airport.services

import airport.model.CityId
import airport.model.database.CityRow
import airport.repositories.CitiesRepository

import scala.concurrent.Future

class CitiesService(repo: CitiesRepository) {
  def getById(id: CityId): Future[Option[CityRow]] = repo.getById(id)

  def getAll: Future[Seq[CityRow]] = repo.getAll

  def create(name: String, abbreviation: String): Future[CityRow] =
    repo.create(name, abbreviation)

  def upsert(city: CityRow): Future[Int] = repo.upsert(city)

  def delete(id: CityId): Future[Int] = repo.delete(id)
}
