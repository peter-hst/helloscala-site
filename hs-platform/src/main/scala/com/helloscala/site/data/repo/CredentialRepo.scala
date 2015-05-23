package com.helloscala.site.data.repo

import com.helloscala.common.HsConstant
import com.helloscala.common.exception.HsUnauthorizedException
import com.helloscala.common.util.PasswordUtil
import com.helloscala.site.data.driver.MyDriver.api._
import com.helloscala.site.data.model.Credential
import com.helloscala.site.data.model.Schemas._
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

class CredentialRepo()(implicit ec: ExecutionContext) extends StrictLogging {
  def existsByMobile(mobile: String): Future[Boolean] = {
    db.run(tCredential.filter(_.email === mobile).length.result).map(_ == 1)
  }

  def findOneById(id: Long): Future[Option[Credential]] = {
    val action = tCredential.filter(_.id === id).result
    db.run(action).map(_.headOption)
  }

  /**
   *
   * @param email
   * @param password
   * @return ID
   */
  def loginByEmail(email: String, password: String): Future[Long] = {
    val f = db.run(tCredential.filter(_.email === email).result).map(_.headOption)
    validatePassword(f, password, email)
  }

  /**
   *
   * @param mobile
   * @param password
   * @return ID
   */
  def loginByMobile(mobile: String, password: String): Future[Long] = {
    val f = db.run(tCredential.filter(_.email === mobile).result).map(_.headOption)
    validatePassword(f, password, mobile)
  }

  /**
   *
   * @param id
   * @param nowPassword
   * @param newPassword
   * @return 返回修改记录行数
   */
  def updatePassword(id: Long, nowPassword: String, newPassword: String): Future[Int] = {
    val q = tCredential.filter(_.id === id)
    val credentialFuture: Future[Option[Credential]] = db.run(q.result).map(_.headOption)

    credentialFuture.flatMap {
      case Some(credential) =>
        if (matchPassword(credential, nowPassword)) {
          val salt = new String(PasswordUtil.salt(HsConstant.SALT_SIZE))
          val password = PasswordUtil.hash(salt + newPassword)
          val action = q.map(t => (t.salt, t.password)).update(salt -> password).transactionally
          db.run(action)
        } else {
          throw HsUnauthorizedException("密码不匹配")
        }

      case None =>
        Future.successful(0)
    }
  }

  private def validatePassword(f: Future[Option[Credential]], password: String, name: String): Future[Long] = {
    f.map {
      case Some(credential) =>
        if (matchPassword(credential, password)) credential.id.get
        else throw HsUnauthorizedException("密码不匹配")

      case None =>
        throw HsUnauthorizedException(s"$name 用户不存在")
    }
  }

  @inline
  private def matchPassword(credential: Credential, password: String): Boolean =
    PasswordUtil.hash(credential.salt + password) == credential.password
}
