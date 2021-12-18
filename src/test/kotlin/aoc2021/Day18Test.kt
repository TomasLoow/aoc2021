package aoc2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test {

    @Test
    fun test_parseSnailNumber() {
        val leaf = parseSnailNumber("123")
        assertEquals(true, leaf is Leaf)
        assertEquals("123", leaf.print())

        val tree = parseSnailNumber("[123,44]")
        assertEquals(true, tree is Tree)
        assertEquals("[123,44]", tree.print())

        val tree2 = parseSnailNumber("[123,[44,11]]")
        assertEquals(true, tree is Tree)
        assertEquals("[123,[44,11]]", tree2.print())
    }

    @Test
    fun test_magnitude() {
        val examples = listOf(
            "[[1,2],[[3,4],5]]" to 143L,
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" to 1384L,
            "[[[[1,1],[2,2]],[3,3]],[4,4]]" to 445L,
            "[[[[3,0],[5,3]],[4,4]],[5,5]]" to 791L,
            "[[[[5,0],[7,4]],[5,5]],[6,6]]" to 1137L,
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]" to 3488L,
        )
        examples.forEach { case ->
            val (example, expected) = case
            val tree = parseSnailNumber(example)
            assertEquals(expected, tree.magnitue)
        }
    }

}