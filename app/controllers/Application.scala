package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import models.Weather

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def weatherToday = Action {
    Ok(Json.toJson(Weather.results(0)))
  }

}
