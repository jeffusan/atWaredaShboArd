package controllers

import play.api.mvc.{Action, Controller}
import models.Weather
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.Date

object Application extends Controller {


  def index = Action {
    Ok(views.html.index())
  }

  def weather = Action {
    Ok(views.html.weather())
  }

  def weatherToday = Action {
    Ok(Json.toJson(models.Weather1.latest))
  }

  def weatherDetail = Action {
    Ok(Json.toJson(models.Weather1.list))
  }

}
