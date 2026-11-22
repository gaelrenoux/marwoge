package fr.renoux.marwoge.engine

import fr.renoux.marwoge.io.WordReader
import fr.renoux.marwoge.{Resources, TestBase}

class ChainsIndexTest extends TestBase {

  "Simple test" in {
    val io = Resources.inputStreamFromClasspath("japanese-male-names.txt")
    val builder = ChainsIndex.Builder()
    try {
      WordReader.consume(io)(builder.index)
    } finally {
      io.close()
    }

    val index = builder.toIndex
    index.infixSize should be(3)
    index.prefixes.size should be(37)

    println(index.existingWords.mkString("Existing: ", " ", ""))
    println(index.prefixes.mkString("Prefixes: ", " ", ""))

    index.infixes.foreach { (prefix, values) =>
      val valuesAsString = values.map {
        case None => "-"
        case Some(char) => char.toString
      }
      println(s"$prefix: ${valuesAsString.mkString(" ")}")
    }
  }


  "Accentuated characters" in {
    val io = Resources.inputStreamFromClasspath("spanish-male-names.txt")
    val builder = ChainsIndex.Builder(infixLength = 2)
    try {
      WordReader.consume(io)(builder.index)
    } finally {
      io.close()
    }

    val index = builder.toIndex
    index.infixSize should be(2)
    index.prefixes.size should be(142)

    println(index.existingWords.mkString("Existing: ", " ", ""))
    println(index.prefixes.mkString("Prefixes: ", " ", ""))

    index.infixes.foreach { (prefix, values) =>
      val valuesAsString = values.map {
        case None => "-"
        case Some(char) => char.toString
      }
      println(s"$prefix: ${valuesAsString.mkString(" ")}")
    }
  }
}
