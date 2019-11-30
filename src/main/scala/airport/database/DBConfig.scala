package airport.database

import com.typesafe.config.ConfigFactory
import org.flywaydb.core.Flyway

object DBConfig {
  object MigrationConfig{
    private val flyway = Flyway
      .configure()
      .dataSource(url, user, password)
      .load()

    def migrate(): Int = flyway.migrate()
  }

  private val conf = ConfigFactory.load().getConfig("database.properties")
  protected[airport] val user: String = conf.getString("user")
  protected[airport] val password: String = conf.getString("password")
  protected[airport] val host: String = conf.getString("host")
  protected[airport] val port: String = conf.getString("port")
  protected[airport] val dbName: String = conf.getString("databaseName")
//  protected[airport] val url: String =   s"jdbc:postgresql://$user:$password@$host:$port/$dbName"
  protected[airport] val url: String = s"jdbc:postgresql://$host:$port/$dbName?user=$user&password=$password"
  protected[airport] val driver: String = conf.getString("driver")
  protected[airport] val migrationScriptsLocation: String = conf.getString("migration-scripts")
}
