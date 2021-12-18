package aoc2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test {

    @Test
    fun test_parseSnailNumber() {
        val leaf = parseSnailNumber("123")
        assertEquals(leaf.print(), "123")

        val tree = parseSnailNumber("[123,44]")
        assertEquals(tree.print(), "[123,44]")

        val tree2 = parseSnailNumber("[123,[44,11]]")
        assertEquals(tree2.print(), "[123,[44,11]]")
    }

    @Test
    fun test_magnitude() {
        val examples = listOf(
            "[[1,2],[[3,4],5]]" to 143,
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" to 1384,
            "[[[[1,1],[2,2]],[3,3]],[4,4]]" to 445,
            "[[[[3,0],[5,3]],[4,4]],[5,5]]" to 791,
            "[[[[5,0],[7,4]],[5,5]],[6,6]]" to 1137,
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]" to 3488,
        )
        examples.forEach { case ->
            val (example, expected) = case
            val tree = parseSnailNumber(example)
            assertEquals(expected, tree.getMagnitue())
        }
    }



    @Test
    fun test_split() {
        assertEquals(parseSnailNumber("[5,6]").arr, parseSnailNumber("11").split().arr)
        assertEquals(parseSnailNumber("[1,[[7,8],10]]").arr, parseSnailNumber("[1,[15,10]]").split().arr)
        assertEquals(parseSnailNumber("[1,[7,[5,5]]]").arr, parseSnailNumber("[1,[7,10]]").split().arr)

        val noSplits = parseSnailNumber("[[3,4],[7,[5,5]]]")
        assertEquals(noSplits, noSplits.split())
    }

    @Test
    fun test_explode() {

        assertEquals(parseSnailNumber("[[[[0,9],2],3],4]").arr, parseSnailNumber("[[[[[9,8],1],2],3],4]").explode().arr)
        assertEquals(parseSnailNumber("[7,[6,[5,[7,0]]]]").arr, parseSnailNumber("[7,[6,[5,[4,[3,2]]]]]").explode().arr)
        assertEquals(parseSnailNumber("[[6,[5,[7,0]]],3]").arr, parseSnailNumber("[[6,[5,[4,[3,2]]]],1]").explode().arr)
        assertEquals(parseSnailNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").arr, parseSnailNumber("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").explode().arr)
        assertEquals(parseSnailNumber("[[3,[2,[8,0]]],[9,[5,[7,0]]]]").arr, parseSnailNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").explode().arr)

    }

    @Test
    fun test_plus() {
        val added = parseSnailNumber("[[[[4,3],4],4],[7,[[8,4],9]]]") + parseSnailNumber("[1,1]")
        val expected = parseSnailNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" )

        assertEquals(expected.arr, added.arr)
    }
}