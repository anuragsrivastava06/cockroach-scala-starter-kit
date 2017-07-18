package com.knoldus

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.google.inject.Guice
import com.knoldus.api.UserApi
import net.codingwell.scalaguice.InjectorExtensions._

import scala.concurrent.ExecutionContext.Implicits.global

object UserBoot extends App {

  val injector = Guice.createInjector(new UserModule)
  val userApi = injector.instance[UserApi]

  implicit val actor = ActorSystem("user-api")
  implicit val actorMaterializer = ActorMaterializer()

  Http().bindAndHandle(userApi.routes, "localhost", 9999).map(service => println(s"Application up and running at ${service.localAddress}"))
}
