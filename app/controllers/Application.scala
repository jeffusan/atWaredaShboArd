package controllers

import play.api.mvc.{Action, Controller}
import models.Weather
import play.api.libs.json.Json

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def weatherToday = Action {
    Ok(Json.toJson(Weather.latest))
  }

}
