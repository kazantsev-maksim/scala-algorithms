import sbt._

object Dependencies {

  private object Version {
    val Jmh = "1.37"
  }

  object Benchmarks {
    private val jmh = "org.openjdk.jmh" % "jmh-core" % Version.Jmh

    val all: Seq[ModuleID] = Seq(jmh)
  }
}
