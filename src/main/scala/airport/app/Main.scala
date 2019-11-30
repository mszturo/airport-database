package airport.app

import airport.database.DBConfig.MigrationConfig

object Main extends App {
  val x = MigrationConfig.migrate()

  println(s"Migrations executed: $x")
}
