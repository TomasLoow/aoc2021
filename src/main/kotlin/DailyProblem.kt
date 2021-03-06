import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

interface DailyProblem {
    val number: Int
    val name: String
    val inputFilePath: String
    fun commonParts() {}
    fun part1(): Long
    fun part2(): Long

    @ExperimentalTime
    fun runBoth(timesToRun:Int = 1) : Duration {
        println("=== Day $number : $name ===")
        var result1 = 0L
        var result2 = 0L
        val runDuration = measureTime{
            (1..timesToRun).forEach {
                this.commonParts()
                result1 = this.part1()
                result2 = this.part2()
            }
        }
        val averageDuration = runDuration/timesToRun
        println("part 1: $result1")
        println("part 2: $result2")
        println("Average runtime for day ${number}: $averageDuration based on $timesToRun runs")
        println("===========")
        println()
        return averageDuration
    }

}