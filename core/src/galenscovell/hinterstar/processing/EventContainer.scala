package galenscovell.hinterstar.processing

import scala.collection.mutable.{ArrayBuffer, Map}


class EventContainer {
  var name: String = ""
  var description: String = ""
  var choices: ArrayBuffer[Map[String, String]] = ArrayBuffer()
}
