package com.knoldus.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ValidationRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.DAO.user.mappings.User
import com.knoldus.service.UserService
import com.knoldus.util.JsonHelper
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFunSuite, Matchers}
import spray.json._

import scala.concurrent.Future

class UserApiTest extends AsyncFunSuite with ScalatestRouteTest with MockitoSugar with Matchers with JsonHelper {

  val mockedUserService = mock[UserService]

  val userApi = new UserApi(mockedUserService)
  import userApi._

  test("api default welcome route") {
    Get("/") ~> welcomeRoute ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "Cockroach Db starter kit with Akka http"
    }
  }

  test("api to insert user data successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.successful(false))
    when(mockedUserService.insert(user)).thenReturn(Future.successful(1))
    Post("/user/add", user.toJson) ~> insertUser ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "User added successfully"
    }
  }

  test("api to insert user data, when service to check user id fails") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.failed(new RuntimeException("Exception while fetching user")))
    when(mockedUserService.insert(user)).thenReturn(Future.successful(1))
    Post("/user/add", user.toJson) ~> insertUser ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Exception while fetching user"
    }
  }


  test("api to insert user data, when user id already exists") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.successful(true))
    when(mockedUserService.insert(user)).thenReturn(Future.successful(1))
    Post("/user/add", user.toJson) ~> insertUser ~> check {
      rejection shouldBe ValidationRejection("User with id 'testId1' already exists")
    }
  }


  test("api to insert user data: exception case") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.successful(false))
    when(mockedUserService.insert(user)).thenReturn(Future.failed(new RuntimeException("Exception while inserting user")))
    Post("/user/add", user.toJson) ~> insertUser ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Exception while inserting user"
    }
  }

  test("api to get user by user id successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.getUserByUserId("testId1")).thenReturn(Future.successful(Some(user)))
    Get("/user/get?id=testId1") ~> getUserByUserId ~> check {
   status shouldBe StatusCodes.OK
      responseAs[Option[User]] shouldBe Some(user)
    }
  }

  test("api to get user by user id, when no user found") {
    when(mockedUserService.getUserByUserId("testId1")).thenReturn(Future.successful(None))
    Get("/user/get?id=testId1") ~> getUserByUserId ~> check {
      status shouldBe StatusCodes.BadRequest
      responseAs[String] shouldBe "No user found with user id: testId1"
    }
  }

  test("api to get user by user id: exception case") {
    when(mockedUserService.getUserByUserId("testId1")).thenReturn(Future.failed(new RuntimeException("Exception while fetching user")))
    Get("/user/get?id=testId1") ~> getUserByUserId ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Exception while fetching user"
    }
  }

  test("api to get user by email successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.getUserByEmail("testuser@gmail.com")).thenReturn(Future.successful(Some(user)))
    Get("/user/get?email=testuser@gmail.com") ~> getUserByEmail ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Option[User]] shouldBe Some(user)
    }
  }

  test("api to get user by email, when no user found") {
    when(mockedUserService.getUserByEmail("testuser@gmail.com")).thenReturn(Future.successful(None))
    Get("/user/get?email=testuser@gmail.com") ~> getUserByEmail ~> check {
      status shouldBe StatusCodes.BadRequest
      responseAs[String] shouldBe "No user found with email: testuser@gmail.com"
    }
  }

  test("api to get user by email: exception case") {
    when(mockedUserService.getUserByEmail("testuser@gmail.com")).thenReturn(Future.failed(new RuntimeException("Exception while fetching user")))
    Get("/user/get?email=testuser@gmail.com") ~> getUserByEmail ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Exception while fetching user"
    }
  }

  test("api to get all users successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserService.getAllUsers()).thenReturn(Future.successful(List(user)))
    Get("/user/get/all") ~> getAllUsers ~> check {
      status shouldBe StatusCodes.OK
      responseAs[List[User]] shouldBe List(user)
    }
  }

  test("api to get all users, when no users found") {
    when(mockedUserService.getAllUsers()).thenReturn(Future.successful(Nil))
    Get("/user/get/all") ~> getAllUsers ~> check {
      status shouldBe StatusCodes.OK
      responseAs[List[User]] shouldBe Nil
    }
  }

  test("api to get all users: exception case") {
    when(mockedUserService.getAllUsers()).thenReturn(Future.failed(new RuntimeException("Exception while fetching users")))
    Get("/user/get/all") ~> getAllUsers ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Exception while fetching users"
    }
  }

  test("api to check user exist with user id successfully") {
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.successful(true))
    Get("/user/exists?userId=testId1") ~> isUserIdExists ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "true"
    }
  }

  test("api to check user exist with user id, when no users found") {
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.successful(false))
    Get("/user/exists?userId=testId1") ~> isUserIdExists ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe "false"
    }
  }

  test("api to check user exist with user id: exception case") {
    when(mockedUserService.isUserIdExists("testId1")).thenReturn(Future.failed(new RuntimeException("Exception while fetching user")))
    Get("/user/exists?userId=testId1") ~> isUserIdExists ~> check {
      status shouldBe StatusCodes.InternalServerError
      responseAs[String] shouldBe "Exception while fetching user"
    }
  }
}
