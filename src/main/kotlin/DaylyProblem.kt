interface DaylyProblem {
    val number: Int
    val inputFilePath: String
    fun part1(): Int
    fun part2(): Int

    fun run() {
        println("=== Day $number ===")
        println("part 1: " + this.part1())
        println("part 2: " + this.part2())
    }

}