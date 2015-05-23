package com.helloscala.site.service

import com.helloscala.common.exception.HsInternalException
import com.helloscala.common.types.SortType
import com.helloscala.site.data.domain.{LoginParam, RegisterParam, UserPage, UserPageQuery}
import com.helloscala.site.data.model.User
import com.helloscala.site.data.repo.{CredentialRepo, UserRepo}

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl()(implicit ec: ExecutionContext) extends UserService {
  val userRepo = new UserRepo()
  val credentialRepo = new CredentialRepo()

  override def findOneById(id: Long): Future[Option[User]] = {
    userRepo.findOneById(id)
  }

  override def registerWithEmail(payload: RegisterParam): Future[Long] = {
    userRepo.create(payload)
  }

  override def loginWithEmail(payload: LoginParam): Future[Long] = {
    credentialRepo.loginByEmail(payload.account, payload.password)
  }

  override def findList(page: Int, size: Int): Future[UserPage] = {
    userRepo.findList(page, size).map { case (items, total) =>
      UserPage(items, Some(total), UserPageQuery(page, size, SortType.DESC))
    }
  }

  override def update(payload: User): Future[User] = {
    userRepo.update(payload).map {
      case 1 => payload
      case _ => throw HsInternalException("修改用户错误")
    }
  }
}
