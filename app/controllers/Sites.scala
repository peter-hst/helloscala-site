package controllers

import play.api.Play.current
import play.api.mvc._

object Sites extends Controller {

  implicit val defaultContext = play.api.libs.concurrent.Execution.Implicits.defaultContext

  def index = Action {
    Ok(views.html.index())
  }
}

