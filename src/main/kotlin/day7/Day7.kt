package day7

import DailyProblem
import java.io.File
import kotlin.math.absoluteValue

fun parseIntsOnOneLineFile(path: String): List<Long> {
    return File(path)
        .readLines()
        .single()
        .split(",")
        .map{ it.toLong()}
}

enum class MoveCost {
    Linear,
    Triangular
}

private fun List<Long>.findBestPosition(costType: MoveCost): Long {
    val lb = this.minOrNull()
    val ub = this.maxOrNull()
    val res = (lb!!..ub!!)
        .map {
            positionCandidate -> this.findPositionScore(positionCandidate, costType)
        }
        .windowed(2) // We can stop looking as soon as we hit a local minimum
        .takeWhile { window ->
            val (cost1, cost2) = window
            cost1 > cost2
        }.last()[1]

    return res
}

private fun List<Long>.findPositionScore(pos: Long, costType: MoveCost) : Long {
    return when (costType) {
        MoveCost.Linear ->
            sumOf { crabPosition ->
                (crabPosition - pos).absoluteValue
            }
        MoveCost.Triangular ->
            sumOf { crabPosition ->
                // Use the formula for arithmetic sum. 1+2+...+n = n*(n+1)/2
                val distance = (crabPosition - pos).absoluteValue
                distance * (distance + 1) / 2
            }
    }

}

class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 7

    override fun part1(): Long {
        val crabPositions = parseIntsOnOneLineFile(inputFilePath)
        return crabPositions.findBestPosition(MoveCost.Linear)
    }

    override fun part2(): Long {
        val crabPositions = parseIntsOnOneLineFile(inputFilePath)
        return crabPositions.findBestPosition(MoveCost.Triangular)
    }
}

val problem = Problem("input/day7.txt")

fun main() {
    Problem("input/day7.txt").run()
}