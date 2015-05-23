package com.helloscala.site.service

import com.helloscala.site.data.domain.{LoginParam, RegisterParam, UserPage}
import com.helloscala.site.data.model.User

import scala.concurrent.{ExecutionContext, Future}

trait UserService {
  def findList(page: Int, size: Int): Future[UserPage]

  def update(payload: User): Future[User]

  def findOneById(id: Long): Future[Option[User]]

  /**
   *
   * @param payload
   * @return 成功返回用户id
   */
  def registerWithEmail(payload: RegisterParam): Future[Long]

  def loginWithEmail(request: LoginParam): Future[Long]
}

object UserService {
  def apply()(implicit ec: ExecutionContext): UserService = new UserServiceImpl()
}
