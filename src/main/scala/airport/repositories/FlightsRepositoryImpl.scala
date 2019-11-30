package airport.repositories

import java.sql.Date

import airport.database.AirportDatabase.db
import airport.model.{AircraftId, CityId, Flight, FlightId}
import airport.repositories.AircraftsRepositoryImpl.aircrafts
import airport.repositories.CitiesRepositoryImpl.cities
import airport.repositories.FlightsRepositoryImpl.flights
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class FlightsRepositoryImpl extends FlightsRepository {
  def getById(id: FlightId): Future[Option[Flight]] = db.run {
    flights.filter(_.id === id).result.headOption
  }

  def getAll: Future[Seq[Flight]] = db.run {
    flights.result
  }

  def create(from: CityId, departureDate: Date, to: CityId, arrivalDate: Date, aircraft: AircraftId): Future[Flight] = db.run {
    (flights.map(f => (f.fromCity, f.departureDate, f.toCity, f.arrivalDate, f.aircraft))
      returning flights.map(_.id)
      into ((fInfo, id) => Flight(id, fInfo._1, fInfo._2, fInfo._3, fInfo._4, fInfo._5))
      ) += (from, departureDate, to, arrivalDate, aircraft)
  }

  def upsert(flight: Flight): Future[Int] = db.run {
    flights.insertOrUpdate(flight)
  }

  def delete(id: FlightId): Future[Int] = db.run {
    flights.filter(_.id === id).delete
  }
}

object FlightsRepositoryImpl {

  private[repositories] class FlightsTable(tag: Tag) extends Table[Flight](tag, Some("airport"), "flights") {
    def id = column[FlightId]("id", O.PrimaryKey, O.AutoInc)

    def fromCity = column[CityId]("from_city")

    def fromCityKey = foreignKey("from_city_fk", fromCity, cities)(_.id, onDelete = ForeignKeyAction.Cascade)

    def departureDate = column[Date]("departure_date")

    def toCity = column[CityId]("to_city")

    def toCityKey = foreignKey("to_city_fk", toCity, cities)(_.id, onDelete = ForeignKeyAction.Cascade)

    def arrivalDate = column[Date]("arrival_date")

    def aircraft = column[AircraftId]("aircraft")

    def aircraftKey = foreignKey("aircraft_fk", aircraft, aircrafts)(_.id, onDelete = ForeignKeyAction.NoAction)

    def * = (id, fromCity, departureDate, toCity, arrivalDate, aircraft) <> (Flight.tupled, Flight.unapply)
  }

  private[repositories] val flights = TableQuery[FlightsTable]
}