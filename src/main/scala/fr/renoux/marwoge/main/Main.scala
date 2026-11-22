package fr.renoux.marwoge.main

import ch.qos.logback.classic.{Level, LoggerContext}
import fr.renoux.marwoge.engine.{ChainsIndex, WordGenerator}
import fr.renoux.marwoge.io.WordReader
import org.slf4j.LoggerFactory

import java.io.FileInputStream
import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.Random

object Main {

  /** Main method, called from the command line. */
  def main(args: Array[String]): Unit = {

    val commandLine: CommandLine = CommandLine.parse(ArraySeq.unsafeWrapArray(args))
    if (commandLine.debug) {
      setDebugLogLevel()
    }

    run(commandLine).foreach(println)

  }

  def run(commandLine: CommandLine): Seq[String] = {
    val indexBuilder = ChainsIndex.Builder(infixLength = commandLine.infixLength, prefixLength = commandLine.prefixLength)

    val io = new FileInputStream(commandLine.inputFile.toFile)
    try {
      WordReader.consume(io)(indexBuilder.index)
    } finally io.close()

    val index = indexBuilder.toIndex

    val minLength = commandLine.minGeneratedLength match {
      case None => math.round(commandLine.minGeneratedLengthMultiplier * index.minWordLength).toInt
      case Some(length) => length
    }

    val maxLength = commandLine.maxGeneratedLength match {
      case None => math.round(commandLine.maxGeneratedLengthMultiplier * index.maxWordLength).toInt
      case Some(length) => length
    }

    val generator = WordGenerator(index, minLength = minLength, maxLength = maxLength)

    given Random = Random(commandLine.seed)
    val alreadyGenerated = mutable.Set[String]()

    Seq.fill[String](commandLine.generationCount) {
      var retries = 1000
      var word = generator.generateWord()
      while (
        retries > 0 &&
            (word.length > 1.5 * maxLength || alreadyGenerated.contains(word))
      ) {
        word = generator.generateWord()
        retries -= 1
      }
      if (retries == 0) ""
      else {
        alreadyGenerated.add(word)
        if (commandLine.capitalize) word.capitalize
        else word
      }
    }
  }

  /** Set the log level to debuq */
  private def setDebugLogLevel(): Unit = {
    LoggerFactory.getILoggerFactory match {
      case ctx: LoggerContext => ctx.getLogger("fr.renoux").setLevel(Level.DEBUG)
      case _ => println("Cannot set logger level to DEBUG")
    }
  }

}
