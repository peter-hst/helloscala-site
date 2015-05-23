package com.helloscala.site.service

import com.helloscala.common.exception.HsNotFoundException
import com.helloscala.site.data.domain.{AdminPage, AdminPageQuery, ModifyPasswordParam}
import com.helloscala.site.data.model.Admin
import com.helloscala.site.data.repo.{AdminRepo, CredentialRepo}

import scala.concurrent.{ExecutionContext, Future}

class AdminServiceImpl()(implicit ex: ExecutionContext) extends AdminService {
  val adminRepo = new AdminRepo()
  val credentialRepo = new CredentialRepo()

  override def loginByEmail(email: String, password: String): Future[Long] = {
    credentialRepo.loginByEmail(email, password)
  }

  override def update(payload: Admin): Future[Admin] = {
    adminRepo.update(payload).map {
      case 1 => payload
      case _ => throw HsNotFoundException("update admin: " + payload.name.getOrElse(payload.id.toString))
    }
  }

  override def create(email: String, password: String): Future[Long] = {
    adminRepo.create(email, password)
  }

  override def removeOne(id: Long): Future[Boolean] = {
    adminRepo.removeOneById(id).map(_ == 1)
  }

  override def findList(page: Int, size: Int): Future[AdminPage] = {
    adminRepo.findList(page, size).map { case (items, total) =>
      AdminPage(items, Some(total), AdminPageQuery(page, size))
    }
  }

  override def findOneById(id: Long): Future[Option[Admin]] = {
    adminRepo.findOneById(id)
  }

  override def modifyPassword(id: Long, param: ModifyPasswordParam): Future[Boolean] = {
    credentialRepo.updatePassword(id, param.nowPassword, param.newPassword).map(_ == 1)
  }
}
