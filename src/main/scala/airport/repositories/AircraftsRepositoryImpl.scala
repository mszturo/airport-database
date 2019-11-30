package airport.repositories

import airport.database.AirportDatabase.db
import airport.model.{Aircraft, AircraftId, AircraftModelId}
import airport.repositories.AircraftModelsRepositoryImpl.aircraftModels
import airport.repositories.AircraftsRepositoryImpl.aircrafts
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class AircraftsRepositoryImpl extends AircraftsRepository {
  def getById(id: AircraftId): Future[Option[Aircraft]] = db.run{
    aircrafts.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[Aircraft]] = db.run{
    aircrafts.result
  }

  def create(operating: Boolean, model: AircraftModelId): Future[Aircraft] = db.run {
    (aircrafts.map(a => (a.operating, a.model))
      returning aircrafts.map(_.id)
      into ((aInfo, id) => Aircraft(id, aInfo._1, aInfo._2))
      ) += (operating, model)
  }

  def upsert(aircraft: Aircraft): Future[Int] = db.run{
    aircrafts.insertOrUpdate(aircraft)
  }

  def delete(id: AircraftId): Future[Int] = db.run{
    aircrafts.filter(_.id === id).delete
  }
}

object AircraftsRepositoryImpl {

  private[repositories] class AircraftsTable(tag: Tag) extends Table[Aircraft](tag, Some("airport"), "aircrafts") {
    def id = column[AircraftId]("id", O.PrimaryKey, O.AutoInc)

    def operating = column[Boolean]("operating")

    def model = column[AircraftModelId]("model")

    def modelKey = foreignKey("model_fk", model, aircraftModels)(_.id, onDelete = ForeignKeyAction.Cascade)

    def * = (id, operating, model) <> (Aircraft.tupled, Aircraft.unapply)
  }

  private[repositories] val aircrafts = TableQuery[AircraftsTable]
}