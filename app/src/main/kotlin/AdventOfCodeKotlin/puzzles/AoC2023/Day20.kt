package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day20 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            // build up all the modules from the input
            val modules = setup(puzzle)

            val loopCounts = mutableListOf<MutableList<Long>>()
            do {
                val signalsToProcess = mutableListOf("button" to "broadcaster" to 0)
                val signalCounts = mutableListOf(0L, 0L)
                loopCounts.add(signalCounts)
                while (signalsToProcess.isNotEmpty()) {
                    val signal = signalsToProcess.removeAt(0)
                    signalCounts[signal.second]++

                    val (source, dest) = signal.first
                    modules[dest]?.let { m ->
                        m.processSignal(source, signal.second)?.let { next ->
                            m.neighbors.forEach { signalsToProcess.add(dest to it to next) }
                        }
                    }
                }
            } while (modules.values.any { !it.hasStartState() } && loopCounts.size < 1000)

            val loopSize = loopCounts.size
            val wholeLoops = 1000 / loopSize
            val remainder = 1000 % loopSize

            var lowTotal = 0L
            var highTotal = 0L
            var lowLoop = 0L
            var highLoop = 0L
            loopCounts.forEachIndexed { index, longs ->
                lowLoop += longs[0]
                highLoop += longs[1]
                if (index < remainder) {
                    lowTotal += longs[0]
                    highTotal += longs[1]
                }
            }
            lowTotal += lowLoop * wholeLoops
            highTotal += highLoop * wholeLoops
            return (lowTotal * highTotal).toString()
        }

        private fun setup(puzzle: PuzzleInputProvider): MutableMap<String, Module> {
            val modules = mutableMapOf<String, Module>()
            val inputs = mutableMapOf<String, MutableList<String>>()
            puzzle.getAsString().forEach { line ->
                val splits = line.split(" -> ")
                val neighbors = splits[1].split(", ")
                var name = splits[0]
                if (name == "broadcaster") {
                    modules[name] = Broadcaster(name, neighbors)
                } else if (name.startsWith("%")) {
                    name = name.removePrefix("%")
                    modules[name] = FlipFlop(name, neighbors)
                } else {
                    name = name.removePrefix("&")
                    modules[name] = Conjunction(name, neighbors)
                }
                neighbors.forEach { n ->
                    val m = inputs.getOrPut(n) { mutableListOf() }
                    m.add(name)
                }
            }

            // Set inputs for Conjunction modules
            modules.values.filterIsInstance<Conjunction>().forEach { con ->
                con.inputs = inputs[con.name]!!.associateWith { 0 }.toMutableMap()
            }
            return modules
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            // build up all the modules from the input
            val modules = setup(puzzle)

            val rxNeighbor = modules.values.first { it.neighbors.contains("rx") }
            val loopCounts = mutableMapOf<String, Long>()
            var loopCount = 0L
            do {
                val signalsToProcess = mutableListOf("button" to "broadcaster" to 0)
                val signalCounts = mutableListOf(0L, 0L)
                loopCount++
                while (signalsToProcess.isNotEmpty()) {
                    val signal = signalsToProcess.removeAt(0)
                    signalCounts[signal.second]++

                    val (source, dest) = signal.first
                    if (dest == rxNeighbor.name && signal.second == 1 && !loopCounts.containsKey(source)) {
                        loopCounts[source] = loopCount
                    }
                    modules[dest]?.let { m ->
                        m.processSignal(source, signal.second)?.let { next ->
                            m.neighbors.forEach { signalsToProcess.add(dest to it to next) }
                        }
                    }
                }
            } while (loopCounts.size < (rxNeighbor as Conjunction).inputs.size)

            return findLCMOfListOfNumbers(loopCounts.values.toList()).toString()
        }
    }

    abstract class Module(val name: String, val neighbors: List<String>) {
        abstract fun hasStartState(): Boolean
        abstract fun processSignal(sender: String, signal: Int): Int?
    }

    class Broadcaster(name: String, neighbors: List<String>) : Module(name, neighbors) {
        override fun hasStartState(): Boolean {
            return true
        }

        override fun processSignal(sender: String, signal: Int): Int? {
            return signal
        }
    }

    class FlipFlop(name: String, neighbors: List<String>) : Module(name, neighbors) {

        var state = 0
        override fun hasStartState(): Boolean {
            return state == 0
        }

        override fun processSignal(sender: String, signal: Int): Int? {
            return if (signal == 0) {
                state = (state + 1) % 2
                state
            } else {
                null
            }
        }

    }

    class Conjunction(name: String, neighbors: List<String>) : Module(name, neighbors) {

        lateinit var inputs: MutableMap<String, Int>
        override fun hasStartState(): Boolean {
            return inputs.values.all { it == 0 }
        }

        override fun processSignal(sender: String, signal: Int): Int? {
            inputs[sender] = signal
            return if (inputs.values.all { it == 1 }) {
                0
            } else {
                1
            }
        }

    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """.trimIndent())

    Day20.part1(ex1).let {
        println(it)
        assert(it == "32000000")
    }

    val ex2 = ExamplePuzzle("""
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent())
    Day20.part1(ex2).let {
        println(it)
        assert(it == "11687500")
    }
//    Runner.solve(2023, 20, part1 = Day20::part1)
    Runner.solve(2023, 20, part2 = Day20::part2)
}