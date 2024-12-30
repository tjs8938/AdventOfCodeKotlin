package AdventOfCodeKotlin.puzzles.EverybodyCodes2024.day20

import AdventOfCodeKotlin.util.Node
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph

fun main() {
    Day20.part1()
    Day20.part2()
    Day20.part3()
}

class Day20 {
    companion object {
        fun part1() {
            val input = """
                #..............S..............#
                #.............................#
                ###-###-####-##-##-####-###-###
                #.............................#
                ###+###+####-##-##+####-###+###
                #.............................#
                #-###-####-#########-###-###-##
                #.............................#
                ###-###-####-##-##+####-###-###
                #.............................#
                #-###-####-#########-###+###+##
                #.............................#
                ###-###-####-##-##-####-###-###
                #.............................#
                #-###+####-#########-###-###-##
                #.............................#
                ###-###-####+##-##-####-###-###
                #.............................#
                #-###-####-#########-###-###-##
                #.............................#
                #+...+....+....+....+....+...+#
                #+++.+++..+++..+..+++..+++.+++#
                #..+...+....+..+..+....+...+..#
                #..+...+....+..+..+....+...+..#
                #.++..++.+.++.+++.++.+.++..++.#
                #.++..++.+.++.+++.++.+.++..++.#
            """.trimIndent().split("\n").map { it.toList() }

            var start: Node? = null
            val graph = buildGraph(input, ::Node, { it != '#' },
                mapOf('S' to { _, node -> start = node })
            )

            val altDeltas = mapOf('.' to -1, '+' to 1, '-' to -2)
            val bestAlt = mutableMapOf((start!! to start!!) to 1000)
            var toProcess = mutableSetOf(Triple(start!!, start!!, 1000))
            repeat(100) {
                toProcess = toProcess.flatMap { (from, to, alt) ->
                    to.neighbors.mapNotNull { neighbor ->
                        if (neighbor == from || neighbor == start) {
                            null
                        } else {
                            val newAlt = alt + altDeltas[(neighbor as Node).label]!!
                            if (newAlt > bestAlt.getOrDefault(to to neighbor, Int.MIN_VALUE)) {
                                bestAlt[to to neighbor] = newAlt
                                Triple(to, neighbor, newAlt)
                            } else null
                        }
                    }
                }.toMutableSet()
            }

            println(bestAlt.values.maxOrNull())

        }

        fun part2() {

        }

        fun part3() {

        }
    }
}