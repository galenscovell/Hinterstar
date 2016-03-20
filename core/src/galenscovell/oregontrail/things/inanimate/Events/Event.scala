package galenscovell.oregontrail.things.inanimate.Events


trait Event {
  def start
  def end
  def setDistanceTo(distance: Int)
  def getDistanceTo(distance: Int): Int
}
