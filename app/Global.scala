import play.api._
import play.api.libs.concurrent.Akka
import akka.actor._
import actors.actors.WeatherRetriever

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    Akka.system(app).actorOf(Props[WeatherRetriever], name = "weather")

  }
}
