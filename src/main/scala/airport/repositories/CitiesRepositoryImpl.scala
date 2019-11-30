package airport.repositories

import airport.model.{City, CityId}
import slick.jdbc.PostgresProfile.api._
import airport.database.AirportDatabase.db
import CitiesRepositoryImpl.cities

import scala.concurrent.Future

class CitiesRepositoryImpl extends CitiesRepository {
  def getById(id: CityId): Future[Option[City]] = db.run{
    cities.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[City]] = db.run{
    cities.result
  }

  def create(name: String, abbreviation: String): Future[City] = db.run {
    (cities.map(c => (c.name, c.abbreviation))
      returning cities.map(_.id)
      into ((nameAbb, id) => City(id, nameAbb._1, nameAbb._2))
      ) += (name, abbreviation)
  }

  def upsert(city: City): Future[Int] = db.run{
    cities.insertOrUpdate(city)
  }

  def delete(id: CityId): Future[Int] = db.run{
    cities.filter(_.id === id).delete
  }
}

object CitiesRepositoryImpl {

  private[repositories]  class CitiesTable(tag: Tag) extends Table[City](tag, Some("airport"), "cities") {
    def id = column[CityId]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def abbreviation = column[String]("abbreviation")

    def * = (id, name, abbreviation) <> (City.tupled, City.unapply)
  }

  private[repositories] val cities = TableQuery[CitiesTable]
}