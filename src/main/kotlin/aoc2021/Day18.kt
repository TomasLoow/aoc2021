package aoc2021

import DailyProblem
import parserCombinators.*
import java.io.File
import kotlin.time.ExperimentalTime

enum class ZigZag {L,R}

typealias TreePos = List<ZigZag>

class Tree(internal val arr: List<Pair<TreePos, Int>>) {

    val right: Tree
        get() = Tree(arr.filter { it.first.first() == ZigZag.R }.map { it.copy(first = it.first.drop(1))})

    val left: Tree
        get() = Tree(arr.filter { it.first.first() == ZigZag.L }.map { it.copy(first = it.first.drop(1))})

    fun isLeaf(): Boolean = arr.size==1

    fun getMagnitue(): Int {
        if (arr.size == 1) return arr.first().second

        return 3 * left.getMagnitue() + 2 * right.getMagnitue()
    }

    fun split(): Tree {
        val thingToSplit = this.arr.withIndex().find { idxVal ->
            idxVal.value.second >= 10
        }
        if (thingToSplit == null) return this
        val idx = thingToSplit.index
        val pathTo = thingToSplit.value.first
        val value = thingToSplit.value.second

        val newList = listOf(
            Pair(pathTo + ZigZag.L, value/2),
            Pair(pathTo + ZigZag.R, value/2 + value.mod(2)),
        )

        return Tree(arr.take(idx) + newList + arr.drop(idx+1))
    }

    fun explode(): Tree {
        val thingToSplit = this.arr.withIndex().find { idxVal ->
            idxVal.value.first.size >= 5
        }

        if (thingToSplit == null) return this
        val idx = thingToSplit.index
        val pathTo = thingToSplit.value.first
        val value = thingToSplit.value.second
        val neighbourValue = arr[idx+1].second

        val newList = listOf(
            Pair(pathTo.dropLast(1), 0),
        )

        val before = arr.take(idx).toMutableList()
        val after = arr.drop(idx+2).toMutableList()
        if (before.isNotEmpty()) {
            val lastBefore = before[before.size - 1]
            before[before.size - 1] = lastBefore.copy(second = lastBefore.second + value)
        }

        if (after.isNotEmpty()) {
            val firstAfter = after[0]
            after[0] = firstAfter.copy(second = firstAfter.second + neighbourValue)
        }

        return Tree(before + newList + after)
    }

    fun reduce(): Tree {
        var reduced = Tree(this.arr)
        while (true) {
            val exploded = reduced.explode()
            if (reduced.arr != exploded.arr) {
                reduced = exploded
                continue
            }
            val splut = exploded.split()
            if (splut.arr == exploded.arr) {
                reduced = splut
                break
            }
            reduced = splut
        }
        return reduced
    }

    operator fun plus(t: Tree): Tree {
        return branch(this, t).reduce()
    }

    fun print(): String {
        if (isLeaf()) {
            return this.arr.first().second.toString()
        }
        else {
            return "[${left.print()},${right.print()}]"
        }
    }
}

fun leaf(i: Int): Tree {
    return Tree(listOf(Pair(emptyList(), i)))
}

fun branch(left: Tree, right: Tree): Tree {
    return Tree(buildList {
        addAll(left.arr.map { it -> it.copy(first=(listOf(ZigZag.L) + it.first)) })
        addAll(right.arr.map { it -> it.copy(first=(listOf(ZigZag.R) + it.first)) })
    })

}

fun parseSnailNumber(str: String) : Tree {
    val pLeftParen = pToken('[')
    val pRightParen = pToken(']')
    val pComma = pToken(',')

    val pLeaf: Parser<Char, Tree> = pApplyFun(::pInt, ::leaf)

    fun parser(input: List<Char>) : ParseResult<Char, Tree> {
        val p: Parser<Char,Tree> = pOneOf(
            pLeaf,
            pLeftParen
                .thenDo(pCombineTwo(
                    ::parser.thenIgnore(pComma),
                    ::parser,
                    ::branch))
                .thenIgnore(pRightParen)
        )
        return p(input)
    }
    val (res, _) = parser(str.toList())
    return res
}

fun parseSnailfishNumbersFile(path: String): List<Tree> {
    return File(path).readLines().map(::parseSnailNumber)
}

class Day18Problem(override val inputFilePath: String) : DailyProblem {
    private lateinit var parsedNumbers: List<Tree>
    override val number = 18

    override val name= "Snailfish"

    override fun commonParts() {
        parsedNumbers = parseSnailfishNumbersFile(inputFilePath)
    }

    override fun part1(): Long {
        return parsedNumbers.reduce { acc, tree -> acc + tree }.getMagnitue().toLong()
    }
    override fun part2(): Long {
        val maxSum = parsedNumbers.flatMap { num1 ->
            parsedNumbers.filter { it != num1 }.map { num2 ->
                (num1 + num2).getMagnitue()
            }
        }.maxOf { it }
        return maxSum.toLong()
    }
}

val day18Problem = Day18Problem("input/aoc2021/day18.txt")

@OptIn(ExperimentalTime::class)
fun main() {

    day18Problem.runBoth(100)
}
