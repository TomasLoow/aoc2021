package aoc2021

import DailyProblem
import parserCombinators.*
import java.io.File
import kotlin.time.ExperimentalTime

enum class ZigZag {L,R}

typealias TreePos = List<ZigZag>

class Tree(var arr: Array<Pair<TreePos, Int>>) {

    internal var usedSize = arr.size

    val right: Tree
        get() = Tree(arrList().filter { it.first.first() == ZigZag.R }.map { it.copy(first = it.first.drop(1))}.toTypedArray())

    val left: Tree
        get() = Tree(arrList().filter { it.first.first() == ZigZag.L }.map { it.copy(first = it.first.drop(1))}.toTypedArray())

    fun isLeaf(): Boolean = arr.size==1

    fun getMagnitue(): Int {
        if (arr.size == 1) return arr[0].second

        return 3 * left.getMagnitue() + 2 * right.getMagnitue()
    }

    private fun expandArray() {
        val newArray = Array<Pair<TreePos,Int>>(arr.size * 2 ) { i ->
            if (i < usedSize) arr[i] else Pair(emptyList(),0)
        }
        this.arr = newArray
    }

    fun split(): Boolean {
        val thingToSplit = this.arr.take(usedSize).withIndex().find { idxVal ->
            idxVal.value.second >= 10
        }
        if (thingToSplit == null) return false
        val idx = thingToSplit.index
        val pathTo = thingToSplit.value.first
        val value = thingToSplit.value.second

        if (usedSize==arr.size) {
            expandArray()
        }
        for (i in (usedSize downTo idx+2)) {
            arr[i] = arr[i-1]
        }
        arr[idx]=Pair(pathTo + ZigZag.L, value/2)
        arr[idx+1]=Pair(pathTo + ZigZag.R, value/2 + value.mod(2))
        usedSize += 1
        return true
    }

    fun explode(): Boolean {
        val thingToSplit = this.arr.take(usedSize).withIndex().find { idxVal ->
            idxVal.value.first.size >= 5
        }

        if (thingToSplit == null) return false
        val idx = thingToSplit.index
        val value = thingToSplit.value.second
        val neighbourValue = arr[idx+1].second

        if (idx > 0) {
            arr[idx-1] = arr[idx-1].copy(second = arr[idx-1].second + value)
        }
        if (idx + 1  < usedSize - 1) {
            arr[idx+2] = arr[idx+2].copy(second = arr[idx+2].second + neighbourValue)
        }

        for (i in (idx+1 .. usedSize-2)) {
            arr[i] = arr[i+1]
        }
        arr[idx] = arr[idx].copy(first = arr[idx].first.dropLast(1), second = 0)
        usedSize -= 1
        return true
    }

    fun reduce() {
        var changed=true
        while (changed) {

            changed = explode()
            if (changed) continue

            changed = split()
        }
        return
    }

    operator fun plus(t: Tree): Tree {
        val added = branch(this, t)
        added.reduce()
        return added
    }

    fun print(): String {
        if (isLeaf()) {
            return this.arr.first().second.toString()
        }
        else {
            return "[${left.print()},${right.print()}]"
        }
    }

    fun arrList(): List<Pair<TreePos,Int>> {
        return arr.take(usedSize)
    }
}

fun leaf(i: Int): Tree {
    return Tree(arrayOf(Pair(emptyList(), i)))
}

fun branch(left: Tree, right: Tree): Tree {
    val arr: Array<Pair<TreePos,Int>> = (
            left.arrList().map { it -> it.copy(first=(listOf(ZigZag.L) + it.first)) } +
            right.arrList().map { it -> it.copy(first=(listOf(ZigZag.R) + it.first)) }).toTypedArray()

    return Tree(arr)
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
