package aoc2021


import DailyProblem
import java.io.File

fun parseBingoFile(path: String): Pair<List<Int>, List<BingoBoard>> {
    var lines: List<String> = File(path).readLines()
    val balls = lines[0]
        .split(",")
        .map { it.toInt() }
    lines = lines.drop(2)

    val boards = ArrayList<BingoBoard>(0)
    while (lines.size >= 5) {
        val board = BingoBoard(lines.subList(0, 5))
        boards.add(board)
        lines = lines.drop(6)  // Five for board, one separator
    }
    return Pair(balls, boards)
}

class BingoBoard() {
    constructor(rows: List<String>) : this() {
        numbers = rows.flatMap { row -> row
            .trim()
            .split(" +".toRegex())
            .map { number -> number.toInt() }
        }.toTypedArray()
    }
    private lateinit var numbers: Array<Int>

    private var marked = Array(25) { false }

    var hasWon: Boolean = false

    private fun toIdx(i: Int, j: Int): Int {
        return 5 * i + j
    }

    fun markNumber(ball: Int) {
        val idx = this.findIdxOfNumber(ball)
        if (idx != null) this.marked[idx] = true
    }

    private fun findIdxOfNumber(ball: Int): Int? {
        return numbers
            .withIndex()
            .find { it.value == ball }
            ?.index
    }

    fun checkBingo(): Boolean {
        return allRowIndices.any { this.checkRow(it) }
    }

    private fun checkRow(indexes: Array<Pair<Int, Int>>): Boolean {
        return indexes.all { this.marked[this.toIdx(it.first, it.second)] }
    }

    fun getScore(): Int {
        val indicesOfUnmarked = marked
            .withIndex()
            .filter { !it.value }
            .map { it.index }
        val unmarked = indicesOfUnmarked.map { this.numbers[it] }
        return unmarked.sum()
    }

    companion object {
        private val allRowIndices: List<Array<Pair<Int, Int>>> = listOf(
            arrayOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3), Pair(0, 4)),
            arrayOf(Pair(1, 0), Pair(1, 1), Pair(1, 2), Pair(1, 3), Pair(1, 4)),
            arrayOf(Pair(2, 0), Pair(2, 1), Pair(2, 2), Pair(2, 3), Pair(2, 4)),
            arrayOf(Pair(3, 0), Pair(3, 1), Pair(3, 2), Pair(3, 3), Pair(3, 4)),
            arrayOf(Pair(4, 0), Pair(4, 1), Pair(4, 2), Pair(4, 3), Pair(4, 4)),

            arrayOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0)),
            arrayOf(Pair(0, 1), Pair(1, 1), Pair(2, 1), Pair(3, 1), Pair(4, 1)),
            arrayOf(Pair(0, 2), Pair(1, 2), Pair(2, 2), Pair(3, 2), Pair(4, 2)),
            arrayOf(Pair(0, 3), Pair(1, 3), Pair(2, 3), Pair(3, 3), Pair(4, 3)),
            arrayOf(Pair(0, 4), Pair(1, 4), Pair(2, 4), Pair(3, 4), Pair(4, 4)),
            // No diagonals
            //arrayOf( Pair(0,0), Pair(1,1), Pair(2,2), Pair(3,3) , Pair(4,4)),
            //arrayOf( Pair(0,4), Pair(1,3), Pair(2,2), Pair(3,1) , Pair(4,0)),
        )
    }
}

class Day4Problem(override val inputFilePath: String) : DailyProblem {
    override val number = 4

    override val name = "Giant Squid"

    override fun part1(): Long {
        val data: Pair<List<Int>, List<BingoBoard>> = parseBingoFile(this.inputFilePath)

        val (balls, boards) = data

        for (ball in balls) {
            for (board in boards) {
                board.markNumber(ball)
                if (board.checkBingo()) {
                    val score = board.getScore()
                    return (ball * score).toLong()
                }
            }
        }
        throw Exception("No bingo")
    }
    override fun part2(): Long {
        val data: Pair<List<Int>, List<BingoBoard>> = parseBingoFile(this.inputFilePath)

        val (balls, boards) = data

        val numberOfBoards = boards.size
        var numberOfWins = 0

        for (ball in balls) {
            for (board in boards) {
                if (board.hasWon) continue
                board.markNumber(ball)
                if (board.checkBingo()) {
                    board.hasWon = true
                    numberOfWins++
                    if (numberOfWins == numberOfBoards) {
                        val score = board.getScore()
                        return (ball * score).toLong()
                    }
                }
            }
        }
        throw Exception("No bingo")
    }
}

val day4Problem = Day4Problem("input/aoc2021/day4.txt")
