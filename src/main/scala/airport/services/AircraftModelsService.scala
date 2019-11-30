package airport.services

import airport.model.{AircraftModel, AircraftModelId}
import airport.repositories.AircraftModelsRepository

import scala.concurrent.Future

class AircraftModelsService(repo: AircraftModelsRepository) {
  def getById(id: AircraftModelId): Future[Option[AircraftModel]] = repo.getById(id)

  def getAll: Future[Seq[AircraftModel]] = repo.getAll

  def create(name: String, year: Short, capacity: Int): Future[AircraftModel] =
    repo.create(name, year, capacity)

  def upsert(aircraftModel: AircraftModel): Future[Int] = repo.upsert(aircraftModel)

  def delete(id: AircraftModelId): Future[Int] = repo.delete(id)
}
