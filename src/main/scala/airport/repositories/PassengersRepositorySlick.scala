package airport.repositories

import java.sql.Date

import airport.model.{PassengerId, database}
import slick.jdbc.PostgresProfile.api._
import airport.database.AirportDatabase.db
import PassengersRepositorySlick.passengers
import airport.model.database.PassengerRow

import scala.concurrent.Future

class PassengersRepositorySlick {
  def getById(id: PassengerId): Future[Option[PassengerRow]] = db.run{
    passengers.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[PassengerRow]] = db.run{
    passengers.result
  }

  def create(firstName: String, middleName: Option[String] = None, lastName: String, birthday: Date): Future[PassengerRow] = db.run {
    (passengers.map(p => (p.firstName, p.middleName, p.lastName, p.birthday))
      returning passengers.map(_.id)
      into ((p, id) => database.PassengerRow(id, p._1, p._2, p._3, p._4))
      ) += (firstName, middleName, lastName, birthday)
  }

  def upsert(passenger: PassengerRow): Future[Int] = db.run{
    passengers.insertOrUpdate(passenger)
  }

  def delete(id: PassengerId): Future[Int] = db.run{
    passengers.filter(_.id === id).delete
  }
}

object PassengersRepositorySlick {

  private[repositories] class PassengersTable(tag: Tag) extends Table[PassengerRow](tag, Some("airport"), "passengers") {
    def id = column[PassengerId]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")

    def middleName = column[Option[String]]("middle_name", O.Default(None))

    def lastName = column[String]("last_name")

    def birthday = column[Date]("birthday")

    def * = (id, firstName, middleName, lastName, birthday) <> (PassengerRow.tupled, PassengerRow.unapply)
  }

  private[repositories] val passengers = TableQuery[PassengersTable]
}