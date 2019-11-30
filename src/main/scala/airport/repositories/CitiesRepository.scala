package airport.repositories

import airport.model.{City, CityId}

import scala.concurrent.Future

trait CitiesRepository {
  def getById(id: CityId): Future[Option[City]]

  def getAll: Future[Seq[City]]

  def create(name: String, abbreviation: String): Future[City]

  def upsert(city: City): Future[Int]

  def delete(id: CityId): Future[Int]
}

