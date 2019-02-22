package zap.framework

import java.util.concurrent.ConcurrentHashMap


package object props {

  var customZaperties: Option[ZapProperties] = None

  val zaperties = customZaperties.getOrElse(createDefaultZaperties())

  trait ZapProperties extends ConcurrentHashMap[String, String] {}

  trait PropertyLoader {
    def load(properties: ZapProperties)
  }

  trait NoopPropertyLoader extends PropertyLoader {
    override def load(properties: ZapProperties): Unit = {}
  }

  trait SystemPropertyLoader extends PropertyLoader {
    abstract override def load(properties: ZapProperties): Unit = {
      sys.props.map { t => properties.put(t._1, t._2) }
      super.load(properties)
    }
  }

  trait EnvVarPropertyLoader extends PropertyLoader {
    abstract override def load(properties: ZapProperties): Unit = {
      sys.env.map { t =>
        properties.put(t._1, t._2)
        properties.put(t._1.toLowerCase.replaceAll("_", "."), t._2)
      }
      super.load(properties)
    }
  }

  def createDefaultZaperties(): ZapProperties = {
    val z = new ZapProperties {}
    val l = new NoopPropertyLoader with EnvVarPropertyLoader with SystemPropertyLoader {}
    l.load(z)
    z
  }


}
