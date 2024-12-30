package year2024.day08

import year2024.day07.Day07
import kotlin.math.ceil

fun main() {
    Day08.part1()
    Day08.part2()
    Day08.part3()
}

class Day08 {
    companion object {
        fun part1() {
            val input = """
                4099027
            """.trimIndent().toInt()

            val side = ceil(Math.sqrt(input.toDouble())).toInt()
            val base = side * 2 - 1
            val area = side * side
            println((area - input) * base)
        }

        fun part2() {
            val input = 375

            var width = 1
            var thickness = 1
            var blocks = 1

            while (blocks < 20240000) {
                thickness = (thickness * input) % 1111
                width = width + 2
                blocks += thickness * width
            }

            println((blocks - 20240000) * width)
        }

        fun part3() {
            val HIGH_PRIESTS = 904813
            val ACOLYTES = 10
            val AVAILABLE_BLOCKS = 202400000

            var thickness = 1L
            var blocks = 1L
            var width = 1L

            val thicknesses = mutableListOf(1L)

            while (blocks < AVAILABLE_BLOCKS) {
                thickness = ((thickness * HIGH_PRIESTS) % ACOLYTES) + ACOLYTES
                width += 2
                blocks += (thickness * width)
                thicknesses.add(thickness)

                var prevHeight = thicknesses.last()
                var totalRemove = 0L
                (0 until thicknesses.size - 1).reversed().forEach { i ->
                    val height = prevHeight + thicknesses[i]
                    val remove = (height * width * HIGH_PRIESTS) % ACOLYTES
                    totalRemove += if (i > 0) {
                        remove * 2
                    } else {
                        remove
                    }
                    prevHeight = height
                }

//                val layer = (width + 1) / 2
//                if (part3_answers.contains(layer)) {
//                    assert(part3_answers[layer] == blocks - totalRemove)
//                }

                if (blocks - totalRemove >= AVAILABLE_BLOCKS) {
                    println(blocks - totalRemove - AVAILABLE_BLOCKS)
                    break
                }
            }
        }

        val part3_answers = mapOf(
            2 to 19,
            3 to 67,
            4 to 115,
            5 to 162,
            6 to 239,
            7 to 353,
            8 to 491,
            9 to 569,
            10 to 690,
            16 to 1885,
            32 to 7601,
            64 to 30655,
            128 to 123131,
            256 to 491005,
            512 to 1964801,
            1024 to 7863295,
            2048 to 31461371,
            4096 to 125820925
        )
    }
}