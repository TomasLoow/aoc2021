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
        var num : Tree
        num = parseSnailNumber("11")
        num.split()
        assertEquals("[5,6]", num.print())

        num = parseSnailNumber("[1,[15,10]]")
        num.split()
        assertEquals("[1,[[7,8],10]]", num.print())

        num = parseSnailNumber("[1,[7,10]]")
        num.split()
        assertEquals("[1,[7,[5,5]]]", num.print())

        val noSplits = parseSnailNumber("[[3,4],[7,[5,5]]]")
        noSplits.split()
        assertEquals("[[3,4],[7,[5,5]]]", noSplits.print())
    }

    @Test
    fun test_explode() {

        var num=parseSnailNumber("[[[[[9,8],1],2],3],4]")
        num.explode()
        assertEquals("[[[[0,9],2],3],4]", num.print())
        num=parseSnailNumber("[7,[6,[5,[4,[3,2]]]]]")
        num.explode()
        assertEquals("[7,[6,[5,[7,0]]]]", num.print())
        num=parseSnailNumber("[[6,[5,[4,[3,2]]]],1]")
        num.explode()
        assertEquals("[[6,[5,[7,0]]],3]", num.print())
        num=parseSnailNumber("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        num.explode()
        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", num.print())
        num=parseSnailNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
        num.explode()
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", num.print())

    }

    @Test
    fun test_plus() {
        val added = parseSnailNumber("[[[[4,3],4],4],[7,[[8,4],9]]]") + parseSnailNumber("[1,1]")

        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", added.print())
    }
}