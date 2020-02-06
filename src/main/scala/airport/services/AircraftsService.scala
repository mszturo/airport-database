package airport.services

import airport.model.database.AircraftRow
import airport.model.{AircraftId, AircraftModelId}
import airport.repositories.AircraftsRepository

import scala.concurrent.Future

class AircraftsService(repo: AircraftsRepository) {
  def getById(id: AircraftId): Future[Option[AircraftRow]] = repo.getById(id)

  def getAll: Future[Seq[AircraftRow]] = repo.getAll

  def create(operating: Boolean, model: AircraftModel): Future[AircraftRow] =
    repo.create(operating, model)

  def upsert(aircraft: AircraftRow): Future[Int] = repo.upsert(aircraft)

  def delete(id: AircraftId): Future[Int] = repo.delete(id)
}
