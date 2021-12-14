package day13

import aoc2021.Day13Problem
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Problem13Test {

    @Test
    fun part1() {
        val problem = Day13Problem("input/aoc2021/testinput/day13.txt")
        assertEquals(problem.part1(), 17)
    }

    @Test
    fun part2() {
        val problem = Day13Problem("input/aoc2021/testinput/day13.txt")
        problem.part2()
        val expected = listOf<String>(
            "#####",
            "#...#",
            "#...#",
            "#...#",
            "#####",
        )
        assertEquals(problem.display, expected)
    }
}