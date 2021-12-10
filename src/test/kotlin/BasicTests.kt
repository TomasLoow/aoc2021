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
        expected_2: Long
    ) {
        assertEquals(expected_1, problem.part1(), "Solution for Day $day part 1 should be correct")
    }

    @ParameterizedTest(name = "Example problem from Day {0}, part 2 should have answer {3}")
    @MethodSource("testCases")
    fun `The solution of part two for each day should satisfy the small example on the webpage`(
        day: Int,
        problem: DailyProblem,
        expected_1: Long,
        expected_2: Long
    ) {
        assertEquals(expected_2, problem.part2(), "Solution for Day $day part 2 should be correct")
    }

    private companion object {
        @JvmStatic
        fun testCases() = Stream.of(
            Arguments.of(1,  day1.Problem("input/testinput/day1.txt"), 7, 5),
            Arguments.of(2,  day2.Problem("input/testinput/day2.txt"), 150, 900),
            Arguments.of(3,  day3.Problem("input/testinput/day3.txt"), 198, 230),
            Arguments.of(4,  day4.Problem("input/testinput/day4.txt"), 4512, 1924),
            Arguments.of(5,  day5.Problem("input/testinput/day5.txt"), 5, 12),
            Arguments.of(6,  day6.Problem("input/testinput/day6.txt"), 5934, 26984457539),
            Arguments.of(7,  day7.Problem("input/testinput/day7.txt"), 37, 168),
            Arguments.of(8,  day8.Problem("input/testinput/day8.txt"), 26, 61229),
            Arguments.of(9,  day9.Problem("input/testinput/day9.txt"), 15, 1134),
            Arguments.of(10, day10.Problem("input/testinput/day10.txt"), 26397, 288957)
        )
    }

}