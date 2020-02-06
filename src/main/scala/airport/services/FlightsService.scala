package airport.services

import java.sql.Date

import airport.model.database.FlightRow
import airport.model.{AircraftId, CityId, FlightId}
import airport.repositories.FlightsRepository

import scala.concurrent.Future

class FlightsService(repo: FlightsRepository) {
  def getById(id: FlightId): Future[Option[FlightRow]] = repo.getById(id)

  def getAll: Future[Seq[FlightRow]] = repo.getAll

  def create(from: CityId, departureDate: Date, to: CityId, arrivalDate: Date, aircraft: AircraftId): Future[FlightRow] =
    repo.create(from, departureDate, to, arrivalDate, aircraft)

  def upsert(flight: FlightRow): Future[Int] = repo.upsert(flight)

  def delete(id: FlightId): Future[Int] = repo.delete(id)
}
