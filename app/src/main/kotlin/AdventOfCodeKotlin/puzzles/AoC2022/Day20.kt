package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import kotlin.math.abs


class Day20 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val nodes = puzzle.getAsInt().map { Node(it.toLong()) }

            nodes.indices.forEach {
                nodes[it].left = nodes[(it - 1).mod(nodes.size)]
                nodes[it].right = nodes[(it + 1).mod(nodes.size)]
            }

            mixNumbers(nodes)

            return calcAnswer(nodes)
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val nodes = puzzle.getAsInt().map { Node(it.toLong() * 811589153L) }

            nodes.indices.forEach {
                nodes[it].left = nodes[(it - 1).mod(nodes.size)]
                nodes[it].right = nodes[(it + 1).mod(nodes.size)]
            }

            repeat(10) {
                mixNumbers(nodes)
            }

            return calcAnswer(nodes)
        }

        private fun mixNumbers(nodes: List<Node>) {
            nodes.forEach { n ->
                val oldLeft = n.left
                val oldRight = n.right

                val dist = (n.num % (nodes.size - 1).toLong()).toInt()

                if (dist != 0) {
                    oldLeft.right = oldRight
                    oldRight.left = oldLeft

                    val next = n.walk(dist)

                    if (n.num >= 0) {
                        n.left = next
                        n.right = next.right
                    } else {
                        n.right = next
                        n.left = next.left
                    }
                    n.left.right = n
                    n.right.left = n
                }
            }
        }

        private fun calcAnswer(nodes: List<Node>): String {
            val zero = nodes.first { it.num == 0L }
            val oneThousand = zero.walk(1000)
            val twoThousand = oneThousand.walk(1000)
            val threeThousand = twoThousand.walk(1000)

            return (oneThousand.num + twoThousand.num + threeThousand.num).toString()
        }
    }
}


fun main() {

    val example = ExamplePuzzle(
        "1\n" +
            "2\n" +
            "-3\n" +
            "3\n" +
            "-2\n" +
            "0\n" +
            "4"
    )

    assert(Day20.part1(example) == "3")

    Runner.solve(2022, 20, part1 = Day20::part1)

    assert(Day20.part2(example) == "1623178306")
    Runner.solve(2022, 20, part2 = Day20::part2)
}


class Node(val num: Long) {
    lateinit var left: Node
    lateinit var right: Node

    fun walk(n: Int): Node {
        var next = this
        if (n >= 0) {
            repeat(n) { next = next.right }
        } else {
            repeat(abs(n)) { next = next.left }
        }
        return next
    }

    override fun toString(): String {
        return "Node(num=$num, left=${left.num}, right=${right.num})"
    }


}