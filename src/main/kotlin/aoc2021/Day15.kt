package aoc2021

import DailyProblem
import java.io.File
import java.util.*
import kotlin.time.ExperimentalTime

typealias RiskMap = Array<Array<Int>>

fun parseRiskMap(path:String): RiskMap {
    return File(path).readLines().map { line ->
        line.map { char -> char.digitToInt() }.toTypedArray()
    }.toTypedArray()
 }

private fun embiggen(map: RiskMap): RiskMap {
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

fun heuristic(map: RiskMap, pos: Coordinate): Int {
    return 0  // 0 means it's actually djikstra's algorithm. The manhattan heuristics did not really help
    // return (map.size - pos.second) + (map[0].size - pos.first)
}

fun getRisk(map: RiskMap, from:Coordinate, to:Coordinate): Int {
    return map[to.second][to.first]
}

fun neighboursOf(map: RiskMap,coord: Coordinate): Collection<Coordinate> {
    val (x, y) = coord
    val maxX = map[0].size-1
    val maxY = map.size-1
    return buildList {
        if (x>0) add(Pair(x-1,y))
        if (x<maxX) add(Pair(x+1,y))
        if (y>0) add(Pair(x, y-1))
        if (y<maxY) add(Pair(x,y+1))
    }

}

fun <K> Map<K, Int>.getOrInf(key :K ) : Int {
    return getOrDefault(key, Int.MAX_VALUE)
}



fun <M, N> aStar(map: M,
                 start: N,
                 goal: N,
                 heur: (M, N)-> Int,
                 neigh: (M, N) -> Collection<N>,
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
            val tentative_gScore = cheapestPathScoreMap.getOrInf(current) + getEdgeValue(map, current, neighbour)
            if (tentative_gScore < cheapestPathScoreMap.getOrInf(neighbour)) {
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
    override val number = 14
    override val name = "Chiton"

    private lateinit var risks: Array<Array<Int>>

    override fun commonParts() {
        this.risks = parseRiskMap(inputFilePath)
    }

    override fun part1(): Long {
        val maxX = risks[0].size-1
        val maxY = risks.size-1

        return aStar<RiskMap, Coordinate>(risks,
            Pair(0, 0),
            Pair(maxX, maxY),
            ::heuristic,
            ::neighboursOf,
            ::getRisk).toLong()
    }

    override fun part2(): Long {
        val moreRisks = embiggen(risks)
        val maxX = moreRisks[0].size - 1
        val maxY = moreRisks.size - 1

        return aStar<RiskMap, Coordinate>(moreRisks,
            Pair(0, 0),
            Pair(maxX, maxY),
            ::heuristic,
            ::neighboursOf,
            ::getRisk).toLong()
    }
}

val day15Problem = Day15Problem("input/aoc2021/day15.txt")

@OptIn(ExperimentalTime::class)
fun main() {
    day15Problem.runBoth(100)
}