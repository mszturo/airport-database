package airport

import airport.model.database.AircraftModelRow
import io.getquill.{idiom => _, _}
import io.getquill._
import doobie.quill.DoobieContext

package object database {
  object schema {
    val aircraftModels = quote {
      querySchema[AircraftModelRow](
        "aircraft_models",
        _.id -> "id",
        _.name -> "name",
        _.year -> "year",
        _.capacity -> "capacity")
    }
  }
}
