package com.knoldus.util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.DAO.user.mappings.User
import spray.json._

trait JsonHelper extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val userFormat = jsonFormat3(User)
  implicit val boolean = Boolean
}
