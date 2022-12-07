package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day07 {
    companion object {
        private fun parseInput(puzzle: PuzzleInputProvider): MutableList<DirTree> {
            var currentDir = DirTree("/")
            val allDirs = mutableListOf(currentDir)
            var totalFileSize = 0

            puzzle.getAsString().forEach {
                with(it) {
                    when {
                        equals("$ cd ..") -> {
                            currentDir = currentDir.parent!!
                        }
                        startsWith("$ cd") -> {
                            val dirName = it.substring(5)
                            if (currentDir.subdirs.keys.contains(dirName)) {
                                currentDir = currentDir.subdirs[dirName]!!
                            }
                        }
                        equals("$ ls") -> {}
                        startsWith("dir") -> {
                            val dirName: String = it.substring(4)
                            val newDir = DirTree(dirName, currentDir)
                            currentDir.subdirs[dirName] = newDir
                            allDirs.add(newDir)
                        }
                        else -> {
                            val (size, name) = it.split(' ')
                            currentDir.files[name] = size.toInt()
                            totalFileSize += size.toInt()
                        }
                    }
                }
            }
            return allDirs
        }

        fun part1(puzzle: PuzzleInputProvider): String {
            val allDirs = parseInput(puzzle)

            return allDirs.map { it.bytes }.filter { it <= 100000 }.sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val allDirs = parseInput(puzzle)

            val targetSize = allDirs[0].bytes - 40000000

            return allDirs.map { it.bytes }.filter { it >= targetSize }.min().toString()
        }
    }
}


fun main() {
    Runner.solve(2022, 7, part1 = Day07::part1)
    Runner.solve(2022, 7, part2 = Day07::part2)
}

data class DirTree(val name: String, val parent: DirTree? = null) {
    val subdirs: MutableMap<String, DirTree> = mutableMapOf()
    val files: MutableMap<String, Int> = mutableMapOf()

    val bytes: Int by lazy {
        files.values.sum() + subdirs.map { it.value.bytes }.sum()
    }
}