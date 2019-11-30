package airport.repositories

import java.sql.Date

import airport.model.{Passenger, PassengerId}
import slick.jdbc.PostgresProfile.api._
import airport.database.AirportDatabase.db
import PassengersRepositoryImpl.passengers

import scala.concurrent.Future

class PassengersRepositoryImpl {
  def getById(id: PassengerId): Future[Option[Passenger]] = db.run{
    passengers.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[Passenger]] = db.run{
    passengers.result
  }

  def create(firstName: String, middleName: Option[String] = None, lastName: String, birthday: Date): Future[Passenger] = db.run {
    (passengers.map(p => (p.firstName, p.middleName, p.lastName, p.birthday))
      returning passengers.map(_.id)
      into ((p, id) => Passenger(id, p._1, p._2, p._3, p._4))
      ) += (firstName, middleName, lastName, birthday)
  }

  def upsert(passenger: Passenger): Future[Int] = db.run{
    passengers.insertOrUpdate(passenger)
  }

  def delete(id: PassengerId): Future[Int] = db.run{
    passengers.filter(_.id === id).delete
  }
}

object PassengersRepositoryImpl {

  private[repositories] class PassengersTable(tag: Tag) extends Table[Passenger](tag, Some("airport"), "passengers") {
    def id = column[PassengerId]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")

    def middleName = column[Option[String]]("middle_name", O.Default(None))

    def lastName = column[String]("last_name")

    def birthday = column[Date]("birthday")

    def * = (id, firstName, middleName, lastName, birthday) <> (Passenger.tupled, Passenger.unapply)
  }

  private[repositories] val passengers = TableQuery[PassengersTable]
}