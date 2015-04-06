package controllers

import play.api.Play.current
import play.api.mvc._

object OAuths extends Controller {

  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  def authorization(social: String, state: String) = Action { implicit request =>
    Redirect("")
  }

  def qq = Action { request =>
    Ok("")
  }

  def weibo = Action { request => 
    Ok("")
  }

  def weiboCancel = Action { request =>
    Ok("")
  }
}

