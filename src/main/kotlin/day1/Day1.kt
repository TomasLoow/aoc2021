package day1

import DailyProblem
import java.io.File


fun countIncreases(input: List<Int>): Long {
    return input.windowed(2).count { it[0] < it[1] }.toLong()
}


fun parseIntsFile(path: String): List<Int> {
    val lines: List<String> = File(path).readLines()
    return lines.map { it.toInt() }
}


class Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 1

    override fun part1(): Long {
        val input = parseIntsFile(this.inputFilePath)
        return countIncreases(input)
    }

    override fun part2(): Long {
        val input = parseIntsFile(this.inputFilePath)
        val windowedSums = input.windowed(3).map { it.sum() }
        return countIncreases(windowedSums)
    }
}

val problem = Problem("input/day1.txt")


