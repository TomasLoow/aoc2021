package aoc2021

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

class Day1Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 1
    override val name = "Sonar Sweep"

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

val day1Problem = Day1Problem("input/aoc2021/day1.txt")


