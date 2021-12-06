package day6

import DailyProblem
import java.io.File
import java.math.BigInteger

fun readInput(path: String): Array<Long> {
    val state = Array<Long>(9) { 0 }
    val line = File(path).readLines()[0].split(",").map { it.toInt()}
    line.forEach { num -> state[num]++}
    return state
}

class Problem(override val inputFilePath: String, steps: Int, steps2: Int) : DailyProblem {
    override val number = 2
    val stepsPart1 = steps
    val stepsPart2 = steps2

    override fun part1(): Long {
        val state = readInput(inputFilePath)
        return simulate(state, stepsPart1)
    }

    private fun simulate(state: Array<Long>, steps: Int): Long {
        for (step in 1..steps) {
            val zeroes = state[0]
            for (i in 0..7) {
                state[i] = state[i + 1]
            }
            state[6] += zeroes
            state[8] = zeroes
        }
        return state.fold(0) { acc, value -> acc + value }
    }

    override fun part2(): Long {
        val state = readInput(inputFilePath)
        return simulate(state, stepsPart2)
    }
}

val problem = Problem("input/day6.txt",80, 256 )