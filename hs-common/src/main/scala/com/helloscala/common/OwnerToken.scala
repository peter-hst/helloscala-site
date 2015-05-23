package com.helloscala.common

import com.helloscala.common.exception.HsUnauthorizedException
import com.helloscala.common.setting.Settings
import com.helloscala.common.types.OwnerType
import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.lang3.StringUtils

/**
 * @param ownerId
 * @param ip
 * @param timestamp 秒
 */
case class OwnerToken private(ownerId: Long, ownerType: OwnerType.Value, timestamp: Long, ip: String) {
  assert(ownerId > 0L)
  assert(ownerType ne null)
  assert(timestamp > 0L)
  assert(StringUtils.isNoneBlank(ip))

  override def toString = {
    ownerId + HsConstant.OWNER_TOKEN_DELIMITER +
      ownerType + HsConstant.OWNER_TOKEN_DELIMITER +
      timestamp + HsConstant.OWNER_TOKEN_DELIMITER +
      ip
  }

  def hasExpired = {
    (timestamp + Settings.cookieSettings.maxAge) < Tools.currentTimeSeconds()
  }

  def isValid = !hasExpired

  def isUser = ownerType == OwnerType.USER

  def isAdmin = ownerType == OwnerType.ADMIN
}

object OwnerToken extends StrictLogging {

  class Builder {
    private var _ownerId: Long = _
    private var _ownerType: OwnerType.Value = _
    private var _timestamp: Long = 0L
    private var _ip: String = _

    def ownerId(v: Long) = {
      _ownerId = v
      this
    }

    def ownerType(v: OwnerType.Value) = {
      _ownerType = v
      this
    }

    def timestamp(t: Long) = {
      _timestamp = t
      this
    }

    def ip(v: String) = {
      _ip = v
      this
    }

    def ownerTokenString(v: String) = {
      v.split(HsConstant.OWNER_TOKEN_DELIMITER) match {
        case Array(userId, ownerType, timestamp, ip) =>
          _ownerId = userId.toLong
          _ownerType = OwnerType.withName(ownerType)
          _timestamp = timestamp.toLong
          _ip = ip

        case _ =>
          logger.error("token format error")
          throw HsUnauthorizedException("Invalid helloscala owner token")
      }

      this
    }

    def ownerToken(v: OwnerToken) = {
      ownerId(v.ownerId).ownerType(v.ownerType).timestamp(Tools.currentTimeSeconds()).ip(v.ip)
      this
    }

    def build() = {
      OwnerToken(_ownerId, _ownerType, _timestamp, _ip)
    }
  }

  def newBuilder(): Builder = new Builder()

}
