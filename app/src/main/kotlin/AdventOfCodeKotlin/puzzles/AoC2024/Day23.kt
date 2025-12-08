package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.puzzles.AoC2023.pop
import AdventOfCodeKotlin.util.Adjacent
import AdventOfCodeKotlin.util.Combinatorics.Companion.combinations


class Day23 {
    companion object {

        class Computer(val label: String) : Adjacent(), Comparable<Computer> {
            override fun compareTo(other: Computer): Int {
                return label.compareTo(other.label)
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Computer

                return label == other.label
            }

            override fun hashCode(): Int {
                return label.hashCode()
            }

            override fun toString(): String {
                return "Computer(label='$label' |n|= ${neighbors.size})"
            }
        }

        fun part1(puzzle: PuzzleInputProvider): Any {

            val computers = mutableMapOf<String, Computer>()
            val startsWithTee = mutableSetOf<Computer>()
            puzzle.getAsString().map { it.split("-") }.forEach { (a, b) ->
                val compA = computers.getOrPut(a) { Computer(a) }
                val compB = computers.getOrPut(b) { Computer(b) }
                compA.addNeighbor(compB)
                if (a.startsWith("t")) {
                    startsWithTee.add(compA)
                }
                if (b.startsWith("t")) {
                    startsWithTee.add(compB)
                }
            }

            val teeCycles = mutableSetOf<String>()

            startsWithTee.forEach { tee ->
                val neighborPairs = combinations(tee.neighbors, 2)
                neighborPairs.forEach { (n1, n2) ->
                    if (n1.neighbors.contains(n2)) {
                        val cycleName = listOf(tee, n1 as Computer, n2 as Computer).sorted().joinToString("-")
                        teeCycles.add(cycleName)
                    }
                }
            }


            return teeCycles.size
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val computers = mutableMapOf<String, Computer>()
            val cycles = mutableSetOf<String>()
            puzzle.getAsString().map { it.split("-") }.forEach { (a, b) ->
                val compA = computers.getOrPut(a) { Computer(a) }
                val compB = computers.getOrPut(b) { Computer(b) }
                compA.addNeighbor(compB)
                cycles.add(listOf(compA, compB).sorted().joinToString(",") { it.label })
            }

            while (cycles.size > 1) {
                val cycle = cycles.pop().split(",").map { computers[it]!! }
                val intersection = cycle.map { it.neighbors }.reduce { acc, list -> acc.intersect(list).toMutableList() }
                cycles.addAll(intersection.map { (cycle + it as Computer).sorted().joinToString(",") { it.label } })
            }

            return cycles.first()
        }
    }
}


fun main() {


    val ex1 = ExamplePuzzle(
        """
        kh-tc
        qp-kh
        de-cg
        ka-co
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        yn-cg
        kh-ub
        ta-co
        de-co
        tc-td
        tb-wq
        wh-td
        ta-ka
        td-qp
        aq-cg
        wq-ub
        ub-vc
        de-ta
        wq-aq
        wq-vc
        wh-yn
        ka-de
        kh-ta
        co-tc
        wh-qp
        tb-vc
        td-yn
    """.trimIndent()
    )

//    Day23.part1(ex1).let {
//        println(it)
//        if (it != 7) throw AssertionError()
//    }
//    Runner.solve(2024, 23, part1 = Day23::part1)

    Day23.part2(ex1).let {
        println(it)
        if (it != "co,de,ka,ta") throw AssertionError()
    }
    Runner.solve(2024, 23, part2 = Day23::part2)
}