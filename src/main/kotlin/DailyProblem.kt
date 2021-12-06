interface DailyProblem {
    val number: Int
    val inputFilePath: String
    fun part1(): Long
    fun part2(): Long

    fun run() {
        println("=== Day $number ===")
        println("part 1: " + this.part1())
        println("part 2: " + this.part2())
        println()
    }

}