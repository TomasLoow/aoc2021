package aoc2021

import DailyProblem
import java.io.File

fun parseFishesInput(path: String): MutableList<Long> {
    val fishesCounts = Array<Long>(9) { 0 }

    File(path).readLines()
        .single()
        .split(",")
        .map { it.toInt() }
        .forEach { fishValue ->
            fishesCounts[fishValue]++
        }

    return fishesCounts.toMutableList()
}

private fun simulate(fishesCounts: MutableList<Long>, steps: Int): Long {
    for (step in 1..steps) {
        val zeroes = fishesCounts.removeAt(0)
        fishesCounts[6] += zeroes // Old zeroes are back to countdown 6
        fishesCounts.add(zeroes)  // Their babies
    }
    return fishesCounts.sum()
}

class Day6Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 6
    override val name = "Lanternfish"

    private val stepsPart1 = 80
    private val stepsPart2 = 256

    override fun part1(): Long {
        val fishesCounts = parseFishesInput(inputFilePath)
        return simulate(fishesCounts, stepsPart1)
    }

    override fun part2(): Long {
        val fishesCounts = parseFishesInput(inputFilePath)
        return simulate(fishesCounts, stepsPart2)
    }
}

val day6Problem = Day6Problem("input/aoc2021/day6.txt")
