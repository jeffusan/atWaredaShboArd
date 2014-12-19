package test

import controllers.Application

import org.specs2.mutable._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  "Application" should {

    "add a bar" in new WithApplication {
      val addBar = Application.addBar()(FakeRequest(POST, "/").withBody(Json.parse("""{"name": "foo"}""")))
      status(addBar) must equalTo(OK)
      Json.parse(contentAsString(addBar)).as[Bar].name must beEqualTo ("foo")
    }
  }
}
