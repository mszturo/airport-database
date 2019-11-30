package airport.repositories

import airport.model.{Aircraft, AircraftId, AircraftModelId}

import scala.concurrent.Future

trait AircraftsRepository {
  def getById(id: AircraftId): Future[Option[Aircraft]]

  def getAll: Future[Seq[Aircraft]]

  def create(operating: Boolean, model: AircraftModelId): Future[Aircraft]

  def upsert(aircraft: Aircraft): Future[Int]

  def delete(id: AircraftId): Future[Int]
}
