package day5

import kotlin.math.absoluteValue
import kotlin.math.max

class VentLine(private val startX: Int, private val startY: Int, private val endX: Int, private val endY: Int) {

    companion object {
        var gridSize: Int = 0
    }

    init {
        val biggest = listOf(startX,startY,endX,endY).maxOf { it }
        if ( biggest > gridSize) {
            gridSize = biggest
        }
    }

    fun isStraight(): Boolean {
        return this.startX == this.endX || this.startY == this.endY
    }

    private fun coordinatesToIdx(x: Int, y: Int): Int {
        return x + y* gridSize
    }

    fun markCoveredPoints(arr : Array<Int>) {

        val dx: Int = endX.compareTo(startX)
        val dy: Int = endY.compareTo(startY)
        val distance: Int = max((startX - endX).absoluteValue, (startY - endY).absoluteValue)


        val d = dx+dy* gridSize
        var idx = coordinatesToIdx(startX,startY)
        (0..distance).forEach {
            arr[idx]++
            idx += d
        }
    }
}