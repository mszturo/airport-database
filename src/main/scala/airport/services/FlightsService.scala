package airport.services

import java.sql.Date

import airport.model.{AircraftId, CityId, Flight, FlightId}
import airport.repositories.FlightsRepository

import scala.concurrent.Future

class FlightsService(repo: FlightsRepository) {
  def getById(id: FlightId): Future[Option[Flight]] = repo.getById(id)

  def getAll: Future[Seq[Flight]] = repo.getAll

  def create(from: CityId, departureDate: Date, to: CityId, arrivalDate: Date, aircraft: AircraftId): Future[Flight] =
    repo.create(from, departureDate, to, arrivalDate, aircraft)

  def upsert(flight: Flight): Future[Int] = repo.upsert(flight)

  def delete(id: FlightId): Future[Int] = repo.delete(id)
}
