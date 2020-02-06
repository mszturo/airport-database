package airport.repositories

import airport.model.{CityId, database}
import slick.jdbc.PostgresProfile.api._
import airport.database.AirportDatabase.db
import CitiesRepositorySlick.cities
import airport.model.database.CityRow

import scala.concurrent.Future

class CitiesRepositorySlick extends CitiesRepository {
  def getById(id: CityId): Future[Option[CityRow]] = db.run{
    cities.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[CityRow]] = db.run{
    cities.result
  }

  def create(name: String, abbreviation: String): Future[CityRow] = db.run {
    (cities.map(c => (c.name, c.abbreviation))
      returning cities.map(_.id)
      into ((nameAbb, id) => database.CityRow(id, nameAbb._1, nameAbb._2))
      ) += (name, abbreviation)
  }

  def upsert(city: CityRow): Future[Int] = db.run{
    cities.insertOrUpdate(city)
  }

  def delete(id: CityId): Future[Int] = db.run{
    cities.filter(_.id === id).delete
  }
}

object CitiesRepositorySlick {

  private[repositories]  class CitiesTable(tag: Tag) extends Table[CityRow](tag, Some("airport"), "cities") {
    def id = column[CityId]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def abbreviation = column[String]("abbreviation")

    def * = (id, name, abbreviation) <> (CityRow.tupled, CityRow.unapply)
  }

  private[repositories] val cities = TableQuery[CitiesTable]
}