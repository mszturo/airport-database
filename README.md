### Prerequisites
- Scala 2.13
- sbt
- Postgresql 11 with database created

### Config
Configuration file is contained in `src/main/resources/application.conf`. Enter your user, password, host, port and database name under `database.properties`.

Database migration scripts are contained in the `src/main/resources/db/migration` directory.

### Run
To run the program type `sbt run` in the terminal. The program will apply db migrations to your database.