package com.helloscala.site.data.repo

import java.time.LocalDateTime

import com.helloscala.common.HsConstant
import com.helloscala.common.types.OwnerType
import com.helloscala.common.util.PasswordUtil
import com.helloscala.site.data.driver.MyDriver.api._
import com.helloscala.site.data.model.Schemas._
import com.helloscala.site.data.model.{Admin, Credential}

import scala.concurrent.{ExecutionContext, Future}

class AdminRepo()(implicit ec: ExecutionContext) {
  def findList(page: Int, size: Int): Future[(Seq[Admin], Int)] = {
    val q = tAdmin
    val action = for {
      items <- q.sortBy(_.id.desc).result
      total <- q.length.result
    } yield (items, total)
    db.run(action)
  }

  def findOneById(id: Long): Future[Option[Admin]] = {
    db.run(tAdmin.filter(_.id === id).result).map(_.headOption)
  }

  def update(bean: Admin): Future[Int] = {
    val q = tAdmin.filter(_.id === bean.id).update(bean)
    db.run(q.transactionally)
  }

  def removeOneById(id: Long): Future[Int] = {
    val q = tAdmin.filter(_.id === id).delete
    db.run(q.transactionally)
  }

  def create(email: String, pwd: String): Future[Long] = {
    val salt = new String(PasswordUtil.salt(HsConstant.SALT_SIZE))
    val password = PasswordUtil.hash(salt + pwd)
    val credential = Credential(None, Some(email), salt, password, OwnerType.ADMIN, LocalDateTime.now)
    val createCredential = tCredential returning tCredential.map(_.id) += credential

    val q = for {
      ownerId <- createCredential
      _ <- tAdmin += Admin(ownerId.get, None, credential.createdAt)
    } yield ownerId.get

    db.run(q.transactionally)
  }

}
