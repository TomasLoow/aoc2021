import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class MultiTest {
    @ParameterizedTest(name = "Example problem from Day {0}, should have answer {2}")
    @MethodSource("testCases")
    fun `The solution of part one for each day should satisfy the small example on the webpage`(
        day: Int,
        problem: DailyProblem,
        expected_1: Long,
        _expected_2: Long
    ) {
        problem.commonParts()
        assertEquals(expected_1, problem.part1(), "Solution for Day $day part 1 should be correct")
    }

    @ParameterizedTest(name = "Example problem from Day {0}, part 2 should have answer {3}")
    @MethodSource("testCases")
    fun `The solution of part two for each day should satisfy the small example on the webpage`(
        day: Int,
        problem: DailyProblem,
        _expected_1: Long,
        expected_2: Long
    ) {
        problem.commonParts()
        assertEquals(expected_2, problem.part2(), "Solution for Day $day part 2 should be correct")
    }

    private companion object {
        @JvmStatic
        fun testCases() = Stream.of(
            Arguments.of(1,  aoc2021.Day1Problem("input/aoc2021/testinput/day1.txt"), 7, 5),
            Arguments.of(2,  aoc2021.Day2Problem("input/aoc2021/testinput/day2.txt"), 150, 900),
            Arguments.of(3,  aoc2021.Day3Problem("input/aoc2021/testinput/day3.txt"), 198, 230),
            Arguments.of(4,  aoc2021.Day4Problem("input/aoc2021/testinput/day4.txt"), 4512, 1924),
            Arguments.of(5,  aoc2021.Day5Problem("input/aoc2021/testinput/day5.txt"), 5, 12),
            Arguments.of(6,  aoc2021.Day6Problem("input/aoc2021/testinput/day6.txt"), 5934, 26984457539),
            Arguments.of(7,  aoc2021.Day7Problem("input/aoc2021/testinput/day7.txt"), 37, 168),
            Arguments.of(8,  aoc2021.Day8Problem("input/aoc2021/testinput/day8.txt"), 26, 61229),
            Arguments.of(9,  aoc2021.Day9Problem("input/aoc2021/testinput/day9.txt"), 15, 1134),
            Arguments.of(10, aoc2021.Day10Problem("input/aoc2021/testinput/day10.txt"), 26397, 288957),
            Arguments.of(11, aoc2021.Day11Problem("input/aoc2021/testinput/day11.txt"), 1656, 195),
            Arguments.of(12, aoc2021.Day12Problem("input/aoc2021/testinput/day12.txt"), 10, 36),

            Arguments.of(14, aoc2021.Day14Problem("input/aoc2021/testinput/day14.txt"), 1588, 2188189693529),
            Arguments.of(15, aoc2021.Day15Problem("input/aoc2021/testinput/day15.txt"), 40, 315),
            Arguments.of(16, aoc2021.Day16Problem("input/aoc2021/testinput/day16.txt"), 20, 1),
            Arguments.of(17, aoc2021.Day17Problem("input/aoc2021/testinput/day17.txt"), 45, 112),
            Arguments.of(18, aoc2021.Day18Problem("input/aoc2021/testinput/day18.txt"), 4140, 3993),
            Arguments.of(21, aoc2021.Day21Problem("input/aoc2021/testinput/day21.txt"), 739785, 444356092776315),
        )
    }

}