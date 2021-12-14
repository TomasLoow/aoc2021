package aoc2021

import DailyProblem
import java.io.File
import java.util.*


/** Encoding: Caves are stored as ints instead of strings.
 * We always have 0 = start, 1 = end. Other caves are arbitrary wih large, caves that can be revisited mapped to
 * negative numbers and small normal caves mapped to positive numbers.
 */
typealias Cave = Int
const val startCave:Cave = 0
const val endCave:Cave = 1

fun allowsRevisits(cave: Cave): Boolean {
    return cave < 0
}

typealias CaveSystem = Map<Cave, List<Cave>>

fun cavesReachableFrom(map: CaveSystem, cave: Cave): Collection<Cave> {
    return map[cave]!!
}

fun parseConnections(path: String): CaveSystem {
    val connections = mutableListOf<Pair<Cave,Cave>>()
    val caveNameMap: MutableMap<String, Int> = mutableMapOf("start" to startCave, "end" to endCave)
    var nextNumber = 2

    val allCaves = mutableSetOf<Cave>(startCave, endCave)
    File(path).readLines().map { line ->
        val (caveNameA, caveNameB) = line.split("-")
        val caveA: Cave
        val caveB: Cave
        if (caveNameMap.contains(caveNameA)) {
            caveA = caveNameMap[caveNameA]!!
        } else {
            caveA = if (caveNameA != caveNameA.uppercase()) {
                nextNumber
            } else {
                -nextNumber
            }
            caveNameMap[caveNameA] = caveA
            allCaves.add(caveA)
            nextNumber++
        }
        if (caveNameMap.contains(caveNameB)) {
            caveB = caveNameMap[caveNameB]!!
        } else {
            caveB = if (caveNameB != caveNameB.uppercase()) {
                nextNumber
            } else {
                -nextNumber
            }
            caveNameMap[caveNameB] = caveB
            allCaves.add(caveB)
            nextNumber++
        }

        connections.add(Pair(caveA,caveB))
    }
    val foo: CaveSystem = buildList<Pair<Cave,List<Cave>>> {
        allCaves.forEach { cave ->
            val connected = buildList<Cave> {
                connections.forEach { connection ->
                    if (connection.first == cave) {
                        add(connection.second)
                    } else if (connection.second == cave) {
                        add(connection.first)
                    }
                }
            }.toList()
            add(Pair(cave, connected))
        }
    }.toMap()
    return foo
}

fun search(map: CaveSystem,
           allowRevisits: Boolean,
           start:Cave,
           end:Cave) : Int {
    if (start == end) {
        return 1
    }

    val initialState: Triple<Boolean, List<Cave>, Cave> = Triple(allowRevisits, listOf(), startCave)
    val searchStack = Stack<Triple<Boolean, List<Cave>, Cave>>()
    var foundPaths = 0

    searchStack.push(initialState)
    while (searchStack.isNotEmpty()) {
        val (allowRevisit, visited, currentNode) = searchStack.pop()
        if (currentNode == endCave) {
            foundPaths++
            continue
        }
        val allConnectedCaves = cavesReachableFrom(map, currentNode)
        val possibleNormalNextSteps =  allConnectedCaves.filter { !visited.contains(it) || allowsRevisits(it)  }
        val possibleBonusRevisitSteps =
            if (allowRevisit) {
                allConnectedCaves
                    .filter { visited.contains(it) && it != startCave }
                    .filter { !possibleNormalNextSteps.contains(it)}
            } else {
                emptyList()
            }

        val newVisitedSet = if (allowsRevisits(currentNode)) {
            visited
        } else {
            visited.plus(currentNode)
        }

        possibleNormalNextSteps.forEach {
            searchStack.push(Triple(allowRevisit, newVisitedSet, it))
        }
        possibleBonusRevisitSteps.forEach {
            searchStack.push(Triple(false, newVisitedSet, it))
        }
    }
    return foundPaths
}

class Day12Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 12
    override val name = "Passage Pathing"

    override fun part1(): Long {
        val connections: CaveSystem = parseConnections(inputFilePath)
        return search(connections, allowRevisits = false, start = startCave, end = endCave).toLong()
    }

    override fun part2(): Long {
        val connections: CaveSystem = parseConnections(inputFilePath)
        return search(connections, allowRevisits = true, start = startCave, end = endCave).toLong()
    }
}

val day12Problem = Day12Problem("input/aoc2021/day12.txt")
