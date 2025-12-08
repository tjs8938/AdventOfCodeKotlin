package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day13 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.get().split("\n\n").map {
                it.split("\n")
                    .map(::parseInput)
            }.map { it.let { (p1, p2) -> Packet.compare(p1, p2) } }
                .mapIndexed { index, b ->
                    if (b < 0) {
                        index + 1
                    } else {
                        0
                    }
                }
                .sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {

            val inputs = puzzle.getAsString().filter { it.isNotEmpty() }.toMutableList()
                .also { it.add("[[2]]") }
                .also { it.add("[[6]]") }
                .map(::parseInput)
                .sorted()
                .map { it.toString() }

            val two = inputs.indexOf("[[2]]") + 1
            val six = inputs.indexOf("[[6]]") + 1
            return (two * six).toString()
        }
    }
}


fun main() {

    val example = ExamplePuzzle(
        "[1,1,3,1,1]\n" +
            "[1,1,5,1,1]\n" +
            "\n" +
            "[[1],[2,3,4]]\n" +
            "[[1],4]\n" +
            "\n" +
            "[9]\n" +
            "[[8,7,6]]\n" +
            "\n" +
            "[[4,4],4,4]\n" +
            "[[4,4],4,4,4]\n" +
            "\n" +
            "[7,7,7,7]\n" +
            "[7,7,7]\n" +
            "\n" +
            "[]\n" +
            "[3]\n" +
            "\n" +
            "[[[]]]\n" +
            "[[]]\n" +
            "\n" +
            "[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
            "[1,[2,[3,[4,[5,6,0]]]],8,9]"
    )
    assert(Day13.part1(example) == "13")

//    Runner.solve(2022, 13, part1 = Day13::part1)
    Runner.solve(2022, 13, part2 = Day13::part2)
}

fun parseInput(input: String): Packet {
    assert(input.first() == '[' && input.last() == ']')
    val packet = Packet(input)
    var data = input.substring(1 until input.length - 1)

    while (data.isNotEmpty()) {
        data = data.removePrefix(",")
        if (data[0] == '[') {
            // new list
            var level = 0
            var index = 1
            while (level > 0 || data[index] != ']') {
                when (data[index]) {
                    '[' -> {
                        level++
                    }
                    ']' -> {
                        level--
                    }
                    else -> {}
                }
                index++
            }
            packet.items.add(parseInput(data.substring(0..index)))
            data = data.removeRange(0..index)
        } else {
            val comma = data.indexOf(',')
            val intString = if (comma > 0) {
                data.substring(0 until comma)
            } else {
                data
            }
            packet.items.add(IntPacket(intString))
            data = if (comma > 0) {
                data.substring(comma + 1)
            } else {
                ""
            }
        }
    }



    return packet
}

open class Packet(val strRep: String) : Comparable<Packet> {
    var items = mutableListOf<Packet>()

    open fun isInt() = false
    open fun getAsList() = items
    open fun getAsInt(): Int {
        throw UnsupportedOperationException()
    }

    companion object {
        fun compare(p1: Packet, p2: Packet): Int {
            if (p1.isInt() && p2.isInt()) {
                return p1.getAsInt() - p2.getAsInt()
            } else {

                p1.getAsList().forEachIndexed { index, packet ->
                    if (index == p2.getAsList().size) {
                        return 1
                    } else {
                        val result = compare(packet, p2.getAsList()[index])
                        if (result != 0) {
                            return result
                        }
                    }
                }
                return p1.getAsList().size - p2.getAsList().size
            }
        }
    }

    override fun compareTo(other: Packet): Int {
        return compare(this, other)
    }

    override fun toString(): String {
        return strRep
    }
}

class IntPacket(strRep: String) : Packet(strRep) {
    private val value: Int

    init {
        value = strRep.toInt()
    }
    override fun isInt(): Boolean {
        return true
    }

    override fun getAsList(): MutableList<Packet> {
        return mutableListOf(this)
    }

    override fun getAsInt(): Int {
        return value
    }

}
