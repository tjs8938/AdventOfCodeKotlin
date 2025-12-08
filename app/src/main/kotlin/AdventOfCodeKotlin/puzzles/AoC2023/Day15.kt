package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day15 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.get().split(",").sumOf {
                hash(it)
            }.toString()
        }

        private fun hash(it: String): Int {
            var value = 0
            it.forEach { c ->
                value += c.code
                value *= 17
                value %= 256
            }
            return value
        }

        fun part2(puzzle: PuzzleInputProvider): String {

            val lensBoxes = mutableMapOf<Int, LinkedHashMap<String, Int>>()
            val regex = Regex("""(.*)([=-])([0-9]?)""")


            puzzle.get().split(",").forEach {
            regex.matchEntire(it)!!.destructured
                .let { (label, operation, focalLength) ->
                    val h = hash(label)
                    val box = lensBoxes.getOrPut(h) { linkedMapOf() }

                    if (operation == "=") {
                        box[label] = focalLength.toInt()
                    } else {
                        box.remove(label)
                    }
                }
            }

            return lensBoxes.flatMap { (boxNumber, boxContents) ->
                boxContents.values.mapIndexed { slot, focalLength -> (boxNumber + 1) * (slot + 1) * focalLength }
            }.sum().toString()
        }
    }
}


fun main() {
//    Runner.solve(2023, 15, part1 = Day15::part1)

    val ex1 = ExamplePuzzle("""rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""")

    assert(Day15.part2(ex1) == "145")
    Runner.solve(2023, 15, part2 = Day15::part2)
}