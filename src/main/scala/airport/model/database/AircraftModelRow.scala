package airport.model.database

import airport.model.AircraftModelId

case class AircraftModelRow(
  id: AircraftModelId,
  name: String,
  year: Short,
  capacity: Int)
