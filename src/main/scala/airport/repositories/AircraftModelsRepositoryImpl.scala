package airport.repositories

import airport.database.AirportDatabase.db
import airport.model.{AircraftModel, AircraftModelId}
import airport.repositories.AircraftModelsRepositoryImpl.aircraftModels
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class AircraftModelsRepositoryImpl extends AircraftModelsRepository {
  def getById(id: AircraftModelId): Future[Option[AircraftModel]] = db.run{
    aircraftModels.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[AircraftModel]] = db.run{
    aircraftModels.result
  }

  def create(name: String, year: Short, capacity: Int): Future[AircraftModel] = db.run {
    (aircraftModels.map(am => (am.name, am.year, am.capacity))
      returning aircraftModels.map(_.id)
      into ((amInfo, id) => AircraftModel(id, amInfo._1, amInfo._2, amInfo._3))
      ) += (name, year, capacity)
  }

  def upsert(aircraftModel: AircraftModel): Future[Int] = db.run{
    aircraftModels.insertOrUpdate(aircraftModel)
  }

  def delete(id: AircraftModelId): Future[Int] = db.run{
    aircraftModels.filter(_.id === id).delete
  }
}

object AircraftModelsRepositoryImpl {

  private[repositories] class AircraftModelsTable(tag: Tag) extends Table[AircraftModel](tag, Some("airport"), "aircraft_models") {
    def id = column[AircraftModelId]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def year = column[Short]("production_year")

    def capacity = column[Int]("capacity")

    def * = (id, name, year, capacity) <> (AircraftModel.tupled, AircraftModel.unapply)
  }

  private[repositories] val aircraftModels = TableQuery[AircraftModelsTable]
}

