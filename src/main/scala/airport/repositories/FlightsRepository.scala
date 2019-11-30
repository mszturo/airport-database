package airport.repositories

import java.sql.Date

import airport.model.{AircraftId, CityId, Flight, FlightId}

import scala.concurrent.Future

trait FlightsRepository {
  def getById(id: FlightId): Future[Option[Flight]]

  def getAll: Future[Seq[Flight]]

  def create(from: CityId, departureDate: Date, to: CityId, arrivalDate: Date, aircraft: AircraftId): Future[Flight]

  def upsert(flight: Flight): Future[Int]

  def delete(id: FlightId): Future[Int]
}

