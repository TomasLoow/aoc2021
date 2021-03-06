package aoc2021

import DailyProblem
import java.io.File

internal fun parseGrid(path: String) =
    OctopusGrid(File(path).readText().filter { it.isDigit() }.map { it.digitToIntOrNull()!! }
        .toTypedArray())

typealias Idx = Int

class OctopusGrid(val grid: Array<Int>) {

    /**
     * Runs a step of the simulation and returns the number of flashes that occurred
     */
    fun step() : Int {
        grid.indices.forEach{ idx -> grid[idx]++}
        return doFlashes()
    }

    /**
     * Return true if all octopuses are 0
     */
    fun allZero(): Boolean {
        return grid.all { it == 0 }
    }

    private fun doFlashes(): Int {
        val indicesThatHaveFlashed = mutableSetOf<Int>()
        do {
            var flashed = false
            val flashPoint: IndexedValue<Idx> =
                grid.asSequence().withIndex().filter { it.value > 9 }
                    .filter { !indicesThatHaveFlashed.contains(it.index)}
                    .firstOrNull() ?: continue
            val idx = flashPoint.index
            doFlash(idx)
            flashed = true
            indicesThatHaveFlashed.add(idx)
        } while (flashed)

        indicesThatHaveFlashed.forEach { grid[it] = 0 }
        return indicesThatHaveFlashed.size
    }

    /**
     * Increases the value of all neighbours of a point
     */
    private fun doFlash(idx: Idx) {
        val neighbours = getNeighboursOf(idx)
        neighbours.forEach {
            grid[it]++
        }
    }

    private fun getNeighboursOf(idx: Idx): List<Idx> {
        val neighbours = buildList {
            val xRange = when (idx.mod(10)) {
                0 -> (0..1)
                9 -> (-1..0)
                else -> (-1..1)
            }
            val yRange = when (idx/10) {
                0 -> (0..10 step 10)
                9 -> (-10..0 step 10)
                else -> (-10..10 step 10)
            }
            for (dx in xRange) {
                for (dy in yRange) {
                    val d = dx+dy
                    if (d == 0) {
                        continue
                    }
                    add(idx + d)
                }
            }
        }
        return neighbours
    }
}

class Day11Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 11
    override val name = "Dumbo Octopus"

    override fun part1(): Long {
        val og = parseGrid(inputFilePath)

        val flashCount = (1..100).sumOf { og.step() }

        return flashCount.toLong()
    }

    override fun part2(): Long {
        val og = parseGrid(inputFilePath)

        var step = 0L
        do {
            og.step()
            step++
        } while (!og.allZero())
        return step
    }
}

val day11Problem = Day11Problem("input/aoc2021/day11.txt")
