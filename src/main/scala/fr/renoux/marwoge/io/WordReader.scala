package fr.renoux.marwoge.io

import java.io.{BufferedReader, InputStream, InputStreamReader}

object WordReader {

  def consume(io: InputStream)(action: String => Unit): Unit = {
    val reader = new BufferedReader(new InputStreamReader(io, java.nio.charset.StandardCharsets.UTF_8))

    try {
      val word = StringBuilder()
      var c = reader.read()
      while (c != -1) {
        if (Character.isWhitespace(c)) {
          if (word.nonEmpty) {
            action(word.toString)
            word.clear()
          }
        } else {
          word.append(c.asInstanceOf[Char])
        }
        c = reader.read()
      }

    } finally {
      reader.close()
    }

  }
}
