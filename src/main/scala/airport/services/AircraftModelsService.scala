package airport.services

import airport.model.AircraftModelId
import airport.model.database.AircraftModelRow
import airport.repositories.AircraftModelsRepository

import scala.concurrent.Future

class AircraftModelsService(repo: AircraftModelsRepository) {
  def getById(id: AircraftModel): Future[Option[AircraftModelRow]] = repo.getById(id)

  def getAll: Future[Seq[AircraftModelRow]] = repo.getAll

  def create(name: String, year: Short, capacity: Int): Future[AircraftModelRow] =
    repo.create(name, year, capacity)

  def upsert(aircraftModel: AircraftModelRow): Future[Int] = repo.upsert(aircraftModel)

  def delete(id: AircraftModel): Future[Int] = repo.delete(id)
}
