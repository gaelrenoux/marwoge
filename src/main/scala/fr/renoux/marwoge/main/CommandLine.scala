package fr.renoux.marwoge.main

import fr.renoux.gaston.BuildInfo
import scopt.Read

import java.nio.file.{Path, Paths}
import scala.util.Random

/** Define command-line arguments, using Scopt. */
final case class CommandLine(
    inputFile: Path = Path.of(""),
    generationCount: Int = 0,
    capitalize: Boolean = false,
    minGeneratedLength: Option[Int] = None,
    maxGeneratedLength: Option[Int] = None,
    minGeneratedLengthMultiplier: Double = 1,
    maxGeneratedLengthMultiplier: Double = 1,
    infixLength: Int = 3,
    prefixLength: Int = 2,
    debug: Boolean = false,
    seed: Long = Random.nextLong(),
)

object CommandLine {

  private given Read[Path] = Read.reads(Paths.get(_))

  private val parser = new scopt.OptionParser[CommandLine]("marwoge") {
    head("marwoge", BuildInfo.Version)

    arg[Path]("<file>")
        .action { (path, in) => in.copy(inputFile = path) }
        .text("Input file")

    arg[Int]("<count>")
        .action { (count, in) => in.copy(generationCount = count) }
        .text("How many words to generate")

    opt[Unit]('C', "capitalize").optional()
        .action((_, in) => in.copy(capitalize = true))
        .text("Capitalize the first letter of each word")

    opt[Int]('m', "min-length").optional().valueName("<length>")
        .action((length, in) => in.copy(minGeneratedLength = Some(length)))
        .text("Minimum length of generated words (has priority over min-length-multiplier)")

    opt[Int]('x', "max-length").optional().valueName("<length>")
        .action((length, in) => in.copy(maxGeneratedLength = Some(length)))
        .text("Maximum length of generated words (has priority over max-length-multiplier)")

    opt[Double]('M', "min-length-multiplier").optional().valueName("<multiplier>")
        .action((multiplier, in) => in.copy(minGeneratedLengthMultiplier = multiplier))
        .text("Multiplier on the found value for the minimum length of generated words (default is 1)")

    opt[Double]('X', "max-length-multiplier").optional().valueName("<multiplier>")
        .action((multiplier, in) => in.copy(maxGeneratedLengthMultiplier = multiplier))
        .text("Multiplier on the found value for the maximum length of generated words (default is 1)")

    opt[Int]("prefix-length").optional().valueName("<length>")
        .action((length, in) => in.copy(prefixLength = length))
        .text("Length to use for prefixes in the Markov index (ie, the length of the starting state)")

    opt[Int]("infix-length").optional().valueName("<length>")
        .action((length, in) => in.copy(infixLength = length))
        .text("Length to use for infixes in the Markov index (ie, the max length of the state)")

    opt[Unit]("debug").optional()
        .action((_, in) => in.copy(debug = true))
        .text("Log debug messages")

    opt[Long]("seed").optional().valueName("<seed>")
        .action((s, in) => in.copy(seed = s))
        .text("Seed to use for randomization")

    help("help").text("Prints this usage text.")
  }

  def parse(args: Seq[String]): CommandLine = {
    parser.parse(args, CommandLine()) match {
      case Some(input) => input
      case None => throw new IllegalArgumentException
    }
  }

}

