package com.knoldus.DAO.db

import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api.Database

// $COVERAGE-OFF$
trait PostgresDbComponent extends DBComponent {

  val driver = PostgresProfile

  import driver.api.Database

  val db: Database = DBConnection.connectionPool
}

object DBConnection {
  val connectionPool = Database.forConfig("db")
}
// $COVERAGE-ON$
