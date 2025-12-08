package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import kotlin.random.Random


class Day25 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val connections = mutableMapOf<String, MutableList<String>>()
            val regex = Regex("""(.*): (.*)""")
            puzzle.getAsString().forEach { s ->
                regex.matchEntire(s)!!.destructured
                    .let { (left, neighbors) ->
                        neighbors.split(" ").forEach { n ->
                            if (!connections.getOrPut(left) { mutableListOf() }.contains(n)) {
                                connections[left]!!.add(n)
                            }
                            if (!connections.getOrPut(n) { mutableListOf() }.contains(left)) {
                                connections[n]!!.add(left)
                            }
                        }
                    }
            }

            var collapsingConnections = mutableMapOf<String, MutableList<String>>()
            do {
                collapsingConnections = mutableMapOf<String, MutableList<String>>()
                connections.forEach {
                    collapsingConnections[it.key] = mutableListOf()
                    collapsingConnections[it.key]!!.addAll(it.value)
                }
                while (collapsingConnections.size > 2) {

                    // get a random node
                    val randomIndex = Random.nextInt(collapsingConnections.size)
                    val randomKey = collapsingConnections.keys.toList()[randomIndex]

                    // get a random neighbor of that node
                    val randomNeigborIndex = Random.nextInt(collapsingConnections[randomKey]!!.size)
                    val randomNeighbor = collapsingConnections[randomKey]!![randomNeigborIndex]

                    // create a new node that concats their names
                    val newNode = randomKey + randomNeighbor
                    collapsingConnections[newNode] = mutableListOf()

                    // redirect all edges from randomKey to the new node (except randomNeighbor)
                    collapsingConnections[randomKey]!!.filterNot { it == randomNeighbor }.forEach {
                        collapsingConnections[it]!!.remove(randomKey)
                        collapsingConnections[it]!!.add(newNode)
                        collapsingConnections[newNode]!!.add(it)
                    }

                    // redirect all edges from randomNeighbor to the new node (except randomKey)
                    collapsingConnections[randomNeighbor]!!.filterNot { it == randomKey }.forEach {
                        collapsingConnections[it]!!.remove(randomNeighbor)
                        collapsingConnections[it]!!.add(newNode)
                        collapsingConnections[newNode]!!.add(it)
                    }

                    // remove the two collapsed nodes
                    collapsingConnections.remove(randomKey)
                    collapsingConnections.remove(randomNeighbor)
                }
            } while (collapsingConnections.values.any { it.size != 3 })
            println(collapsingConnections)


            return collapsingConnections.map { it.key.length / 3 }.reduce { acc, i -> acc * i }.toString()
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        jqt: rhn xhk nvd
        rsh: frs pzl lsr
        xhk: hfx
        cmg: qnr nvd lhk bvb
        rhn: xhk bvb hfx
        bvb: xhk hfx
        pzl: lsr hfx nvd
        qnr: nvd
        ntq: jqt hfx bvb xhk
        nvd: lhk
        lsr: lhk
        rzs: qnr cmg lsr rsh
        frs: qnr lhk lsr
    """.trimIndent()
    )

    val part1 = Day25.part1(ex1)
    println(part1)
    assert(part1 == "54")

    Runner.solve(2023, 25, part1 = Day25::part1)
}