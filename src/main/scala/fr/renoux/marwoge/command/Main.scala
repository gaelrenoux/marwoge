package fr.renoux.marwoge.command

import ch.qos.logback.classic.{Level, LoggerContext}
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

import scala.collection.immutable.ArraySeq

object Main {
  private val log = Logger("Main")

  /** Main method, called from the command line. */
  def main(args: Array[String]): Unit = {

    val commandLine: CommandLine = CommandLine.parse(ArraySeq.unsafeWrapArray(args))
    if (commandLine.debug) {
      setDebugLogLevel()
    }
    log.info(s"Commande line is: $commandLine")

    
  }

  /** Set the log level to debuq */
  private def setDebugLogLevel(): Unit = {
    LoggerFactory.getILoggerFactory match {
      case ctx: LoggerContext => ctx.getLogger("fr.renoux").setLevel(Level.DEBUG)
      case _ => println("Cannot set logger level to DEBUG")
    }
  }

}
