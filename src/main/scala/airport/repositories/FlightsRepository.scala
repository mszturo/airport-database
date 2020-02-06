package airport.repositories

import java.sql.Date

import airport.model.database.FlightRow
import airport.model.{AircraftId, CityId, FlightId}

import scala.concurrent.Future

trait FlightsRepository {
  def getById(id: FlightId): Future[Option[FlightRow]]

  def getAll: Future[Seq[FlightRow]]

  def create(from: CityId, departureDate: Date, to: CityId, arrivalDate: Date, aircraft: AircraftId): Future[FlightRow]

  def upsert(flight: FlightRow): Future[Int]

  def delete(id: FlightId): Future[Int]
}

