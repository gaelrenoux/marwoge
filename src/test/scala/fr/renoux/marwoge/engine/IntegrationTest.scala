package fr.renoux.marwoge.engine

import fr.renoux.marwoge.io.WordReader
import fr.renoux.marwoge.{Resources, TestBase}

import scala.util.Random

class IntegrationTest extends TestBase {

  def printNamesWith(file: String) = {
    val io = Resources.inputStreamFromClasspath(file)
    val builder = ChainsIndex.Builder()
    try {
      WordReader.consume(io)(builder.index)
    } finally {
      io.close()
    }

    given Random = Random(0)
    val index = builder.toIndex
    val generator = WordGenerator(index, 3, 16)

    (0 until 100).foreach { _ =>
      println(generator.generateWord().capitalize)
    }
  }

  "japanese" - {
    "male names" in {
      printNamesWith("japanese-male-names.txt")
    }
  }

  "cuban" - {
    "male names" in {
      printNamesWith("cuban-male-names.txt")
    }
  }
}
