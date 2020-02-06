package airport.repositories

import airport.model.CityId
import airport.model.database.CityRow

import scala.concurrent.Future

trait CitiesRepository {
  def getById(id: CityId): Future[Option[CityRow]]

  def getAll: Future[Seq[CityRow]]

  def create(name: String, abbreviation: String): Future[CityRow]

  def upsert(city: CityRow): Future[Int]

  def delete(id: CityId): Future[Int]
}

