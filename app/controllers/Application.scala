package controllers

import play.api.mvc.{Action, Controller}
import models.Weather
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Application extends Controller {

  implicit val weatherWrites: Writes[Weather] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "temperature").write[Double] and
      (JsPath \ "humidity").write[Int] and
      (JsPath \ "pressure").write[Int] and
      (JsPath \ "cloudsPercent").write[Int]
  )(unlift(Weather.unapply))


  def index = Action {
    Ok(views.html.index())
  }

  def weatherToday = Action {
    Ok(Json.toJson(models.Weather1.latest))
  }

}
