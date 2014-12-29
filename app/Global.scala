import play.api._
import play.api.libs.concurrent.Akka
import akka.actor._
import actors.WeatherRetriever
import actors.RepoRetriever

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    Akka.system(app).actorOf(Props[WeatherRetriever], name = "weather")
    Akka.system(app).actorOf(Props[RepoRetriever], name = "repo")
  }
}
