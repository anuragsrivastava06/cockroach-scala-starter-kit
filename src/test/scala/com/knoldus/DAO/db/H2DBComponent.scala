package com.knoldus.DAO.db

import slick.jdbc.H2Profile

trait H2DBComponent extends DBComponent {

  val driver = H2Profile
  import driver.api.Database
  val h2Url = "jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'src/test/resources/schema.sql'\\;RUNSCRIPT FROM 'src/test/resources/initaldata.sql'"
  val db: Database = Database.forURL(url = h2Url, driver = "org.h2.Driver")

}


