package aoc2021

import DailyProblem
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max

fun parseLinesFile(path: String): List<VentLine> {
    val lines: List<String> = File(path).readLines()
    return lines.map { line ->
        val (from, to) = line.split(" -> ")
        val (startX, startY) = from.split(",")
        val (endX, endY) = to.split(",")
        VentLine(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())
    }
}

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

private fun List<VentLine>.countIntersections(): Long {
    val array : Array<Int> = Array((VentLine.gridSize + 1) * (VentLine.gridSize + 1)) { 0 }
    forEach { it.markCoveredPoints(array) }
    return array.count { it > 1 }.toLong()
}

class Day5Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 5

    override val name = "Hydrothermal Venture"

    override fun part1(): Long {
        return parseLinesFile(inputFilePath)
            .filter { it.isStraight() }
            .countIntersections()
    }
    override fun part2(): Long {
        return parseLinesFile(this.inputFilePath)
            .countIntersections()
    }
}

val day5Problem = Day5Problem("input/aoc2021/day5.txt")
