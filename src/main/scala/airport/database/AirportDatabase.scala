package airport.database

import slick.jdbc.PostgresProfile.api.Database

object AirportDatabase {
  val db = Database.forConfig("database")
}
