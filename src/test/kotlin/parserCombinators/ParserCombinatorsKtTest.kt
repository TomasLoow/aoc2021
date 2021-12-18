package parserCombinators

import aoc2021.pFixLenghtBinaryInt
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class ParserCombinatorsKtTest {


    @Test
    fun parseManySpecificCount() {
        val example = "11100111011".toList().map {it -> it.digitToInt() }
        val parser = pManySpecificCount(3, pFixLenghtBinaryInt(3))
        val (res, rest) = parser(example)

        Assertions.assertEquals(listOf(7, 1, 6), res)
        Assertions.assertEquals(listOf(1, 1), rest)
    }


    @Test
    fun parseManySpecificTokenCount() {
        val example = "11100111011000110".toList().map {it -> it.digitToInt() }
        val parser = pManySpecificTokenCount(16, pFixLenghtBinaryInt(4))
        val (res, rest) = parser(example)

        Assertions.assertEquals(listOf(14, 7, 6, 3), res)
        Assertions.assertEquals(listOf(0), rest)
    }


    @Test
    fun oneOfBinary() {
        val parser = pOneOf(pFixLenghtBinaryInt(5), pFixLenghtBinaryInt(3))
        val example1 = listOf(1,1,0,1,1)  // 27
        val (res1, _) = parser(example1)
        Assertions.assertEquals(27, res1)
        val example2 = listOf(1,1,0,1)  // 27
        val (res2, _) = parser(example2)
        Assertions.assertEquals(6, res2)
    }

    @Test
    fun oneOfStrings() {
        val fooParser = pLiteral<Char>("foo".toList())
        val barParser = pLiteral<Char>("bar".toList())
        val parser = pOneOf(fooParser, barParser)

        val (_, rest) = parser("foo is a good variable name".toList())
        Assertions.assertEquals(" is a good variable name".toList(), rest)

        val (_, rest2) = parser("bar is a good variable name".toList())
        Assertions.assertEquals(" is a good variable name".toList(), rest2)

        assertFailsWith<NoMatchException>(
            block = {
                parser("qux is a bit silly".toList())
            }
        )
    }

    @Test
    fun testParseList() {
        val pfirst = (pToken('(') thenDo ::pInt)
        val pmid = (pToken(',') thenDo ::pInt)
        val plast = (pToken(',') thenDo ::pInt) thenIgnore pToken(')')

        val pCommaList = pList(listOf(pfirst, pmid, pmid, plast))

        val (res, _) = pCommaList("(13,4,7,99)".toList())
        assertEquals(listOf(13,4,7,99), res)
    }

    @Test
    fun parseInt() {
        val (i, _) = pInt("1729".toList())
        Assertions.assertEquals(1729, i)

        assertFailsWith<NoMatchException>(
            block = {
                pInt("bob".toList())
            }
        )
    }

    @Test
    fun parenthesizedInt() {

        val parenthesizedInt : Parser<Char, Int> = (pToken('(') thenDo ::pInt) thenIgnore pToken(')')

        val (i, _) = parenthesizedInt("(172)".toList())
        Assertions.assertEquals(172, i)

        assertFailsWith<NoMatchException>(
            block = {
                parenthesizedInt("123".toList())
            }
        )
        assertFailsWith<NoMatchException>(
            block = {
                parenthesizedInt("(xyz)".toList())
            }
        )

    }

}