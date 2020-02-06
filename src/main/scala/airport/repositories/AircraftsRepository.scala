package airport.repositories

import airport.model.database.AircraftRow
import airport.model.{AircraftId, AircraftModelId}

import scala.concurrent.Future

trait AircraftsRepository {
  def getById(id: AircraftId): Future[Option[AircraftRow]]

  def getAll: Future[Seq[AircraftRow]]

  def create(operating: Boolean, model: AircraftModel): Future[AircraftRow]

  def upsert(aircraft: AircraftRow): Future[Int]

  def delete(id: AircraftId): Future[Int]
}
