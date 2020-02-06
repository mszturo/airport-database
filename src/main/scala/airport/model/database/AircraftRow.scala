package airport.model.database

import airport.model.{AircraftId, AircraftModelId}

case class AircraftRow(
  id: AircraftId,
  operating: Boolean,
  model: AircraftModelId)
