import kotlin.time.ExperimentalTime

val problems = listOf(
    aoc2021.day1Problem,
    aoc2021.day2Problem,
    aoc2021.day3Problem,
    aoc2021.day4Problem,
    aoc2021.day5Problem,
    aoc2021.day6Problem,
    aoc2021.day7Problem,
    aoc2021.day8Problem,
    aoc2021.day9Problem,
    aoc2021.day10Problem,
    aoc2021.day11Problem,
    aoc2021.day12Problem,
    aoc2021.day13Problem,
    aoc2021.day14Problem,
    aoc2021.day15Problem,
    aoc2021.day16Problem,
    aoc2021.day17Problem,
    aoc2021.day18Problem,
    aoc2021.day21Problem,
    aoc2021.day22Problem,
)

@OptIn(ExperimentalTime::class)
fun main() {
    val durations = problems.map { problem ->
        val dur = problem.runBoth(timesToRun = 100)
        Pair(problem.number, dur)
    }.toMap()
    println("=== Timing summary ===")
    durations.forEach {
        println("Day ${it.key}\t${it.value}")
    }
    println("Total time: ${durations.values.reduce{acc, duration -> acc + duration }}")
}