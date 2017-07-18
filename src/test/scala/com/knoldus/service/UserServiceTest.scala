package com.knoldus.service

import com.knoldus.DAO.user.UserComponent
import com.knoldus.DAO.user.mappings.User
import org.scalatest.{AsyncFunSuite, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

import scala.concurrent.Future

class UserServiceTest extends AsyncFunSuite with MockitoSugar with Matchers {

  val mockedUserComponent = mock[UserComponent]
  val userService = new UserService(mockedUserComponent)

  test("insert user successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.insert(user)).thenReturn(Future.successful(1))
    val result = userService.insert(user)
    result.map(insertedRows => insertedRows shouldBe 1)
  }

  test("insert user : exception case") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.insert(user)).thenReturn(Future.failed(new RuntimeException("exception")))
    val result = userService.insert(user)
    result.failed.map(fail => fail.getMessage shouldBe "exception")
  }


  test("get user by user id successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.getUserByUserId("testId1")).thenReturn(Future.successful(Some(user)))
    val result = userService.getUserByUserId("testId1")
    result.map(userOpt => userOpt shouldBe Some(user))
  }

  test("get user by user id returns none") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.getUserByUserId("testId1")).thenReturn(Future.successful(None))
    val result = userService.getUserByUserId("testId1")
    result.map(userOpt => userOpt shouldBe None)
  }

  test("get user by user id: exception case") {
    when(mockedUserComponent.getUserByUserId("testId1")).thenReturn(Future.failed(new RuntimeException("exception")))
    val result = userService.getUserByUserId("testId1")
    result.failed.map(fail => fail.getMessage shouldBe "exception")
  }

  test("get user by email successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.getUserByEmail("testuser@gmail.com")).thenReturn(Future.successful(Some(user)))
    val result = userService.getUserByEmail("testuser@gmail.com")
    result.map(userOpt => userOpt shouldBe Some(user))
  }

  test("get user by email returns none") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.getUserByEmail("testuser@gmail.com")).thenReturn(Future.successful(None))
    val result = userService.getUserByEmail("testuser@gmail.com")
    result.map(userOpt => userOpt shouldBe None)
  }

  test("get user by email: exception case") {
    when(mockedUserComponent.getUserByEmail("testuser@gmail.com")).thenReturn(Future.failed(new RuntimeException("exception")))
    val result = userService.getUserByEmail("testuser@gmail.com")
    result.failed.map(fail => fail.getMessage shouldBe "exception")
  }

  test("get all users successfully") {
    val user = User("testId1", "test user", "testuser@gmail.com")
    when(mockedUserComponent.getAllUsers).thenReturn(Future.successful(List(user)))
    val result = userService.getAllUsers
    result.map(userList => userList shouldBe List(user))
  }

  test("get all users returns Nil") {
    when(mockedUserComponent.getAllUsers).thenReturn(Future.successful(Nil))
    val result = userService.getAllUsers
    result.map(userList => userList shouldBe Nil)
  }

  test("get all users: exception case") {
    when(mockedUserComponent.getAllUsers).thenReturn(Future.failed(new RuntimeException("exception")))
    val result = userService.getAllUsers()
    result.failed.map(fail => fail.getMessage shouldBe "exception")
  }

  test("check user by user id exists successfully") {
    when(mockedUserComponent.isUserIdExists("testId1")).thenReturn(Future.successful(true))
    val result = userService.isUserIdExists("testId1")
    result.map(isUserExist => isUserExist shouldBe true)
  }

  test("check user by user id exists, case false") {
    when(mockedUserComponent.isUserIdExists("testId1")).thenReturn(Future.successful(false))
    val result = userService.isUserIdExists("testId1")
    result.map(isUserExist => isUserExist shouldBe false)
  }

  test("check user by user id exists: exception case") {
    when(mockedUserComponent.isUserIdExists("testId1")).thenReturn(Future.failed(new RuntimeException("exception")))
    val result = userService.isUserIdExists("testId1")
    result.failed.map(fail => fail.getMessage shouldBe "exception")
  }


}
