package com.knoldus.DAO.user

import java.sql.Timestamp

import com.knoldus.DAO.db.H2DBComponent
import com.knoldus.DAO.user.mappings.User
import org.h2.jdbc.JdbcSQLException
import org.scalatest.AsyncFunSuite

class UserComponentTest extends AsyncFunSuite with UserComponent with H2DBComponent {


  test("Insert user into database record ") {
    val user = User("id-5", "knol-anurag", "anurag@knoldus.com")
    val resultF = insert(user)
    resultF.map(res => assert(res === 1))
  }

  test("Insert user into database ") {
    val user = User("id-6", "knol-jyotsna", "jyotsna@knoldus.com")
    val resultF = insert(user)
    resultF.map(res => assert(res === 1))
  }

  test("Insert user into database with existing primary key") {
    val user = User("id-2", "knol-jyotsna", "jyotsna@knoldus.com")
    recoverToSucceededIf[JdbcSQLException](insert(user))
  }

  test("Fetch user record with the help of email ") {
    val result = getUserByEmail("jyotsna@knoldus.com")
    val expectedUser = User(
      "id-2", "knol-jyotsna", "jyotsna@knoldus.com")
    result.map { actualUser =>
      assert(actualUser.map(_.id) === Some(expectedUser.id))
      assert(actualUser.map(_.email) === Some(expectedUser.email))
    }
  }

  test("Fetch user record with the wrong value of email") {
    val result = getUserByEmail("wrong.email@knoldus.com")
    result.map { actualUser =>
      assert(actualUser === None)
    }
  }

  test("Fetch user record with the help of userId") {
    val result = getUserByUserId("id-2")
    val expectedUser = User(
      "id-2", "knol-jyotsna", "jyotsna@knoldus.com")
    result.map { actualUser =>
      assert(actualUser.map(_.id) === Some(expectedUser.id))
      assert(actualUser.map(_.email) === Some(expectedUser.email))
    }
  }

  test("Fetch user record with the wrong userId") {
    val result = getUserByUserId("wrong-id")
    result.map { actualUser =>
      assert(actualUser === None)
    }
  }

  test("Fetch all users") {
    val result = getAllUsers
    result.map { actualUserList =>
      assert(actualUserList.length === 2)
    }
  }

  test("check user by employee Id") {
    val result = isEmployeeIdExists("id-1")
    result.map(isExists => assert(isExists === true))
  }

  test("check user by employee Id for wrong employee id") {
    val result = isEmployeeIdExists("wrong emp id")
    result.map(isExists => assert(isExists === false))
  }
}
