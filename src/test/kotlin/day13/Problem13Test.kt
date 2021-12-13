package day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Problem13Test {

    @Test
    fun part1() {
        val problem = Problem("input/testinput/day13.txt")
        assertEquals(problem.part1(), 17)
    }

    @Test
    fun part2() {
        val problem = Problem("input/testinput/day13.txt")
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