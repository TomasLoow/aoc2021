package day1

import DailyProblem
import java.io.File

fun parseIntsFile(path: String): List<Int> {
    val lines: List<String> = File(path).readLines()
    return lines.map { it.toInt() }
}


private fun Collection<Int>.countIncreases(): Long {
    return windowed(2).count { it[0] < it[1] }.toLong()
}


private fun Collection<Int>.threeElementWindowSums(): Collection<Int> {
    return windowed(3)
        .map { it.sum() }
}

class Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 1

    override fun part1(): Long {
        return parseIntsFile(this.inputFilePath)
            .countIncreases()
    }
    override fun part2(): Long {
        return parseIntsFile(this.inputFilePath)
            .threeElementWindowSums()
            .countIncreases()
    }
}

val problem = Problem("input/day1.txt")


