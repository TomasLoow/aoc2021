import java.io.File
import kotlin.math.roundToInt
import kotlin.math.sign

class VentLine(private val startX: Int, private val startY: Int, private val endX: Int, private val endY: Int) {
    fun isStraight(): Boolean {
        return this.startX == this.endX || this.startY == this.endY
    }

    fun coveredPoints(): Set<Pair<Int, Int>> {
        val dx: Int = sign((endX - startX).toDouble()).roundToInt()
        val dy: Int = sign((endY - startY).toDouble()).roundToInt()

        val result: MutableSet<Pair<Int, Int>> = mutableSetOf()
        var x = startX
        var y = startY
        while (x != endX || y != endY) {
            result.add(Pair(x, y))
            x += dx
            y += dy
        }
        result.add(Pair(endX, endY))
        return result

    }
}
fun parseLinesFile(path: String): List<VentLine> {
    val lines: List<String> = File(path).readLines()
    return lines.map { line ->
        line.split(" -> ")
    }.map { coordinatePairs ->
        val start = coordinatePairs[0].split(",").map { num -> num.toInt() }
        val end = coordinatePairs[1].split(",").map { num -> num.toInt() }
        VentLine(start[0], start[1], end[0], end[1])
    }
}


private fun countIntersections(lines: List<VentLine>): Int {
    val counter: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    val pointsWithDuplicates = lines.flatMap { it.coveredPoints() }

    for (point in pointsWithDuplicates) {
        val current = counter.getOrDefault(point, 0)
        counter[point] = current + 1
    }

    return counter.filter { it.value > 1 }.size
}


fun day5_1(): Int {
    val lines = parseLinesFile("input/day5.txt")
    val straightLines = lines.filter {
        it.isStraight()
    }
    return countIntersections(straightLines)
}


fun day5_2(): Int {
    val lines = parseLinesFile("input/day5.txt")
    return countIntersections(lines)
}
