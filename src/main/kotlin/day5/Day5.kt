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

typealias Coordinate = Int  // Dumb encoding to make Sets of coordinates work a lot faster.

fun buildCoordinate(x: Int, y: Int) = 10000 * x + y

private fun List<VentLine>.countIntersections(): Long {
    val pointsWithDuplicates =
        flatMap { it.coveredPoints() }.sorted()  // Actual order not important, we just want identical to be adjacent.
    return pointsWithDuplicates
        .windowed(2)
        .filter { it.first() == it.last() }
        .map { it.first() }  // Keep only those who are identical to it's successor in the list
        .toSet().size.toLong()  // Count em up
}

class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 5

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
