package fr.renoux.marwoge.engine

import fr.renoux.marwoge.util.pick

import scala.annotation.tailrec
import scala.util.Random

// TODO improve length distribution
class WordGenerator(
    val index: ChainsIndex,
    val minLength: Int,
    val maxLength: Int // soft max, we still need to stop somewhere possible
) {

  /**   */
  @tailrec
  final def generateWord(excludeExistingWords: Boolean = true, attempts: Int = 1000)(using rand: Random): String = {

    val prefix = rand.pick(index.prefixes)

    var continue = true
    val currentWord = StringBuilder(prefix)

    while (continue) {
      val state = currentWord.takeRight(index.infixSize).toString
      val possibleNextOnes = index.infixes(state)

      if (currentWord.size >= maxLength && possibleNextOnes.contains(None)) {
        continue = false
      } else if (currentWord.size < minLength) {
        if (possibleNextOnes == Seq(None)) {
          continue = false
        } else if (possibleNextOnes.contains(None)) {
          currentWord.append(rand.pick(possibleNextOnes.drop(1)).get)
        } else {
          currentWord.append(rand.pick(possibleNextOnes).get)
        }
      } else {
        rand.pick(possibleNextOnes) match {
          case None =>
            continue = false
          case Some(char) =>
            currentWord.append(char)
        }
      }
    }

    val word = currentWord.toString
    if (word.length < minLength || (excludeExistingWords && index.existingWords.contains(word))) {
      if (attempts >= 0) {
        generateWord(excludeExistingWords, attempts - 1)
      } else {
        throw new IllegalStateException
      }
    } else {
      word
    }

  }
}
