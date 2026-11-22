package fr.renoux.marwoge.main

import fr.renoux.marwoge.TestBase

import java.nio.file.Paths

class IntegrationTest extends TestBase {

  def printNames(file: String, infixLength: Int = 3): Unit = {
    val command = CommandLine(
      inputFile = Paths.get(getClass.getClassLoader.getResource(file).toURI),
      capitalize = true,
      seed = 0,
      infixLength = infixLength,
      generationCount = 300,
      minGeneratedLength = Some(3),
      maxGeneratedLength = Some(12)
    )
    val result = Main.run(command)
    println(file)
    println(result.mkString(" "))

    println()
  }

  "japanese" - {
    "male names" in {
      printNames("japanese-male-names.txt")
    }
    "female names" in {
      printNames("japanese-female-names.txt")
    }
    "family names" in {
      printNames("japanese-family-names.txt")
    }
  }

  "spanish" - {
    "male names" in {
      printNames("spanish-male-names.txt")
    }
    "female names" in {
      printNames("spanish-female-names.txt")
    }
    "family names" in {
      printNames("spanish-family-names.txt")
    }
  }

  "french" - {
    "male names" in {
      printNames("french-male-names.txt")
    }
    "female names" in {
      printNames("french-female-names.txt")
    }
    "family names" in {
      printNames("french-family-names.txt")
    }
  }

  "russian" - {
    "male names" in {
      printNames("russian-male-names.txt")
    }
    "female names" in {
      printNames("russian-female-names.txt")
    }
    "family names" in {
      printNames("russian-family-names.txt")
    }
  }

  "bantu" - {
    "male names" in {
      printNames("bantu-male-names.txt")
    }
    "female names" in {
      printNames("bantu-female-names.txt")
    }
    "family names" in {
      printNames("bantu-family-names.txt")
    }
  }

  "latin" - {
    "masculine words" in {
      printNames("latin-masculine-words.txt")
    }
    "feminine words" in {
      printNames("latin-feminine-words.txt")
    }
  }
}
