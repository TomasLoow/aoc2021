package aoc2021

import DailyProblem
import parserCombinators.*
import java.io.File

interface ITree {
    val isLeaf: Boolean

    val magnitue: Long

    fun print(): String

    operator fun plus(t2: ITree): Tree {
        return Tree(left = this, right = t2)
    }
}


class Tree(val left: ITree, val right: ITree) : ITree{
    override  val isLeaf = false
    override fun print(): String = "[${left.print()},${right.print()}]"

    override val magnitue: Long
        get() {
            return 3 * left.magnitue + 2 * right.magnitue
        }
}

class Leaf(val value: Int) : ITree {
    override val isLeaf = true
    override fun print(): String = value.toString()

    override val magnitue: Long
        get() = value.toLong()
}



fun parseSnailNumber(str: String) : ITree {
    val pLeftParen = pToken('[')
    val pRightParen = pToken(']')
    val pComma = pToken(',')

    val pLeaf: Parser<Char, ITree> = pApplyFun(::pInt, ::Leaf)

    fun parser(input: List<Char>) : ParseResult<Char, ITree> {
        val p: Parser<Char,ITree> = pOneOf(
            pLeaf,
            pLeftParen
                .thenDo(pCombineTwo(
                    ::parser.thenIgnore(pComma),
                    ::parser,
                    ::Tree))
                .thenIgnore(pRightParen)
        )
        return p(input)
    }
    val (res, _) = parser(str.toList())
    return res
}

fun parseSnailfishNumbersFile(path: String): List<ITree> {
    return File(path).readLines().map(::parseSnailNumber)
}

class Day18Problem(override val inputFilePath: String) : DailyProblem {
    private lateinit var parsed: List<ITree>
    override val number = 18
    override val name= "Snailfish"

    override fun commonParts() {
        parsed = parseSnailfishNumbersFile(inputFilePath)
        parsed.forEach{println(it.print())}
    }

    override fun part1(): Long {
        parsed.first()+parsed.last()
        TODO("Not yet implemented")
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }
}

fun main() {
    Day18Problem("input/aoc2021/day18.txt").commonParts()
}
