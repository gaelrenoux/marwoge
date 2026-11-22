package fr.renoux.marwoge.engine

import fr.renoux.marwoge.io.WordReader
import fr.renoux.marwoge.{Resources, TestBase}

import scala.util.Random

class IntegrationTest extends TestBase {

  def printNamesWith(file: String, infixLength: Int = 3) = {
    val io = Resources.inputStreamFromClasspath(file)
    val builder = ChainsIndex.Builder(infixLength = infixLength)
    try {
      WordReader.consume(io)(builder.index)
    } finally {
      io.close()
    }

    given Random = Random(0)
    val index = builder.toIndex
    val generator = WordGenerator(index, 3, 10)
    println(file)
    (0 until 100).foreach { _ =>
      print(generator.generateWord().capitalize)
      print(" ")
    }
    println()
  }

  "japanese" - {
    "male names" in {
      printNamesWith("japanese-male-names.txt")
    }
    "female names" in {
      printNamesWith("japanese-female-names.txt", infixLength = 3)
    }
    "family names" in {
      printNamesWith("japanese-family-names.txt", infixLength = 3)
    }
  }

  "spanish" - {
    "male names" in {
      printNamesWith("spanish-male-names.txt", infixLength = 3)
    }
    "female names" in {
      printNamesWith("spanish-female-names.txt", infixLength = 3)
    }
    "family names" in {
      printNamesWith("spanish-family-names.txt", infixLength = 3)
    }
  }

  "french" - {
    "male names" in {
      printNamesWith("french-male-names.txt", infixLength = 3)
    }
    "female names" in {
      printNamesWith("french-female-names.txt", infixLength = 3)
    }
    "family names" in {
      printNamesWith("french-family-names.txt", infixLength = 3)
    }
  }

  "russian" - {
    "male names" in {
      printNamesWith("russian-male-names.txt", infixLength = 3)
    }
    "male names v2" in {
      printNamesWith("russian-male-names-old.txt", infixLength = 3)
    }
    "female names" in {
      printNamesWith("russian-female-names.txt", infixLength = 3)
    }
    "family names" in {
      printNamesWith("russian-family-names.txt", infixLength = 3)
    }
  }
}
