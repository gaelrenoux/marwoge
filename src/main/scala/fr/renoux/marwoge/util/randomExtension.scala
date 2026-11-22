package fr.renoux.marwoge.util

import scala.util.Random

extension (rand: Random) {
  
  /** Picks randomly an element in a Seq */
  inline def pick[A](s: collection.Seq[A]): A = {
    if (s.isEmpty) throw new IndexOutOfBoundsException()
    val i = rand.nextInt(s.size)
    s(i)
  }
}
