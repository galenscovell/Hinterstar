package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable.ArrayBuffer


class PartParser {
  private val source: String = "data/parts.json"


  def parseAll(partType: String, rank: String): Array[Part] = {
    // Parse out all Parts from parts.json to an Array
    val parts: ArrayBuffer[Part] = ArrayBuffer()
    val partsJson: JsonValue = new JsonReader().parse(Gdx.files.internal(source))
    val partTypeJson: JsonValue = partsJson.get(partType)
    val partRankJson: JsonValue = partTypeJson.get(rank)

    for (i <- 0 until partRankJson.size) {
      val currentEntry: JsonValue = partRankJson.get(i)
      val part: Part = constructPart(currentEntry, partType)
      parts.append(part)
    }

    parts.toArray
  }

  def parseSingle(partType: String, name: String, rank: String): Part = {
    // Parse out single Part from parts.json
    val partsJson: JsonValue = new JsonReader().parse(Gdx.files.internal(source))
    val partTypeJson: JsonValue = partsJson.get(partType)
    val partRankJson: JsonValue = partTypeJson.get(rank)
    val entry: JsonValue = partRankJson.get(name)
    constructPart(entry, partType)
  }

  def constructPart(entry: JsonValue, partType: String): Part = {
    val name: String = entry.name
    val desc: String = entry.getString("description")
    val stat: Int = entry.getInt("stat")
    val requiredEnergy: Int = entry.getInt("required-energy")
    new Part(name, partType, desc, stat, requiredEnergy)
  }
}
