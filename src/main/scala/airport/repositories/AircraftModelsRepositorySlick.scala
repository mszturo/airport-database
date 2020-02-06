package airport.repositories

import airport.database.AirportDatabase.db
import airport.model.{AircraftModelId, database}
import airport.model.database.AircraftModelRow
import airport.repositories.AircraftModelsRepositorySlick.aircraftModels
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class AircraftModelsRepositorySlick extends AircraftModelsRepository {
  def getById(id: AircraftModel): Future[Option[AircraftModelRow]] = db.run{
    aircraftModels.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[AircraftModelRow]] = db.run{
    aircraftModels.result
  }

  def create(name: String, year: Short, capacity: Int): Future[AircraftModelRow] = db.run {
    (aircraftModels.map(am => (am.name, am.year, am.capacity))
      returning aircraftModels.map(_.id)
      into ((amInfo, id) => database.AircraftModelRow(id, amInfo._1, amInfo._2, amInfo._3))
      ) += (name, year, capacity)
  }

  def upsert(aircraftModel: AircraftModelRow): Future[Int] = db.run{
    aircraftModels.insertOrUpdate(aircraftModel)
  }

  def delete(id: AircraftModel): Future[Int] = db.run{
    aircraftModels.filter(_.id === id).delete
  }
}

object AircraftModelsRepositorySlick {

  private[repositories] class AircraftModelsTable(tag: Tag) extends Table[AircraftModelRow](tag, Some("airport"), "aircraft_models") {
    def id = column[AircraftModel]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def year = column[Short]("production_year")

    def capacity = column[Int]("capacity")

    def * = (id, name, year, capacity) <> (AircraftModelRow.tupled, AircraftModelRow.unapply)
  }

  private[repositories] val aircraftModels = TableQuery[AircraftModelsTable]
}

