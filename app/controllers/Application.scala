package controllers

import javax.inject.Singleton

import play.api.mvc.{Action, Controller}

/**
  * Created by mactur on 04/03/16.
  */
@Singleton
class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Hello"))
  }

}
