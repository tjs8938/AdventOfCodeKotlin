package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.MemoizedFunction
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize


class Day24 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {

            val (constantsInput, functionInput) = puzzle.get().split("\n\n")
            val functions = mutableMapOf<String, (() -> Int)>()

            constantsInput.split('\n').forEach { constant ->
                val (name, value) = constant.split(": ")
                functions[name] = { value.toInt() }
            }

            functionInput.split('\n').forEach { functionString ->
                val (arg1, op, arg2, _, target) = functionString.split(" ")
                functions[target] = when (op) {
                    "AND" -> {
                        { functions[arg1]!!() and functions[arg2]!!() }
                    }

                    "OR" -> {
                        { functions[arg1]!!() or functions[arg2]!!() }
                    }

                    "XOR" -> {
                        { functions[arg1]!!() xor functions[arg2]!!() }
                    }

                    else -> {
                        throw IllegalArgumentException("Unknown operator: $op")
                    }
                }
            }

            var result = 0L
            functions.keys.filter { it.startsWith('z') }
                .sortedByDescending { it.removePrefix("z").toInt() }
                .forEach { z ->
                    result *= 2
                    result += functions[z]!!()
                }
            return result
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val (constantsInput, functionInput) = puzzle.get().split("\n\n")
            val functions = mutableMapOf<String, (() -> Int)>()

            constantsInput.split('\n').forEach { constant ->
                val (name, value) = constant.split(": ")
                functions[name] = { value.toInt() }
            }

            val inputs = mutableMapOf<String, Triple<String, String, String>>()
            functionInput.split('\n').forEach { functionString ->
                val (arg1, op, arg2, _, target) = functionString.split(" ")
                functions[target] = when (op) {
                    "AND" -> {
                        { functions[arg1]!!() and functions[arg2]!!() }
                    }

                    "OR" -> {
                        { functions[arg1]!!() or functions[arg2]!!() }
                    }

                    "XOR" -> {
                        { functions[arg1]!!() xor functions[arg2]!!() }
                    }

                    else -> {
                        throw IllegalArgumentException("Unknown operator: $op")
                    }
                }
                inputs[target] = Triple(arg1, arg2, op)
            }

            val allInputs: MemoizedFunction<String, List<String>> = memoize { name: String ->
                inputs[name]!!.first.let {
                    if (it.startsWith("x") || it.startsWith("y")) {
                        listOf(it)
                    } else {
                        this(it).toMutableList().apply { add(it) }
                    }
                } + inputs[name]!!.second.let {
                    if (it.startsWith("x") || it.startsWith("y")) {
                        listOf(it)
                    } else {
                        this(it).toMutableList().apply { add(it) }
                    }
                }
            }

            var x = 0L
            functions.keys.filter { it.startsWith('x') }
                .sortedByDescending { it.removePrefix("x").toInt() }
                .forEach { z ->
                    x *= 2
                    x += functions[z]!!()
                }

            var y = 0L
            functions.keys.filter { it.startsWith('y') }
                .sortedByDescending { it.removePrefix("y").toInt() }
                .forEach { z ->
                    y *= 2
                    y += functions[z]!!()
                }

            var result = 0L
            functions.keys.filter { it.startsWith('z') }
                .sortedByDescending { it.removePrefix("z").toInt() }
                .forEach { z ->
                    result *= 2
                    result += functions[z]!!()
                }

            val badZeeSignals =
                inputs.filterKeys { it.startsWith("z") }.filterValues { it.third != "XOR" }.keys.filterNot { it == "z45" }.toMutableList()

            val badSimpleXorSignals = inputs.filterValues { it.third == "XOR" && (it.first.startsWith("x") || it.first.startsWith("y")) }
                .keys.filter { (it.startsWith("z") && it != "z00") || inputs.values.none { parent -> parent.third == "XOR" && (parent.first == it || parent.second == it) } }.filterNot { it in listOf("z00") }
            val badSimpleAndSignals = inputs.filterValues { it.third == "AND" && (it.first.startsWith("x") || it.first.startsWith("y")) && it.first != "x00" && it.first != "y00" }
                .keys.filter { (it.startsWith("z") && it != "z00") || inputs.values.none { parent -> parent.third == "OR" && (parent.first == it || parent.second == it) } }
            val badCompAndSignals = inputs.filterValues { it.third == "AND" && !it.first.startsWith("x") && !it.first.startsWith("y") }
                .keys.filter { (it.startsWith("z") && it != "z00") || inputs.values.none { parent -> parent.third == "OR" && (parent.first == it || parent.second == it) } }
            val badOrSignals = inputs.filterValues { it.third == "OR"  }
                .keys.filter { (it.startsWith("z") && it != "z00") || inputs.values.none { parent -> parent.third == "XOR" && (parent.first == it || parent.second == it) } }.filter { it != "z45" }
            val badCompXorSignals = inputs.filterValues { it.third == "XOR" && !it.first.startsWith("x") && !it.first.startsWith("y") }
                .keys.filter { !it.startsWith("z") }
//            badSignals.add("gdd")
//
//            inputs.swap("gdd", "z05")
//            functions.swap("gdd", "z05")
//            allInputs.clear()
//            inputs.swap("cwt", "z09")
//            functions.swap("cwt", "z09")
//            badSignals.add("cwt")
//            allInputs.clear()
//            inputs.swap("pqt", "z37")
//            functions.swap("pqt", "z37")
//            badSignals.add("pqt")
//            allInputs.clear()
//            inputs.swap("css", "jmv")
//            functions.swap("css", "jmv")
//            badSignals.add("css")
//            badSignals.add("jmv")
//            allInputs.clear()
//            result = 0L
//            functions.keys.filter { it.startsWith('z') }
//                .sortedByDescending { it.removePrefix("z").toInt() }
//                .forEach { z ->
//                    result *= 2
//                    result += functions[z]!!()
//                }
            println(badZeeSignals)
            println(badSimpleXorSignals)
            println(badSimpleAndSignals)
            println(badCompAndSignals)
            println(badOrSignals)
            println(badCompXorSignals)

            val badSignals = (badZeeSignals + badSimpleXorSignals + badSimpleAndSignals + badCompAndSignals + badOrSignals + badCompXorSignals).toSet().toList().sorted()
            println(badSignals.joinToString(","))
            return badSignals.joinToString(",")
        }
    }
}

private fun <K, V> MutableMap<K, V>.swap(k: K, k1: K) {
    val temp = this[k]
    this[k] = this[k1]!!
    this[k1] = temp!!
}


fun main() {


//    Runner.solve(2024, 24, part1 = Day24::part1)


    Runner.solve(2024, 24, part2 = Day24::part2)
}