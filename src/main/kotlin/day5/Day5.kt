package day5

import DaylyProblem
import java.io.File

fun parseLinesFile(path: String): List<VentLine> {
    val lines: List<String> = File(path).readLines()
    return lines.map { line ->
        line.split(" -> ")
    }.map { coordinatePairs ->
        val start = coordinatePairs[0].split(",").map { num -> num.toInt() }
        val end = coordinatePairs[1].split(",").map { num -> num.toInt() }
        VentLine(start[0], start[1], end[0], end[1])
    }
}


private fun countIntersections(lines: List<VentLine>): Int {
    val counter: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    val pointsWithDuplicates = lines.flatMap { it.coveredPoints() }

    for (point in pointsWithDuplicates) {
        val current = counter.getOrDefault(point, 0)
        counter[point] = current + 1
    }

    return counter.filter { it.value > 1 }.size
}


class Problem(override val inputFilePath: String) : DaylyProblem {
    override val number: Int = 5

    override fun part1(): Int {
        val lines = parseLinesFile(this.inputFilePath)
        val straightLines = lines.filter {
            it.isStraight()
        }
        return countIntersections(straightLines)
    }

    override fun part2(): Int {
        val lines = parseLinesFile(this.inputFilePath)
        return countIntersections(lines)
    }
}

val problem = Problem("input/day5.txt")