package fr.renoux.marwoge.engine

import scala.annotation.tailrec
import scala.collection.immutable.SortedSet
import scala.collection.mutable


/** Note: because the seqs are sorted, Nones always appear first */
final case class ChainsIndex(
    infixes: Map[String, Seq[Option[Char]]],
    prefixes: Seq[String],
    existingWords: SortedSet[String]
) {
  val infixSize: Int = infixes.keys.head.length
}

object ChainsIndex {

  class Builder(infixLength: Int = 3, prefixLength: Int = 2) {

    val infixes: mutable.Map[String, mutable.Set[Option[Char]]] = mutable.Map()

    val prefixes: mutable.Set[String] = mutable.Set()

    val existingWords: mutable.Set[String] = mutable.Set()

    def index(word: String): Unit = {
      if (word.length < prefixLength) {
        return ()
      }

      val lowercaseWord = word.toLowerCase

      existingWords.add(lowercaseWord)

      val (prefix, rest) = lowercaseWord.splitAt(prefixLength)
      prefixes.add(prefix)
      recIndex(prefix, rest)
    }

    @tailrec
    private def recIndex(state: String, part: String): Unit = {
      val headOption = part.headOption
      infixes.getOrElseUpdate(state, mutable.Set[Option[Char]]()).add(headOption)

      headOption match {
        case None => // nothing left to do
        case Some(head) => recIndex((state + head).takeRight(infixLength), part.drop(1))
      }
    }

    def toIndex: ChainsIndex =
      ChainsIndex(
        infixes.view.mapValues(_.toArray.toSeq.sorted).toMap,
        prefixes.toSeq.sorted,
        SortedSet.from(existingWords)
      )
  }
}