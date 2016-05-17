import javax.servlet.ServletContext

import com.ajug.demo.weka._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new ProductSenseDisambig, "/*")
  }
}
