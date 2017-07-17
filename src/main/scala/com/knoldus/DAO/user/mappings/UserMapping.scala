package com.knoldus.DAO.user.mappings

import com.knoldus.DAO.db.DBComponent
import slick.lifted.ProvenShape

case class User(id: String = "", name: String, email: String)

trait UserMapping {
  this: DBComponent =>

  import driver.api._

  class UserMapping(tag: Tag) extends Table[User](tag, "user") {
    def id: Rep[String] = column[String]("id", O.PrimaryKey)

    def name: Rep[String] = column[String]("name")

    def email: Rep[String] = column[String]("email", O.Unique)

    def * : ProvenShape[User] = (
      id,
      name,
      email
    ) <> (User.tupled, User.unapply)
  }

  val userInfo: TableQuery[UserMapping] = TableQuery[UserMapping]
}
