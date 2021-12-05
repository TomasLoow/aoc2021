package day5

import kotlin.math.roundToInt
import kotlin.math.sign

class VentLine(private val startX: Int, private val startY: Int, private val endX: Int, private val endY: Int) {
    fun isStraight(): Boolean {
        return this.startX == this.endX || this.startY == this.endY
    }

    fun coveredPoints(): Set<Pair<Int, Int>> {
        val dx: Int = sign((endX - startX).toDouble()).roundToInt()
        val dy: Int = sign((endY - startY).toDouble()).roundToInt()

        val result: MutableSet<Pair<Int, Int>> = mutableSetOf()
        var x = startX
        var y = startY
        while (x != endX || y != endY) {
            result.add(Pair(x, y))
            x += dx
            y += dy
        }
        result.add(Pair(endX, endY))
        return result

    }
}