package day1

import DaylyProblem
import java.io.File


fun countIncreases(input: List<Int>): Int {
    var count = 0
    for (x in input.windowed(2)) {
        if (x[0] < x[1]) count++
    }
    return count
}


fun parseIntsFile(path: String): List<Int> {
    val lines: List<String> = File(path).readLines()
    return lines.map { it.toInt() }
}


class Problem(override val inputFilePath: String) : DaylyProblem {
    override val number = 1

    override fun part1(): Int {
        val input = parseIntsFile(this.inputFilePath)
        return countIncreases(input)
    }

    override fun part2(): Int {
        val input = parseIntsFile(this.inputFilePath)
        val windowedSums = input.windowed(3).map { it.sum() }
        return countIncreases(windowedSums)
    }
}

val problem = Problem("input/day1.txt")


