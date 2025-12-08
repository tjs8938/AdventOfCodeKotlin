package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day18 {
    companion object {
        private fun parseInput(puzzle: PuzzleInputProvider): Map<Triple<Int, Int, Int>, Droplet> {
            val droplets = puzzle.getAsString().map {
                it.split(",").map { it.toInt() }
            }.associate { (x, y, z) ->
                val position = Triple(x, y, z)
                position to Droplet(position, DropletType.LAVA, DropletType.AIR)
            }.toMutableMap()

            val airDroplets = mutableMapOf<Triple<Int, Int, Int>, Droplet>()
            val waterDroplets = mutableMapOf<Triple<Int, Int, Int>, Droplet>()

            droplets.values.forEach { drop ->
                drop.neighbors.keys.forEach { nPos ->
                    if (nPos in droplets.keys) {
                        drop.neighbors[nPos] = DropletType.LAVA
                    } else {
                        if (!airDroplets.containsKey(nPos)) {
                            airDroplets[nPos] = Droplet(nPos, DropletType.AIR, DropletType.AIR)
                        }
                        airDroplets[nPos]!!.neighbors[drop.position] = DropletType.LAVA
                        drop.neighbors[nPos] = DropletType.AIR
                    }
                }
            }

            droplets.putAll(airDroplets)

            val minX = droplets.minBy { it.key.first }.key.first - 1
            val maxX = droplets.maxBy { it.key.first }.key.first + 1
            val minY = droplets.minBy { it.key.second }.key.second - 1
            val maxY = droplets.maxBy { it.key.second }.key.second + 1
            val minZ = droplets.minBy { it.key.third }.key.third - 1
            val maxZ = droplets.maxBy { it.key.third }.key.third + 1

            (minX..maxX).forEach { x->
                (minY..maxY).forEach { y ->
                    (minZ..maxZ).forEach { z ->
                        val pos = Triple(x, y, z)
                        if (!droplets.containsKey(pos)) {
                            val drop : Droplet
                            if (x == minX || x == maxX || y == minY || y == maxY || z == minZ || z == maxZ) {
                                drop = Droplet(pos, DropletType.WATER, DropletType.WATER)
                                waterDroplets[pos] = drop
                            } else {
                                drop = Droplet(pos, DropletType.AIR, DropletType.AIR)
                            }
                            droplets[pos] = drop
                        }
                    }
                }
            }

            val processing = ArrayDeque<Droplet>()
            processing.addAll(waterDroplets.values)
            while (processing.isNotEmpty()) {
                val drop = processing.removeFirst()
                drop.neighbors.keys.filter { it in droplets.keys }.map { droplets[it] }
                    .forEach { neighbor ->
                        when (neighbor!!.type) {
                            DropletType.LAVA -> neighbor.neighbors[drop.position] =
                                DropletType.WATER
                            DropletType.AIR -> {
                                neighbor.neighbors[drop.position] = DropletType.WATER
                                neighbor.type = DropletType.WATER
                                processing.add(neighbor)
                            }
                            DropletType.WATER -> {}
                        }
                    }
            }

            return droplets
        }

        fun part1(puzzle: PuzzleInputProvider): String {
            val droplets = parseInput(puzzle)

            return droplets.values.filter { it.type == DropletType.LAVA }
                .sumOf { it.neighbors.count { it.value != DropletType.LAVA } }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val droplets = parseInput(puzzle)

            return droplets.values.filter { it.type == DropletType.LAVA }
                .sumOf { it.neighbors.count { it.value == DropletType.WATER } }.toString()
        }
    }
}


fun main() {

    val example = ExamplePuzzle(
        "2,2,2\n" +
            "1,2,2\n" +
            "3,2,2\n" +
            "2,1,2\n" +
            "2,3,2\n" +
            "2,2,1\n" +
            "2,2,3\n" +
            "2,2,4\n" +
            "2,2,6\n" +
            "1,2,5\n" +
            "3,2,5\n" +
            "2,1,5\n" +
            "2,3,5"
    )

//    assert(Day18.part1(example) == "64")

//    Runner.solve(2022, 18, part1 = Day18::part1)

    val block = ExamplePuzzle(
        "0,0,0\n" +
            "0,0,1\n" +
            "0,0,2\n" +
            "0,1,0\n" +
            "0,1,1\n" +
            "0,1,2\n" +
            "0,2,0\n" +
            "0,2,1\n" +
            "0,2,2\n" +
            "1,0,0\n" +
            "1,0,1\n" +
            "1,0,2\n" +
            "1,1,0\n" +
            "1,1,1\n" +
            "1,1,2\n" +
            "1,2,0\n" +
            "1,2,1\n" +
            "1,2,2\n" +
            "2,0,0\n" +
            "2,0,1\n" +
            "2,0,2\n" +
            "2,1,0\n" +
            "2,1,1\n" +
            "2,1,2\n" +
            "2,2,0\n" +
            "2,2,1\n" +
            "2,2,2"
    )

    val tunnel = ExamplePuzzle(
        "0,0,0\n" +
            "0,0,1\n" +
            "0,0,2\n" +
            "0,1,0\n" +
            "0,1,1\n" +
            "0,1,2\n" +
            "0,2,0\n" +
            "0,2,1\n" +
            "0,2,2\n" +
            "1,0,0\n" +
            "1,0,1\n" +
            "1,0,2\n" +
            "1,1,0\n" +
            "1,2,0\n" +
            "1,2,1\n" +
            "1,2,2\n" +
            "2,0,0\n" +
            "2,0,1\n" +
            "2,0,2\n" +
            "2,1,0\n" +
            "2,1,1\n" +
            "2,1,2\n" +
            "2,2,0\n" +
            "2,2,1\n" +
            "2,2,2"
    )

    val blocked_tunnel = ExamplePuzzle(
        "0,0,0\n" +
            "0,0,1\n" +
            "0,0,2\n" +
            "0,1,0\n" +
            "0,1,1\n" +
            "0,1,2\n" +
            "0,2,0\n" +
            "0,2,1\n" +
            "0,2,2\n" +
            "1,0,0\n" +
            "1,0,1\n" +
            "1,0,2\n" +
            "1,1,0\n" +
            "1,2,0\n" +
            "1,2,1\n" +
            "1,2,2\n" +
            "2,0,0\n" +
            "2,0,1\n" +
            "2,0,2\n" +
            "2,1,0\n" +
            "2,1,1\n" +
            "2,1,2\n" +
            "2,2,0\n" +
            "2,2,1\n" +
            "2,2,2\n" +
            "1,1,3"
    )

    assert(Day18.part2(example) == "58")
    assert(Day18.part2(block) == "54")
    assert(Day18.part2(blocked_tunnel) == "58")


    val bigCube = mutableListOf<String>()
    (0..4).forEach { x ->
        (0..4).forEach { y ->
            (0..4).forEach { z ->
                if (x in (1..3) && y in (1..3) && z in (1..3)) {
                } else {
                    bigCube.add("$x,$y,$z")
                }
            }
        }
    }

    assert(Day18.part2(ExamplePuzzle(bigCube.joinToString("\n"))) == "150")


    Runner.solve(2022, 18, part2 = Day18::part2)
}

enum class DropletType { LAVA, AIR, WATER }

class Droplet(
    val position: Triple<Int, Int, Int>,
    var type: DropletType,
    neighborType: DropletType
) {

    companion object {
        private val offsets = listOf(
            Triple(0, 0, 1),
            Triple(0, 0, -1),
            Triple(0, 1, 0),
            Triple(0, -1, 0),
            Triple(1, 0, 0),
            Triple(-1, 0, 0)
        )
    }

    val neighbors: MutableMap<Triple<Int, Int, Int>, DropletType> = mutableMapOf()

    init {
        offsets.forEach { neighbors[it + position] = neighborType }
    }

    override fun toString(): String {
        return "Droplet(position=$position, type=$type)"
    }
}

private operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(first + other.first, second + other.second, third + other.third)
}
