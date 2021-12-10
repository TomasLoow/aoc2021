package day5

import kotlin.math.absoluteValue
import kotlin.math.max

class VentLine(private val startX: Int, private val startY: Int, private val endX: Int, private val endY: Int) {
    fun isStraight(): Boolean {
        return this.startX == this.endX || this.startY == this.endY
    }

    fun coveredPoints(): List<Coordinate> {

        val dx: Int = endX.compareTo(startX)
        val dy: Int = endY.compareTo(startY)

        val distance: Int = max((startX - endX).absoluteValue, (startY - endY).absoluteValue)

        return buildList {
            var x = startX
            var y = startY
            (0..distance).forEach {
                add(buildCoordinate(x, y))
                x += dx
                y += dy
            }
        }
    }
}