package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import kotlin.math.min

class Day09 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val diskMap = puzzle.get().split("").filterNot { it.isEmpty() }.map { it.toInt() }.toMutableList()

            val compacted = mutableListOf<Int>()

            var index = 0
            while (index < diskMap.size) {
                val blockSize = diskMap[index]
                if (index % 2 == 0) {
                    repeat(blockSize) {
                        compacted.add(index / 2)
                    }
                } else {
                    val fill = if (index + 2 == diskMap.size) {
                        min(blockSize, diskMap.last())
                    } else {
                        blockSize
                    }
                    repeat(fill) {
                        if (diskMap.last() == 0) {
                            diskMap.removeLast()
                            diskMap.removeLast()
                        }
                        compacted.add(diskMap.size / 2)
                        diskMap[diskMap.size - 1]--
                    }
                }
                index++
            }

            return compacted.mapIndexed { index, i -> i.toLong() * index.toLong() }.sum()
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val diskMap = puzzle.get().split("").filterNot { it.isEmpty() }.map { it.toInt() }
            val emptySpace = mutableListOf<Pair<Int, Int>>()
            val blocks = mutableListOf<FileBlock>()

            var location = 0
            diskMap.forEachIndexed { index, size ->
                if (index % 2 == 0) {
                    blocks.add(FileBlock(size, index / 2, location))
                } else if (size > 0) {
                    emptySpace.add(size to location)
                }
                location += size
            }

            blocks.reversed().forEach { block ->
                emptySpace.sortedBy { it.second }.firstOrNull { it.first >= block.size && it.second < block.location }?.let { (size, location) ->
                    block.location = location
                    emptySpace.remove(size to location)
                    if (size > block.size) {
                        emptySpace.add(size - block.size to location + block.size)
                    }
                }
            }


            return blocks.sumOf { it.checksum() }
        }

        class FileBlock(val size: Int, val id: Int, var location: Int) {
            fun checksum(): Long {
                return (((size * (size  - 1)) / 2) + (size * location)) * id.toLong()
            }

            override fun toString(): String {
                return "FileBlock(size=$size, id=$id, location=$location)"
            }
        }

    }
}

fun main() {

    val ex1 = ExamplePuzzle(
        """
        2333133121414131402
    """.trimIndent()
    )
    assert(Day09.part1(ex1) == 1928)

    val ex2 = ExamplePuzzle("10272")
    assert(Day09.part1(ex2) == 17)

    Runner.solve(2024, 9, part1 = Day09::part1)

//    assert(Day09.part2(ex1) == 2858L)
//    assert(Day09.part2(ex2) == 17)

//    val ex3 = ExamplePuzzle("4820205")
//    assert(Day09.part2(ex3) == 153)
    Runner.solve(2024, 9, part2 = Day09::part2)
}