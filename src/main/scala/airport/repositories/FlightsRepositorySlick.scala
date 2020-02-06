package airport.repositories

import java.sql.Date

import airport.database.AirportDatabase.db
import airport.model.database.FlightRow
import airport.model.{AircraftId, CityId, FlightId, database}
import airport.repositories.AircraftsRepositorySlick.aircrafts
import airport.repositories.CitiesRepositorySlick.cities
import airport.repositories.FlightsRepositorySlick.flights
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class FlightsRepositorySlick extends FlightsRepository {
  def getById(id: FlightId): Future[Option[FlightRow]] = db.run {
    flights.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[FlightRow]] = db.run {
    flights.result
  }

  def create(from: CityId, departureDate: Date, to: CityId, arrivalDate: Date, aircraft: AircraftId): Future[FlightRow] = db.run {
    (flights.map(f => (f.fromCity, f.departureDate, f.toCity, f.arrivalDate, f.aircraft))
      returning flights.map(_.id)
      into ((fInfo, id) => database.FlightRow(id, fInfo._1, fInfo._2, fInfo._3, fInfo._4, fInfo._5))
      ) += (from, departureDate, to, arrivalDate, aircraft)
  }

  def upsert(flight: FlightRow): Future[Int] = db.run {
    flights.insertOrUpdate(flight)
  }

  def delete(id: FlightId): Future[Int] = db.run {
    flights.filter(_.id === id).delete
  }
}

object FlightsRepositorySlick {

  private[repositories] class FlightsTable(tag: Tag) extends Table[FlightRow](tag, Some("airport"), "flights") {
    def id = column[FlightId]("id", O.PrimaryKey, O.AutoInc)

    def fromCity = column[CityId]("from_city")

    def fromCityKey = foreignKey("from_city_fk", fromCity, cities)(_.id, onDelete = ForeignKeyAction.Cascade)

    def departureDate = column[Date]("departure_date")

    def toCity = column[CityId]("to_city")

    def toCityKey = foreignKey("to_city_fk", toCity, cities)(_.id, onDelete = ForeignKeyAction.Cascade)

    def arrivalDate = column[Date]("arrival_date")

    def aircraft = column[AircraftId]("aircraft")

    def aircraftKey = foreignKey("aircraft_fk", aircraft, aircrafts)(_.id, onDelete = ForeignKeyAction.NoAction)

    def * = (id, fromCity, departureDate, toCity, arrivalDate, aircraft) <> (FlightRow.tupled, FlightRow.unapply)
  }

  private[repositories] val flights = TableQuery[FlightsTable]
}