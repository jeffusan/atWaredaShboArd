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
      Duration(60, TimeUnit.MINUTES),
      Duration(30, TimeUnit.SECONDS),
      self,
      "tick")

  override def postStop() = tick.cancel()

  def receive = {
    case message:String =>
      val weather = Http("http://api.openweathermap.org/data/2.5/weather").param("q", "Yokohama,jp").asString
      val json: JsValue = Json.parse(weather.body)
      log.info("Json: => {}", json)
      models.Weather1.create(json)
    case _ => log.info("unknown message")
  }


}
