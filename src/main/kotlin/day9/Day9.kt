package day9

import DailyProblem
import java.io.File


typealias Coordinate = Pair<Int, Int>

private fun parseMap(path: String): SeaFloor {
    return SeaFloor(File(path)
        .readLines()
        .map { line ->
            line.toCharArray().map { it.digitToIntOrNull()!! }.toTypedArray()
        }
        .toTypedArray())
}

private fun Collection<Int>.product(): Int {
    return reduce { acc, i -> acc * i }
}


class Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 9

    override fun part1(): Long {
        val seaFloor = parseMap(inputFilePath)
        return seaFloor
            .findLowestPoints()
            .sumOf { seaFloor.valueAt(it) + 1 }
            .toLong()
    }

    override fun part2(): Long {
        val seaFloor = parseMap(inputFilePath)
        val lowestPoints = seaFloor
            .findLowestPoints()

        return lowestPoints
            .map { p ->
                seaFloor.sizeOfBasinAt(p)
            }
            .sortedDescending()
            .take(3)
            .product()
            .toLong()
    }
}

val problem = Problem("input/day9.txt")
