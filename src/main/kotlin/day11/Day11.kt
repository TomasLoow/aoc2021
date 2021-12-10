package day11

import DailyProblem


class Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 11

    override fun part1(): Long {
        val og = readGrid(inputFilePath)

        val flashCount = (1..100).sumOf { og.step() }

        return flashCount.toLong()
    }

    override fun part2(): Long {
        val og = readGrid(inputFilePath)

        var step = 0L
        do {
            og.step()
            step++
        } while (!og.allZero())
        return step
    }
}

val problem = Problem("input/day11.txt")
