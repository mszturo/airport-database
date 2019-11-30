package airport

import airport.database.SqlDbEvolution
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object Boot {
  def boot(): Unit = {
    implicit lazy val ec: ExecutionContextExecutor = ExecutionContext.global
    lazy val sqlDbEvo = new SqlDbEvolution()

    sqlDbEvo.runEvolutions()
    lazy val db = Database.forConfig("database")


  }
}
