package controllers.api

import com.helloscala.common.exception.HsUnauthorizedException
import com.helloscala.common.types.OwnerType
import com.helloscala.common.{HsConstant, OwnerToken, Tools}
import com.helloscala.site.data.Implicits._
import com.helloscala.site.data.domain.{LoginParam, RegisterParam}
import com.helloscala.site.data.model.User
import com.helloscala.site.service.{CaptchaService, UserService}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import tool.{BaseController, TokenAction, WebTools}

import scala.concurrent.Future

object UserController extends Controller with BaseController {
  val userService = UserService()
  val captchaService = CaptchaService()

  def login = Action.async(parse.json.map(_.as[LoginParam])) { implicit request =>
    request.session.get(HsConstant.CAPTCHA_KEY) match {
      case Some(captcha) if captcha.equalsIgnoreCase(request.body.captcha) =>
        userService.loginWithEmail(request.body).map { ownerId =>
          val token = OwnerToken.newBuilder().
            ip(request.remoteAddress).
            ownerId(ownerId).
            ownerType(OwnerType.USER).
            timestamp(Tools.currentTimeSeconds()).
            build()
          val cookie = WebTools.createCookieByToken(token)
          Ok(Json.obj("id" -> ownerId)).withCookies(cookie).withNewSession
        }

      case _ =>
        Future.successful(Unauthorized(Json.toJson(HsUnauthorizedException("验证码不正确"))))
    }
  }

  def logout = TokenAction {
    val cookie = WebTools.createCookie(HsConstant.OWNER_TOKEN_COOKIE_NAME, "", Some(0))
    Ok.withCookies(cookie).withNewSession
  }

  def register = Action.async(parse.json.map(_.as[RegisterParam])) { request =>
    userService.registerWithEmail(request.body).map { bo =>
      Ok(Json.toJson(bo))
    }
  }

  def findOneById(id: Long) = TokenAction.async { request =>
    userService.findOneById(id).map {
      case Some(bo) => Ok(Json.toJson(bo))
      case None => Unauthorized
    }
  }

  def createCaptcha = Action {
    val (token, bytes) = captchaService.createCaptcha()
    Ok(bytes).
      withSession(HsConstant.CAPTCHA_KEY -> token).
      withHeaders("Content-Type" -> "image/png", "Cache-Control" -> "no-cache, no-store", "Pragma" -> "no-cache")
  }


  def update(id: Long) = TokenAction.async(parse.json.map(_.as[User])) { request =>
    userService.update(request.body).map { bo =>
      Ok(Json.toJson(bo))
    }
  }

  def findList(page: Int,
               size: Int) = TokenAction.async {
    userService.findList(page, size).map(bo => Ok(Json.toJson(bo)))
  }

}
