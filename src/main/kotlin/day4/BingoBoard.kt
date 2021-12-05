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

class BingoBoard(private var numbers: Array<Int>) {

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
        val idx = this.numbers.indexOf(ball)
        if (idx < 0) {
            return null
        }
        return idx
    }

    fun checkBingo(): Boolean {
        // Check rows

        return allRowIndices.any { this.checkRow(it) }
    }

    private fun checkRow(indexes: Array<Pair<Int, Int>>): Boolean {
        return indexes.all { this.marked[this.toIdx(it.first, it.second)] }
    }

    fun getScore(): Int {
        val indicesOfUnmarked = this.marked.withIndex().filter { !it.value }.map { it.index }
        val unmarked = indicesOfUnmarked.map { this.numbers[it] }
        return unmarked.sum()
    }

/*    fun printMarks() {
        for (i in 0.rangeTo(4)) {
            for (j in 0.rangeTo(4)) {
                if (this.marked[this.toIdx(i, j)]) {
                    print("X")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun printBoard() {
        for (i in 0.rangeTo(4)) {
            for (j in 0.rangeTo(4)) {
                print("" + this.numbers[this.toIdx(i, j)] + " ")
            }
            println()
        }
    }*/
}