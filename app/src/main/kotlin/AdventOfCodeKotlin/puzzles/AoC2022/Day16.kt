package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day16 {
    companion object {
        private fun findBestPath(valves: MutableMap<String, Valve>, startingTime: Int): Pair<Long, List<String>> {
            // Associate each valve with an index, to be used in a bitmap of "turned on" valves
            var i = 0
            val indexes = valves.mapValues { i++ }


            val startingPath = Path("AA", startingTime, 0, 0)
            val paths = ArrayDeque(listOf(startingPath))
            val pastPaths = mutableSetOf(startingPath)
            var bestRelief: Long = 0
            var bestReliefValves = 0

            while (paths.isNotEmpty()) {
                val p = paths.removeFirst()
                val valve = valves[p.currentValve]
                valve?.neighbors?.forEach { n ->
                    if (n.value < p.timeRemaining) {
                        // Add a path to neighbor "n" without turning it on
                        Path(
                            n.key,
                            p.timeRemaining - n.value,
                            p.turnedOnValves,
                            p.pressureRelief
                        )
                            .takeIf { !pastPaths.contains(it) }?.let {
                                paths.add(it)
                                pastPaths.add(it)
                            }

                        // Add a path to neighbor "n" where it is turned on
                        Path(
                            n.key,
                            p.timeRemaining - n.value - 1,
                            p.turnedOnValves or (1 shl indexes[n.key]!!),
                            p.pressureRelief + (p.timeRemaining - n.value - 1) * valves[n.key]!!.flowRate
                        ).takeIf { !pastPaths.contains(it) && it.turnedOnValves != p.turnedOnValves }
                            ?.let {
                                paths.add(it)
                                pastPaths.add(it)
                            }
                    } else {
                        if (p.pressureRelief > bestRelief) {
                            bestRelief = p.pressureRelief
                            bestReliefValves = p.turnedOnValves
                        }
                    }
                }
            }

            val bestTurnedOnValves = indexes.mapNotNull { (name, index) ->
                if (bestReliefValves and (1 shl index) > 0) {
                    name
                } else {
                    null
                }
            }

            return (bestRelief to bestTurnedOnValves)
        }

        private fun removeZeroValves(valves: MutableMap<String, Valve>) {
            // Reduce away the 0 value valves
            val splitValves = valves.values.partition { it.flowRate > 0 || it.valveName == "AA" }
            splitValves.second.forEach { zeroValve ->
                valves.remove(zeroValve.valveName)
                zeroValve.neighbors.keys.forEach { m ->
                    zeroValve.neighbors.keys.forEach { n ->
                        if (m < n) {
                            valves[m]!!.neighbors[n] =
                                zeroValve.neighbors[m]!! + zeroValve.neighbors[n]!!
                            valves[n]!!.neighbors[m] =
                                zeroValve.neighbors[m]!! + zeroValve.neighbors[n]!!
                        }
                    }
                    valves[m]!!.neighbors.remove(zeroValve.valveName)
                }
            }
        }

        private fun parseInput(puzzle: PuzzleInputProvider): MutableMap<String, Valve> {
            val valves = mutableMapOf<String, Valve>()

            // Generate all valves
            puzzle.getAsString().forEach {
                Regex("""Valve ([A-Z]{2}) has flow rate=(\d*); tunnels? leads? to valves? (.*)""")
                    .matchEntire(it)!!
                    .destructured
                    .let { (valveName, flowString, neighborList) ->
                        val v = Valve(valveName, flowString.toInt(), neighborList)
                        valves[valveName] = v
                    }
            }
            return valves
        }

        fun part1(puzzle: PuzzleInputProvider): String {

            val valves = parseInput(puzzle)

            removeZeroValves(valves)
            val bestRelief = findBestPath(valves, 30)

            return bestRelief.first.toString()
        }


        fun part2(puzzle: PuzzleInputProvider): String {
            var valves = parseInput(puzzle)

            removeZeroValves(valves)
            val bestRelief = findBestPath(valves, 26)

            valves = parseInput(puzzle)
            bestRelief.second.forEach { name ->
                valves[name]!!.flowRate = 0
            }

            removeZeroValves(valves)
            val nextBestRelief = findBestPath(valves, 26)

            return (bestRelief.first + nextBestRelief.first).toString()
        }
    }
}


fun main() {
    Runner.solve(2022, 16, part1 = Day16::part1)
    Runner.solve(2022, 16, part2 = Day16::part2)
}

class Valve(val valveName: String, var flowRate: Int, tunnelList: String) {
    val neighbors: MutableMap<String, Int>

    init {
        neighbors = tunnelList.split(", ").associateWith { 1 }.toMutableMap()
    }

    override fun toString(): String {
        return "Valve(valveName='$valveName', flowRate=$flowRate, neighbors=$neighbors)"
    }
}

data class Path(
    val currentValve: String,
    val timeRemaining: Int,
    val turnedOnValves: Int,
    val pressureRelief: Long
)