package aoc2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class Day16Test {


    @Test
    fun packetParserLiteral() {
        val example = "110100101111111000101000".toList().map {it -> it.digitToInt() }
        val (packet, rest) = pPacket(example)
        assertEquals(2021, packet.value)
        assertEquals(listOf(0,0,0), rest)
    }

    @Test
    fun packetParserComplex() {
        val example = "00111000000000000110111101000101001010010001001000000000".toList().map {it -> it.digitToInt() }
        val (packet, rest) = pPacket(example)
        assertEquals(0, packet.value)
        assertTrue(rest.all { it == 0 })
        assertEquals(7, rest.size)
    }


    @ParameterizedTest(name = "Example code {0} should have value {1}")
    @MethodSource("testCases")
    fun part2Examples(hexString: String, expected:Long) {
        val example = hexString.hexToBits()
        val (packet, _) = pPacket(example)
        assertEquals(expected, packet.calcValue())
    }


    private companion object {
        @JvmStatic
        fun testCases() = Stream.of(
            Arguments.of( "C200B40A82", 3),
            Arguments.of( "04005AC33890", 54),
            Arguments.of("880086C3E88112", 7),
            Arguments.of("CE00C43D881120", 9),
            Arguments.of("D8005AC2A8F0", 1),
            Arguments.of("F600BC2D8F", 0),
            Arguments.of("9C005AC2F8F0",  0),
            Arguments.of("9C0141080250320F1802104A08", 1)
        )
    }
}