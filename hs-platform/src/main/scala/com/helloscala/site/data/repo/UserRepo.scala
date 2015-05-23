package com.helloscala.site.data.repo

import java.sql.SQLException
import java.time.LocalDateTime

import com.helloscala.common.exception.HsUnauthorizedException
import com.helloscala.common.types.OwnerType
import com.helloscala.common.util.PasswordUtil
import com.helloscala.common.{HsConstant, Tools}
import com.helloscala.site.data.domain.RegisterParam
import com.helloscala.site.data.driver.MyDriver.api._
import com.helloscala.site.data.model.Schemas._
import com.helloscala.site.data.model.{Credential, User}

import scala.concurrent.{ExecutionContext, Future}

class UserRepo()(implicit ec: ExecutionContext) {
  def findList(page: Int, size: Int): Future[(Seq[User], Int)] = {
    val action = for {
      items <- tUser.sortBy(_.id.desc).drop(Tools.pageOffset(page, size)).take(size).result
      total <- tUser.length.result
    } yield Tuple2(items, total)
    db.run(action)
  }

  def update(bean: User): Future[Int] = {
    val action = tUser.filter(_.id === bean.id).update(bean).transactionally
    db.run(action)
  }

  def create(payload: RegisterParam): Future[Long] = {
    val now = LocalDateTime.now()
    val salt = new String(PasswordUtil.salt(HsConstant.SALT_SIZE))
    val password = PasswordUtil.hash(salt + payload.password)
    val credential = Credential(None, Some(payload.account), salt, password, OwnerType.USER, now)

    val action = (for {
      ownerId <- (tCredential returning tCredential.map(_.id)) += credential
      _ <- tUser += User(ownerId.get, None, None, None, None, None, None, None, None, now)
    } yield ownerId.get).transactionally

    db.run(action).transform(ownerId => ownerId, {
      case e: SQLException => HsUnauthorizedException(s"账号：${payload.account} 已存在")
      case e: Throwable => e
    })
  }

  def findOneById(id: Long): Future[Option[User]] = {
    val action = tUser.filter(_.id === id).result
    db.run(action).map(_.headOption)
  }

  def findOneFullById(id: Long): Future[Option[User]] = {
    val action = for {
      users <- tUser.filter(_.id === id).result
    } yield users

    db.run(action).map(_.headOption)
  }

}
