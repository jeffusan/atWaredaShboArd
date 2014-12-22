package actors

import akka.actor._
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scalaj.http.Http
import play.api.libs.json._

class WeatherRetriever extends Actor with ActorLogging {

  import context.dispatcher
  val tick =
    context.system.scheduler.schedule(
      Duration(5, TimeUnit.SECONDS),
      Duration(10, TimeUnit.SECONDS),
      self,
      "tick")

  override def postStop() = tick.cancel()

  def receive = {
    case message:String =>
      val weather = Http("http://api.openweathermap.org/data/2.5/weather").param("q", "Yokohama,jp").asString
      val json: JsValue = Json.parse(weather.body)
      log.info("Json: => {}", json)
      val maybeName = (json \ "main" \ "temp").asOpt[Double]
      log.info("Weather: => {}", maybeName.getOrElse(0.00))
      models.Weather1.create(maybeName.getOrElse(0.00).toString())
    case _ => log.info("unknown message")
  }

}
