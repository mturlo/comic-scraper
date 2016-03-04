package service

import javax.inject.Inject

import com.google.inject.Singleton
import com.typesafe.config.Config
import model.Comic
import net.ruippeixotog.scalascraper.browser.Browser
import play.api.Configuration
import utils.LoggingComponent
import scala.xml._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
/**
  * Created by mactur on 04/03/16.
  */
@Singleton
class ComicService @Inject() (configuration: Configuration) extends LoggingComponent {

  val comicConfigs = configuration.underlying.getConfigList("comics")

  logger.info(s"Got comic configs: $comicConfigs")

  val browser = new Browser

  def getAll: Seq[Comic] = comicConfigs flatMap getComic

  private def getComic(config: Config): Option[Comic] = {

    val name = config.getString("name")

    val rssXML = XML.load(config.getString("rss"))

    (rssXML \\ "item").headOption map { firstItem =>

      logger.debug(s"Got first item on the list: $firstItem")

      val link = (firstItem \ "link").text

      val doc = browser.get(link)

      val imgUrl: String = doc >> attr("src")(config.getString("imgSelector"))

      logger.debug(s"Got image URL: $imgUrl")

      val absoluteImgUrl = {
        if (imgUrl.startsWith("http")) imgUrl
        else (rssXML \ "channel" \ "link").text + imgUrl
      }

      logger.debug(s"Absolute image URL: $absoluteImgUrl")

      Comic(name, absoluteImgUrl)

    }

  }

}
