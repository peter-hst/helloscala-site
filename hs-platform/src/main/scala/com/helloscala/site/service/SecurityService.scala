package com.helloscala.site.service

import java.net.URLEncoder

import com.helloscala.common.exception.HsInternalException
import com.helloscala.common.util.PasswordUtil
import com.helloscala.common.{HsConstant, TripleDesUtils}
import com.typesafe.scalalogging.StrictLogging

class SecurityService private() extends StrictLogging {
  private val des3 = new TripleDesUtils

  def encrypt(clearText: String): String = {
    try {
      des3.encrypt(clearText)
    } catch {
      case e: Exception =>
        logger.error("3des encrypt fail for clearText: {}", clearText)
        logger.error("encrypt exception", e)
        throw HsInternalException("加密失败")
    }
  }

  def decrypt(encryptedText: String): String = {
    try {
      des3.decrypt(encryptedText)
    }
    catch {
      case e: Exception => {
        logger.error("3des decrypt fail for encryptedText: {}", encryptedText)
        logger.error("decrypt exception", e)
        throw HsInternalException("解密失败")
      }
    }
  }

  def generateHashAndSalt(clearText: String): String = {
    PasswordUtil.hash(clearText)
  }

  def compareWithHashAndSalt(clearText: String, hashText: String): Boolean = {
    PasswordUtil.verify(clearText, hashText)
  }

  def urlEncode(value: String): String = URLEncoder.encode(value, HsConstant.UTF8.name())

}

object SecurityService {
  private val _securityService = new SecurityService

  def apply() = _securityService
}
