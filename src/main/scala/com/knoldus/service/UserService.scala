package com.knoldus.service

import com.google.inject.Inject
import com.knoldus.DAO.user.UserComponent
import com.knoldus.DAO.user.mappings.User

import scala.concurrent.Future

class UserService @Inject()(userComponent: UserComponent){

  /**
    * Inserts user object in db
    *
    * @param user
    */
  def insert(user: User): Future[Int] = userComponent.insert(user)

  /**
    * Get user by user id
    *
    * @param id
    * @return
    */
  def getUserByUserId(id: String): Future[Option[User]] = userComponent.getUserByUserId(id)

  /**
    * Get user by email id
    *
    * @param email
    * @return
    */
  def getUserByEmail(email: String): Future[Option[User]] = userComponent.getUserByEmail(email)

  /**
    * Get list of all users
    *
    * @return
    */
  def getAllUsers(): Future[List[User]] = userComponent.getAllUsers

  /**
    * Check whether user exists with user id
    *
    * @param userId
    * @return
    */
  def isUserIdExists(userId: String): Future[Boolean] = userComponent.isUserIdExists(userId)

}
