import java.io.File
import java.util.*

fun parseIntsFile(path: String): List<Int> {
    val lines: List<String> = File(path).readLines()
    return lines.map { it.toInt() }
}

fun parseBinaryFile(path: String): List<Array<Boolean>> {
    val lines: List<String> = File(path).readLines()
    return lines.map{
        val bs = Array<Boolean>(it.length) {false}
        var idx = 0
        for (char in it.toCharArray()) {
            if (char == '1') bs[idx] = true
            idx++
        }
        bs
    }
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
    println("===Day 3===")
    println("part 1: " + day3_1())
    println("part 2: " + day3_2())
    println("===Day 4===")
    println("part 1: " + day4_1())
    println("part 2: " + day4_2())
}