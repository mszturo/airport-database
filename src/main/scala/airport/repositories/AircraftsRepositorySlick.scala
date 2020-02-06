package airport.repositories

import airport.database.AirportDatabase.db
import airport.model.database.AircraftRow
import airport.model.{AircraftId, AircraftModelId, database}
import airport.repositories.AircraftModelsRepositorySlick.aircraftModels
import airport.repositories.AircraftsRepositorySlick.aircrafts
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class AircraftsRepositorySlick extends AircraftsRepository {
  def getById(id: AircraftId): Future[Option[AircraftRow]] = db.run{
    aircrafts.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[AircraftRow]] = db.run{
    aircrafts.result
  }

  def create(operating: Boolean, model: AircraftModel): Future[AircraftRow] = db.run {
    (aircrafts.map(a => (a.operating, a.model))
      returning aircrafts.map(_.id)
      into ((aInfo, id) => database.AircraftRow(id, aInfo._1, aInfo._2))
      ) += (operating, model)
  }

  def upsert(aircraft: AircraftRow): Future[Int] = db.run{
    aircrafts.insertOrUpdate(aircraft)
  }

  def delete(id: AircraftId): Future[Int] = db.run{
    aircrafts.filter(_.id === id).delete
  }
}

object AircraftsRepositorySlick {

  private[repositories] class AircraftsTable(tag: Tag) extends Table[AircraftRow](tag, Some("airport"), "aircrafts") {
    def id = column[AircraftId]("id", O.PrimaryKey, O.AutoInc)

    def operating = column[Boolean]("operating")

    def model = column[AircraftModel]("model")

    def modelKey = foreignKey("model_fk", model, aircraftModels)(_.id, onDelete = ForeignKeyAction.Cascade)

    def * = (id, operating, model) <> (AircraftRow.tupled, AircraftRow.unapply)
  }

  private[repositories] val aircrafts = TableQuery[AircraftsTable]
}