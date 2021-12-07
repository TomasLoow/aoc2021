package day4

val allRowIndices: List<Array<Pair<Int, Int>>> = listOf(
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
}