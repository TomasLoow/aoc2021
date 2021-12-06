package day6

import DailyProblem
import java.io.File

fun readFishesInput(path: String): MutableList<Long> {
    val fishesCounts = Array<Long>(9) { 0 }
    val fishes = File(path).readLines()[0].split(",").map { it.toInt() }
    fishes.forEach { fishValue -> fishesCounts[fishValue]++ }
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

class Problem(override val inputFilePath: String, private val stepsPart1: Int, private val stepsPart2: Int) : DailyProblem {
    override val number = 6

    override fun part1(): Long {
        val fishesCounts = readFishesInput(inputFilePath)
        return simulate(fishesCounts, stepsPart1)
    }

    override fun part2(): Long {
        val fishesCounts = readFishesInput(inputFilePath)
        return simulate(fishesCounts, stepsPart2)
    }
}

val problem = Problem("input/day6.txt", 80, 256)
