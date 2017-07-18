package com.knoldus.api

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Inject
import com.knoldus.DAO.user.mappings.User
import com.knoldus.service.UserService
import com.knoldus.util.JsonHelper

import scala.util.{Failure, Success}

class UserApi@Inject()(userService: UserService) extends JsonHelper {

  /**
    * Default route
    *
    * @return
    */
  def welcomeRoute: Route = path("") {
    get {
      complete("Cockroach Db starter kit with Akka http")
    }
  }

  /**
    * Creates http route to insert user object
    *
    * @return
    */
  def insertUser: Route = path("user" / "add") {
    (post & entity(as[User])) { user =>
      onComplete(userService.isUserIdExists(user.id)) {
        case Success(res) => validate(!res, s"User with id '${user.id}' already exists") {
          onComplete(userService.insert(user)) {
            case Success(result) => complete("User added successfully")
            case Failure(ex) => complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
          }
        }
        case Failure(ex) => complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
      }
    }
  }

  /**
    * Creates http route to get user by user id
    *
    * @return
    */
  def getUserByUserId: Route = path("user" / "get") {
    get {
      parameters("id") { id =>
        onComplete(userService.getUserByUserId(id)) {
          case Success(userOpt) => userOpt match {
            case Some(user) => complete(user)
            case None => val msg = s"No user found with user id: ${id}"
              complete(HttpResponse(StatusCodes.BadRequest, entity = msg))
          }
          case Failure(ex) => complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
        }
      }
    }
  }

  /**
    * Creates http route to get user by email
    *
    * @return
    */
  def getUserByEmail: Route = path("user" / "get") {
    get {
      parameters("email") { email =>
        onComplete(userService.getUserByEmail(email)) {
          case Success(userOpt) => userOpt match {
            case Some(user) => complete(user)
            case None => val msg = s"No user found with email: ${email}"
              complete(HttpResponse(StatusCodes.BadRequest, entity = msg))
          }
          case Failure(ex) => complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
        }
      }
    }
  }

  /**
    * Creates http route to get list of all users
    *
    * @return
    */
  def getAllUsers: Route = path("user" / "get" / "all") {
    get {
      onComplete(userService.getAllUsers()) {
        case Success(users) => complete(users)
        case Failure(ex) => complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
      }
    }
  }


  /**
    * Creates http route to check whether user exists by given user Id
    *
    * @return
    */
  def isUserIdExists: Route = path("user" / "exists") {
    get {
      parameters("userId") { userId =>
        onComplete(userService.isUserIdExists(userId)) {
          case Success(users) => complete(users.toString)
          case Failure(ex) => complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
        }
      }
    }
  }

  val routes = welcomeRoute ~ insertUser ~ getUserByUserId ~ getUserByEmail ~ getAllUsers ~ isUserIdExists
}
