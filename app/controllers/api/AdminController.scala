package controllers.api

import com.helloscala.common.types.OwnerType
import com.helloscala.common.{HsConstant, OwnerToken, Tools}
import com.helloscala.site.data.Implicits._
import com.helloscala.site.data.domain.{LoginParam, ModifyPasswordParam, RegisterParam}
import com.helloscala.site.data.model.Admin
import com.helloscala.site.service.AdminService
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import tool.{AdminAction, BaseController, WebTools}

object AdminController extends Controller with BaseController {
  val adminService = AdminService()

  def register = Action.async(parse.json.map(_.as[RegisterParam])) { request =>
    val payload = request.body
    adminService.create(payload.account, payload.password).map { ownerId =>
      Ok(Json.obj("id" -> ownerId))
    }
  }

  def findOneFromToken = AdminAction.async { request =>
    adminService.findOneById(request.ownerToken.ownerId).map {
      case Some(bo) => Ok(Json.toJson(bo))
      case None => NotFound
    }
  }

  def findOneById(id: Long) = AdminAction.async {
    adminService.findOneById(id).map {
      case Some(bo) => Ok(Json.toJson(bo))
      case None => NotFound
    }
  }

  def login() = Action.async(parse.json.map(_.as[LoginParam])) { request =>
    val payload = request.body
    adminService.loginByEmail(payload.account, payload.password).map { ownerId =>
      val token = OwnerToken.newBuilder().
        ip(request.remoteAddress).
        ownerId(ownerId).
        ownerType(OwnerType.ADMIN).
        timestamp(Tools.currentTimeSeconds()).
        build()
      val cookie = WebTools.createCookieByToken(token)
      Ok(Json.obj("id" -> ownerId)).withCookies(cookie)
    }
  }

  def logout() = AdminAction { request =>
    val cookie = WebTools.createCookie(HsConstant.OWNER_TOKEN_COOKIE_NAME, "", Some(0))
    Ok.withCookies(cookie).withNewSession
  }

  def update(id: Long) = AdminAction.async(parse.json.map(_.as[Admin])) { request =>
    adminService.update(request.body).map { bo =>
      Ok(Json.toJson(bo))
    }
  }

  def findList(page: Int, size: Int) = AdminAction.async {
    adminService.findList(page, size).map { page =>
      Ok(Json.toJson(page))
    }
  }

  def modifyPassword(id: Long) = AdminAction.async(parse.json.map(_.as[ModifyPasswordParam])) { request =>
    adminService.modifyPassword(id, request.body).map {
      case true => Ok(Json.obj("id" -> id))
      case false => NotFound
    }
  }

}
