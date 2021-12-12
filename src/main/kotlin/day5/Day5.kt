package day5

import DailyProblem
import java.io.File

fun parseLinesFile(path: String): List<VentLine> {
    val lines: List<String> = File(path).readLines()
    return lines.map { line ->
        val (from, to) = line.split(" -> ")
        val (startX, startY) = from.split(",")
        val (endX, endY) = to.split(",")
        VentLine(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())
    }
}

private fun List<VentLine>.countIntersections(): Long {
    val array : Array<Int> = Array((VentLine.gridSize + 1) * (VentLine.gridSize + 1)) { 0 }
    forEach { it.markCoveredPoints(array) }
    return array.count { it > 1 }.toLong()
}

class Problem(override val inputFilePath: String) : DailyProblem {
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


val problem = Problem("input/day5.txt")
