package tool

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import com.helloscala.common.setting.Settings
import play.api.Play
import play.api.libs.concurrent.Execution
import play.api.mvc.Controller

import scala.concurrent.duration.Duration

trait BaseController {
  this: Controller =>

  implicit def __ec = Execution.Implicits.defaultContext

  implicit def __currentApplication = Play.current

  implicit val duration = Duration(Settings.schedule.duration.toMillis, TimeUnit.MILLISECONDS)
  implicit val timeout = Timeout(duration)

}
