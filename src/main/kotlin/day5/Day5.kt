package day5

import DailyProblem
import java.io.File

fun parseLinesFile(path: String): List<VentLine> {
    val lineRegex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

    val lines: List<String> = File(path).readLines()
    return lines.map { line ->
        val matchResult = lineRegex.find(line)
        val (startX, startY, endX, endY) = matchResult!!.destructured
        VentLine(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())
    }
}


private fun List<VentLine>.countIntersections(): Long {
    val counter: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    val pointsWithDuplicates = flatMap { it.coveredPoints() }

    for (point in pointsWithDuplicates) {
        counter[point] = counter.getOrDefault(point, 0) + 1
    }

    return counter
        .filter { it.value > 1 }
        .size
        .toLong()
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