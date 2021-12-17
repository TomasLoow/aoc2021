package aoc2021

import DailyProblem
import java.io.File
import java.util.*
import kotlin.time.ExperimentalTime

typealias RiskMap2D = Array<Array<Int>>
typealias RiskMap = Array<Int>

fun parseRiskMap(path:String): RiskMap2D {
    return File(path).readLines().map { line ->
        line.map { char -> char.digitToInt() }.toTypedArray()
    }.toTypedArray()
 }

private fun embiggen(map: RiskMap2D): RiskMap2D {
    val factor = 5
    val originalWidth = map.size
    val originalHeight = map[0].size
    val newHeight = originalWidth * factor
    val newWidth = originalHeight * factor
    val biggerArray: Array<Array<Int>> = Array(newHeight) { Array(newWidth) {0} }

    (0 until newHeight).forEach { y ->
        (0 until newHeight).forEach { x ->
            val baseValue = map[y.mod(originalHeight)][x.mod(originalWidth)] + x/ originalWidth + y/ originalHeight
            biggerArray[y][x] = if (baseValue > 9) {
                baseValue - 9
            } else {
                baseValue
            }
        }
    }
    return biggerArray
}

fun heuristic(map: RiskMap, pos: Int): Int {
    return 0  // 0 means it's actually djikstra's algorithm. The manhattan heuristics did not really help
    // return (map.size - pos.second) + (map[0].size - pos.first)
}

fun getRisk(map: RiskMap, from:Int, to:Int): Int {
    return map[to]
}

fun neighboursOfForSize(size: Int): (RiskMap, Int) -> Sequence<Int> {
    return { intsmap, idx ->
        val x = idx.mod(size)
        val y = idx/size

        val maxX = size-1
        val maxY = size-1

        sequence {
            if (x>0) yield(idx-1)
            if (x<maxX) yield(idx+1)
            if (y>0) yield(idx-size)
            if (y<maxY) yield(idx+size)
        }
    }
}

fun <M, N> aStar(map: M,
                 start: N,
                 goal: N,
                 heur: (M, N)-> Int,
                 neigh: (M, N) -> Sequence<N>,
                 getEdgeValue: (M, N, N) -> Int): Int {
    val openSet = PriorityQueue<Pair<Int,N>>(compareBy { it.first })
    openSet.add(heur(map,start) to start)

    val cameFrom: MutableMap<N, N> = mutableMapOf()
    val cheapestPathScoreMap : MutableMap<N, Int> = mutableMapOf(start to 0)
    val heuristicScoreMap: MutableMap<N, Int> = mutableMapOf(start to heur(map, start))

    while (openSet.isNotEmpty()) {
        val currentPair = openSet.first()!!
        val current= currentPair.second
        if (current == goal) {
            return cheapestPathScoreMap[current]!!
        }
        openSet.remove(currentPair)

        for (neighbour: N in neigh(map, current)) {
            var neighbourValue: Int
            try {
                neighbourValue = getEdgeValue(map, current, neighbour)
            } catch (e: ArrayIndexOutOfBoundsException) {
                continue
            }
            val tentative_gScore = cheapestPathScoreMap.getOrDefault(current, Int.MAX_VALUE) + neighbourValue
            if (tentative_gScore < cheapestPathScoreMap.getOrDefault(neighbour, Int.MAX_VALUE)) {
                cameFrom[neighbour] = current
                cheapestPathScoreMap[neighbour] = tentative_gScore
                heuristicScoreMap[neighbour] = tentative_gScore + heur(map, neighbour)
                openSet.add(heuristicScoreMap[neighbour]!! to neighbour)
            }
        }
    }
    throw Exception("No path")
}

class Day15Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 15
    override val name = "Chiton"

    private lateinit var risks: Array<Array<Int>>

    override fun commonParts() {
        this.risks = parseRiskMap(inputFilePath)
    }

    override fun part1(): Long {
        val flattened = risks.flatten().toTypedArray()
        return aStar<RiskMap, Int>(flattened,
            0,
            flattened.size - 1,
            ::heuristic,
            neighboursOfForSize(risks[0].size),
            ::getRisk).toLong()
    }

    override fun part2(): Long {
        val moreRisks = embiggen(risks)
        val flattened = moreRisks.flatten().toTypedArray()
        return aStar<RiskMap, Int>(flattened,
            0,
            flattened.size - 1,
            ::heuristic,
            neighboursOfForSize(moreRisks[0].size),
            ::getRisk).toLong()
    }
}

val day15Problem = Day15Problem("input/aoc2021/day15.txt")

@OptIn(ExperimentalTime::class)
fun main() {
    day15Problem.runBoth(5)
}