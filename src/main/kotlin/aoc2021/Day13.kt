package aoc2021

import DailyProblem
import java.io.File

enum class Axis {
    X,Y
}

data class Point(var first: Int, var second: Int)  // Use dataclass instead of Pair so it's mutable


data class Fold(val direction: Axis, val foldLinePos:Int) {
    fun applyToGrid(grid: Array<Point>) {
        when (direction) {
            Axis.Y -> grid
                .forEach { p ->
                            val y = p.second
                            if (y > foldLinePos) {
                                p.second = 2 * foldLinePos - y
                            }
                        }
            Axis.X -> grid
                .forEach { p ->
                            val x= p.first
                            if (x > foldLinePos) {
                                p.first = 2 * foldLinePos - x
                            }
                        }

        }
    }
}

private fun parseFoldingFile(path: String): Pair<Array<Point>, List<Fold>> {
    val lines = File(path).readLines()

    val grid = lines
        .takeWhile { line -> line.isNotEmpty() }
        .map { line ->
            val (x, y) = line.split(",").map { it.toInt() }
            Point(x, y)
        }.toTypedArray()

    val folds = lines
        .dropWhile { line -> line.isNotEmpty() }.drop(1)
        .map { line ->
            val (axis, index) = line.removePrefix("fold along ").split("=")
            Fold(if (axis == "x") Axis.X else Axis.Y, index.toInt())
        }
    return Pair(grid, folds)
}

private fun gridToStrings(pairCollection: Array<Point>): List<String> {
    val maxX = pairCollection.maxOf { it.first }
    val maxY = pairCollection.maxOf { it.second }

    return (0 .. maxY).map { y ->
        (0 .. maxX).map { x ->
            if (Point(x,y) in pairCollection) '#' else '.'
        }.joinToString("")
    }
}


class Day13Problem(override val inputFilePath: String) : DailyProblem {

    override val number = 13
    override val name = "Transparent Origami"

    lateinit var display : List<String>  // We will put the answer to part 2 here

    private lateinit var parseResult: Pair<Array<Point>, List<Fold>>

    override fun commonParts() {
        parseResult = parseFoldingFile(inputFilePath)
    }
    override fun part1(): Long {
        val (grid, folds) = parseResult
        val firstFold = folds.first()

        firstFold.applyToGrid(grid)
        return grid.toSet().size.toLong()
    }

    override fun part2(): Long {
        val (grid, folds) = parseResult

        folds.forEach { fold ->
                fold.applyToGrid(grid)
        }
        display = gridToStrings(grid)
        //display.forEach(::println)
        return -999  // Not an integer solution. Answers stored in display attribute instead
    }
}

val day13Problem = Day13Problem("input/aoc2021/day13.txt")
