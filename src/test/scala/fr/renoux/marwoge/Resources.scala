package fr.renoux.marwoge

import java.io.InputStream

object Resources {

  def inputStreamFromClasspath(path: String): InputStream =
    getClass.getClassLoader.getResourceAsStream(path)

}