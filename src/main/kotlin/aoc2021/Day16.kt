package aoc2021

import DailyProblem
import parserCombinators.*
import java.io.File
import kotlin.time.ExperimentalTime

private fun parseHexFileToBinary(path: String): List<Int> {
    return File(path).readLines().first().hexToBits()
}

fun String.hexToBits(): List<Int> {
    return flatMap { c ->
        when(c) {
            '0' -> listOf(0,0,0,0)
            '1' -> listOf(0,0,0,1)
            '2' -> listOf(0,0,1,0)
            '3' -> listOf(0,0,1,1)
            '4' -> listOf(0,1,0,0)
            '5' -> listOf(0,1,0,1)
            '6' -> listOf(0,1,1,0)
            '7' -> listOf(0,1,1,1)
            '8' -> listOf(1,0,0,0)
            '9' -> listOf(1,0,0,1)
            'A' -> listOf(1,0,1,0)
            'B' -> listOf(1,0,1,1)
            'C' -> listOf(1,1,0,0)
            'D' -> listOf(1,1,0,1)
            'E' -> listOf(1,1,1,0)
            'F' -> listOf(1,1,1,1)
            else -> throw Exception()
        }
    }

}

fun List<Int>.bitsToInt() : Int {
    return fold(0){ acc, i -> acc*2+i}
}

fun pFixLenghtBinaryInt(numBits: Int): Parser<Int, Int> {
    return { input ->
        if (input.size < numBits) throw NoMatchException("Too few elements to parse $numBits-length binary number")
        val num = input.take(numBits).bitsToInt()
        Pair(num, input.drop(numBits))
    }
}

fun pBit(input: List<Int>): ParseResult<Int, Boolean> {
    return Pair(input.first() == 1, input.drop(1))
}

fun pPentaBytes(input: List<Int>): ParseResult<Int, Long> {
    var res = 0L
    var eatenBytes = 0
    for (chunk in input.asSequence().chunked(5)) {
        val lastChunk = (chunk.first() == 0)

        res = 16*res + chunk.drop(1).bitsToInt()
        eatenBytes += 5
        if (lastChunk) break
    }
    return Pair(res, input.drop(eatenBytes))
}

fun pPacket(input: List<Int>): ParseResult<Int, Packet> {
    val headerParser = pPair(
        pFixLenghtBinaryInt(3),
        pFixLenghtBinaryInt(3))
    val (header,rest1) = headerParser(input)
    val (version, type) = header

    if (type == 4) {

        val (value, rest3) = pPentaBytes(rest1)
        return Pair(Packet(version=version, type=type, value=value), rest3)

    } else {

        val (lenghtType, rest2) = pBit(rest1)

        if (lenghtType) {
            val (numChildren, rest4) = pFixLenghtBinaryInt(11)(rest2)

            val parseChildren  = pManySpecificCount(numChildren) { it: List<Int> -> pPacket(it) }
            val (children : List<Packet>, rest5) = parseChildren(rest4)

            return Pair(Packet(version=version, type=type, value=0, children=children), rest5)

        } else {

            val (numBytes, rest4) = pFixLenghtBinaryInt(15)(rest2)

            val parseChildren  = pManySpecificTokenCount(numBytes) { it: List<Int> -> pPacket(it) }
            val (children : List<Packet>, rest5) = parseChildren(rest4)

            return Pair(Packet(version=version, type=type, value=0, children=children), rest5)
        }
    }
}

class Packet(val version: Int, val type: Int, val value: Long, val children: List<Packet> = emptyList()) {
    fun sumVersions(): Int {
        return version + children.map {it.sumVersions() }.sum()
    }

    fun calcValue(): Long {
        return when(type) {
            0 -> children.map { it.calcValue() }.sum()
            1 -> children.map { it.calcValue() }.reduce { acc, i -> acc*i }
            2 -> children.map { it.calcValue() }.minOf { it }
            3 -> children.map { it.calcValue() }.maxOf { it }
            4 -> value
            5 -> if (children[0].calcValue() > children[1].calcValue()) 1 else 0
            6 -> if (children[0].calcValue() < children[1].calcValue()) 1 else 0
            7 -> if (children[0].calcValue() == children[1].calcValue()) 1 else 0
            else -> throw Exception("Unknown type")
        }

    }

}

class Day16Problem(override val inputFilePath: String) : DailyProblem {
    override val number: Int = 16
    override val name: String = "Packet Decoder"

    private lateinit var packet: Packet

    override fun commonParts() {
        val binaryString: List<Int> = parseHexFileToBinary(inputFilePath)
        val (parsedPacket, _) = pPacket(binaryString)
        this.packet = parsedPacket
    }
    override fun part1(): Long {
        return packet.sumVersions().toLong()
    }

    override fun part2(): Long {
        return packet.calcValue()
    }
}

val day16Problem = Day16Problem("input/aoc2021/day16.txt")
@OptIn(ExperimentalTime::class)
fun main() {
    day16Problem.runBoth(100)
}
