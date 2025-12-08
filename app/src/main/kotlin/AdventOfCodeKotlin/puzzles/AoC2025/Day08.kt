package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Combinatorics
import kotlin.math.pow
import kotlin.math.sqrt

class Day08 {
    companion object {

        class Connection(val p1: Triple<Int, Int, Int>, val p2: Triple<Int, Int, Int>) : Comparable<Connection> {

            fun distance(): Double {
                return sqrt(
                    (p1.first - p2.first).toDouble().pow(2.0) +
                            (p1.second - p2.second).toDouble().pow(2.0) +
                            (p1.third - p2.third).toDouble().pow(2.0)
                )
            }

            override fun compareTo(other: Connection): Int {
                return (this.distance() - other.distance()).toInt()
            }
        }

        fun part1(puzzle: PuzzleInputProvider): String {
            val boxes =
                puzzle.getAsString().map { it.split(",").map { it.toInt() }.let { Triple(it[0], it[1], it[2]) } }
            val connections = Combinatorics.combinations(boxes, 2).map { Connection(it[0], it[1]) }.sorted()
            val circuits = mutableListOf<MutableSet<Triple<Int, Int, Int>>>()

            connections.take(1000).forEach { connection ->
                val setsContainingP1 = circuits.firstOrNull { it.contains(connection.p1) }
                val setsContainingP2 = circuits.firstOrNull { it.contains(connection.p2) }

                when {
                    setsContainingP1 == null && setsContainingP2 == null -> {
                        val newSet = mutableSetOf(connection.p1, connection.p2)
                        circuits.add(newSet)
                    }

                    setsContainingP1 != null && setsContainingP2 == null -> {
                        setsContainingP1.add(connection.p2)
                    }

                    setsContainingP1 == null && setsContainingP2 != null -> {
                        setsContainingP2.add(connection.p1)
                    }

                    setsContainingP1 != null && setsContainingP2 != null -> {
                        if (setsContainingP1 != setsContainingP2) {
                            val set1 = setsContainingP1
                            val set2 = setsContainingP2
                            set1.addAll(set2)
                            circuits.remove(set2)
                        }
                    }
                }
            }
            return circuits.sortedWith(compareBy<MutableSet<Triple<Int, Int, Int>>> { it.size }.reversed()).take(3)
                .fold(1, { acc, set -> acc * set.size }).toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val boxes =
                puzzle.getAsString().map { it.split(",").map { it.toInt() }.let { Triple(it[0], it[1], it[2]) } }
            val connections = Combinatorics.combinations(boxes, 2).map { Connection(it[0], it[1]) }.sorted()
            val circuits = mutableListOf<MutableSet<Triple<Int, Int, Int>>>()

            var index = 0
            while (circuits.isEmpty() || circuits.first().size != boxes.size ) {
                val connection = connections[index]
                val setsContainingP1 = circuits.firstOrNull { it.contains(connection.p1) }
                val setsContainingP2 = circuits.firstOrNull { it.contains(connection.p2) }

                when {
                    setsContainingP1 == null && setsContainingP2 == null -> {
                        val newSet = mutableSetOf(connection.p1, connection.p2)
                        circuits.add(newSet)
                    }

                    setsContainingP1 != null && setsContainingP2 == null -> {
                        setsContainingP1.add(connection.p2)
                    }

                    setsContainingP1 == null && setsContainingP2 != null -> {
                        setsContainingP2.add(connection.p1)
                    }

                    setsContainingP1 != null && setsContainingP2 != null -> {
                        if (setsContainingP1 != setsContainingP2) {
                            val set1 = setsContainingP1
                            val set2 = setsContainingP2
                            set1.addAll(set2)
                            circuits.remove(set2)
                        }
                    }
                }
                index++
            }
            return connections[index-1].let { it.p1.first.toLong() * it.p2.first.toLong() }.toString()
        }
    }
}

fun main() {
    Runner.solve(2025, 8, 1, Day08::part1)

    val ex = ExamplePuzzle("""
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
    """.trimIndent(), "25272")
    Runner.solve(2025, 8, 2, Day08::part2, ex)
}

