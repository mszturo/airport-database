package airport.database

import org.flywaydb.core.Flyway
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class SqlDbEvolution(additionalMigrationPackages: List[String] = List.empty)(implicit val ec: ExecutionContext)
  extends LazyLogging {

  import DBConfig._

  private lazy val flyway = Flyway
    .configure()
    .dataSource(url, user, password)
    .locations(migrationScriptsLocation :: additionalMigrationPackages: _*)
    .load()

  def runEvolutions(): Future[Int] = Future {
    Try(flyway.migrate()) match {
      case Success(migrationsCount) => migrationsCount
      case Failure(why) =>
        logger.error(s"DB migration for $url failed. All activities should be terminated", why)
        throw why
    }
  }
}
