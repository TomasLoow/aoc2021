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
    day12.problem,
    day13.problem,
    day14.problem,
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