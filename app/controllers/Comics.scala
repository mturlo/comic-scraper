package controllers

import javax.inject.Singleton

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import service.ComicService

/**
  * Created by mactur on 04/03/16.
  */
@Singleton
class Comics @Inject() (comicService: ComicService) extends Controller {

  def list = Action {
    Ok(views.html.comics(comicService.getAll))
  }

}
