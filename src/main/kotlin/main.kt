import java.io.File

fun parseIntsFile(path: String): List<Int> {
    val lines: List<String> = File(path).readLines()
    return lines.map { it.toInt() }
}

fun countIncreases(input: List<Int>): Int {
    var count = 0
    for (x in input.windowed(2)) {
        if (x[0] < x[1]) count++
    }
    return count
}


fun main() {
    println("===Day 1===")
    println("part 1: " + day1_1())
    println("part 2: " + day1_2())
    println("===Day 2===")
    println("part 1: " + day2_1())
    println("part 2: " + day2_2())
}