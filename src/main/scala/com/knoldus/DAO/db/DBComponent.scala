package com.knoldus.DAO.db

import slick.jdbc.JdbcProfile

trait DBComponent {

  val driver: JdbcProfile

  import driver.api._

  val db: Database
}

