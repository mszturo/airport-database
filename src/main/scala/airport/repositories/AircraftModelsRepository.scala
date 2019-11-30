package airport.repositories

import airport.model.{AircraftModel, AircraftModelId}
import scala.concurrent.Future

trait AircraftModelsRepository {
  def getById(id: AircraftModelId): Future[Option[AircraftModel]]

  def getAll: Future[Seq[AircraftModel]]

  def create(name: String, year: Short, capacity: Int): Future[AircraftModel]

  def upsert(aircraftModel: AircraftModel): Future[Int]

  def delete(id: AircraftModelId): Future[Int]
}
