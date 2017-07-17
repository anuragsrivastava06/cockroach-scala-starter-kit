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
  def insert(user: User) = {
  val id = Random.alphanumeric.toString
    userComponent.insert(user.copy(id = id))
  }

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
    * Check whether user exists with employee id
    *
    * @param employeeId
    * @return
    */
  def isEmployeeIdExists(employeeId: String) = userComponent.isEmployeeIdExists(employeeId)

}
