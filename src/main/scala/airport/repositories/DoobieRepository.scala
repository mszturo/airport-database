package airport.repositories

import airport.database.DBConfig
import cats.effect.{Blocker, ContextShift, IO}
import doobie.free.connection.ConnectionIO
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._

import scala.concurrent.{ExecutionContext, Future}

trait DoobieRepository {
  implicit def ec: ExecutionContext
  // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
  // is where nonblocking operations will be executed. For testing here we're using a synchronous EC.
  implicit lazy val cs: ContextShift[IO] = IO.contextShift(ec)

  import DBConfig._
  // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
  // on an our synchronous EC. See the chapter on connection handling for more info.
  lazy val xa: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    driver,
    url,
    user,
    password,
    Blocker.liftExecutionContext(ec)
  )

  implicit class Runnable[T](query: ConnectionIO[T]) {
    def runSync(): Future[T] = query.transact(xa).unsafeToFuture()
  }
}
