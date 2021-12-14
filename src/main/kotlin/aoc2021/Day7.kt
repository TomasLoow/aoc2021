package aoc2021

import DailyProblem
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.min

typealias CrabPositions = List<Long>

fun parseIntsOnOneLineFile(path: String): CrabPositions {
    return File(path)
        .readLines()
        .single()
        .split(",")
        .map { it.toLong() }
}

private fun CrabPositions.findBestPosition(): Long {
    /*
    We know that the value as a function of a position is a convex function. So we can solve with a
    kind of binary search where we divide the range in half and looks at *two* values near the middle. We
    look at the difference between these to determine where on the convex curve we are and choose which direction
    to divide in next.
     */
    var lowerBound = this.minOrNull()!!
    var upperBound = this.maxOrNull()!!
    var middlePointScore: Long

    while (upperBound > lowerBound + 1) {
        val middlePoint = (lowerBound + upperBound) / 2
        middlePointScore = this.findPositionScore(middlePoint)
        val neighbourOfMiddleScore = this.findPositionScore(middlePoint + 1)
        when (neighbourOfMiddleScore.compareTo(middlePointScore)) {
            0  -> return middlePointScore  // If they are equal both must be optimal
            1  -> upperBound = middlePoint // The rightmost value was larger, we are to the right of the critical point
            -1 -> lowerBound = middlePoint // The leftmost value was larger, we are to the left of the critical point
        }
    }
    return min(
        this.findPositionScore(lowerBound),
        this.findPositionScore(upperBound)
    )
}

private fun CrabPositions.findPositionScore(pos: Long): Long {
    return sumOf { crabPosition ->
        // Use the formula for arithmetic sum. 1+2+...+n = n*(n+1)/2
        val distance = (crabPosition - pos).absoluteValue
        distance * (distance + 1) / 2
    }
}

class Day7Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 7
    override val name = "The Treachery of Whales"

    override fun part1(): Long {
        val crabPositions = parseIntsOnOneLineFile(inputFilePath)
        // Solution for part 1 is just the mean of the positions
        val pos = crabPositions.sorted()[crabPositions.size / 2]
        return crabPositions.sumOf { (it - pos).absoluteValue }
    }

    override fun part2(): Long {
        val crabPositions = parseIntsOnOneLineFile(inputFilePath)
        val bestPos = crabPositions.findBestPosition()
        return bestPos
    }
}

val day7Problem = Day7Problem("input/aoc2021/day7.txt")

