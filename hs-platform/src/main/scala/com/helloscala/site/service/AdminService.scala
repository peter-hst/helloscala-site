package com.helloscala.site.service

import com.helloscala.site.data.domain.{AdminPage, ModifyPasswordParam}
import com.helloscala.site.data.model.Admin

import scala.concurrent.{ExecutionContext, Future}

/**
 * 管理员
 * Created by yangjing on 15-5-23.
 */
trait AdminService {
  def modifyPassword(id: Long, param: ModifyPasswordParam): Future[Boolean]

  def findList(page: Int, size: Int): Future[AdminPage]

  def findOneById(id: Long): Future[Option[Admin]]

  def loginByEmail(email: String, password: String): Future[Long]

  def create(email: String, password: String): Future[Long]

  def update(payload: Admin): Future[Admin]

  def removeOne(id: Long): Future[Boolean]
}

object AdminService {
  def apply()(implicit ex: ExecutionContext): AdminService = new AdminServiceImpl()
}
