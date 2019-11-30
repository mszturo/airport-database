package airport.services

import airport.model.{Aircraft, AircraftId, AircraftModelId}
import airport.repositories.AircraftsRepository

import scala.concurrent.Future

class AircraftsService(repo: AircraftsRepository) {
  def getById(id: AircraftId): Future[Option[Aircraft]] = repo.getById(id)

  def getAll: Future[Seq[Aircraft]] = repo.getAll

  def create(operating: Boolean, model: AircraftModelId): Future[Aircraft] =
    repo.create(operating, model)

  def upsert(aircraft: Aircraft): Future[Int] = repo.upsert(aircraft)

  def delete(id: AircraftId): Future[Int] = repo.delete(id)
}
