package com.knoldus.DAO.user


import com.knoldus.DAO.db.{DBComponent, PostgresDbComponent}
import com.knoldus.DAO.user.mappings.{User, UserMapping}
import com.google.inject.ImplementedBy
import scala.concurrent.Future

@ImplementedBy(classOf[UserPostgresComponent])
trait UserComponent extends UserMapping {

  this: DBComponent =>

  import driver.api._

  /**
    * Inserts user into database
    *
    * @param user
    * @return
    */
  def insert(user: User): Future[Int] = {
    db.run(userInfo += user)
  }

  /**
    * Fetches user detail using email
    *
    * @param email
    * @return Future[Option[User]]
    **/
  def getUserByEmail(email: String): Future[Option[User]] = {
    db.run(userInfo.filter(user => user.email === email).result.headOption)
  }

  /**
    * Fetches user record with the help of userId
    *
    * @param userId
    * @return Option[User]
    */
  def getUserByUserId(userId: String): Future[Option[User]] = {
    db.run(userInfo.filter(user => user.id === userId).result.headOption)
  }

  /**
    * Fetches All user from DB
    *
    * @return
    */
  def getAllUsers: Future[List[User]] = {
    db.run(userInfo.to[List].result)
  }

  /**
    * Checks if user with employee id exists
    *
    * @param employeeId
    * @return
    */
  def isEmployeeIdExists(employeeId: String): Future[Boolean] = {
    val query = userInfo.filter(user => user.id === employeeId).exists
    db.run(query.result)
  }
}

class UserPostgresComponent extends UserComponent with PostgresDbComponent
