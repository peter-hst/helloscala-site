package tool

import com.helloscala.common.setting.Settings
import com.helloscala.common.{HsConstant, OwnerToken}
import com.helloscala.site.service.SecurityService
import com.typesafe.scalalogging.StrictLogging
import play.api.Play
import play.api.Play.current
import play.api.mvc.{Cookie, RequestHeader}

object WebTools extends StrictLogging {
  val cookieSettings = Settings.cookieSettings
  val securityService = SecurityService()

  def getIpFromRequest(implicit request: RequestHeader) =
    request.headers.get(HsConstant.REAL_IP).getOrElse(request.remoteAddress)

  def hasMin(path: String) = {
    if (Play.isDev) path
    else {
      val n = path.lastIndexOf('.')
      path.substring(0, n) + ".min" + path.substring(n)
    }
  }

  def createCookieByToken(token: OwnerToken) =
    createCookie(HsConstant.OWNER_TOKEN_COOKIE_NAME, securityService.encrypt(token.toString), None)

  def createCookie(name: String, value: String, maxAge: Option[Int]) = {
    val cookie = Cookie(name, value, maxAge orElse Some(cookieSettings.maxAge), cookieSettings.path, cookieSettings.domain, httpOnly = cookieSettings.httpOnly)
    logger.debug("create cookie: " + cookie)
    cookie
  }

  /**
   * 从Request中获取token
   * @param request
   * token未找到或超期抛出异常
   * @return
   */
  def getOwnerToken(implicit request: RequestHeader): Option[OwnerToken] = {
    val userTokenStr = request.cookies.get(HsConstant.OWNER_TOKEN_COOKIE_NAME).map(_.value) orElse
      request.headers.get(HsConstant.OWNER_TOKEN_COOKIE_NAME)

    userTokenStr.map { v =>
      val s = securityService.decrypt(v)
      OwnerToken.newBuilder().ownerTokenString(s).build()
    }
  }

}
