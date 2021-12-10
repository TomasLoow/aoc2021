import kotlin.time.ExperimentalTime

val problems = listOf(
    day1.problem,
    day2.problem,
    day3.problem,
    day4.problem,
    day5.problem,
    day6.problem,
    day7.problem,
    day8.problem,
    day9.problem,
    day10.problem,
    day11.problem,
)

@OptIn(ExperimentalTime::class)
fun main() {
    problems.forEach { problem ->
        problem.runBoth(timesToRun = 10)
    }
}