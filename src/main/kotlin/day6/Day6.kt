package day6

import DailyProblem
import java.io.File
import java.math.BigInteger

fun readInput(path: String): MutableList<Long> {
    val state = Array<Long>(9) { 0 }
    val line = File(path).readLines()[0].split(",").map { it.toInt()}
    line.forEach { num -> state[num]++}
    return state.toMutableList()
}

class Problem(override val inputFilePath: String, steps: Int, steps2: Int) : DailyProblem {
    override val number = 2
    private val stepsPart1 = steps
    private val stepsPart2 = steps2

    override fun part1(): Long {
        val state = readInput(inputFilePath)
        return simulate(state, stepsPart1)
    }

    private fun simulate(state: MutableList<Long>, steps: Int): Long {
        for (step in 1..steps) {
            val zeroes = state.removeAt(0)
            state[6] += zeroes // Old zeroes are back to countdown 6
            state.add(zeroes)  // Their babies
        }
        return state.sum()
    }

    override fun part2(): Long {
        val state = readInput(inputFilePath)
        return simulate(state, stepsPart2)
    }
}

val problem = Problem("input/day6.txt",80, 256 )
