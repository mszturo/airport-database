package airport.model.database

import airport.model.CityId

case class CityRow(
  id: CityId,
  name: String,
  abbreviation: String)
