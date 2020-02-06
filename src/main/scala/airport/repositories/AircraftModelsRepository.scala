package airport.repositories

import airport.model.AircraftModelId
import airport.model.database.AircraftModelRow

import scala.concurrent.Future

trait AircraftModelsRepository {
  def getById(id: AircraftModel): Future[Option[AircraftModelRow]]

  def getAll: Future[Seq[AircraftModelRow]]

  def create(name: String, year: Short, capacity: Int): Future[AircraftModelRow]

  def upsert(aircraftModel: AircraftModelRow): Future[Int]

  def delete(id: AircraftModel): Future[Int]
}
