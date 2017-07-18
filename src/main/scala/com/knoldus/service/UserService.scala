package com.knoldus.service

import com.google.inject.Inject
import com.knoldus.DAO.user.UserComponent
import com.knoldus.DAO.user.mappings.User

import scala.util.Random

class UserService @Inject()(userComponent: UserComponent){

  /**
    * Inserts user object in db
    *
    * @param user
    */
  def insert(user: User) = userComponent.insert(user)

  /**
    * Get user by user id
    *
    * @param id
    * @return
    */
  def getUserByUserId(id: String) = userComponent.getUserByUserId(id)

  /**
    * Get user by email id
    *
    * @param email
    * @return
    */
  def getUserByEmail(email: String) = userComponent.getUserByEmail(email)

  /**
    * Get list of all users
    *
    * @return
    */
  def getAllUsers() = userComponent.getAllUsers

  /**
    * Check whether user exists with user id
    *
    * @param userId
    * @return
    */
  def isUserIdExists(userId: String) = userComponent.isUserIdExists(userId)

}
