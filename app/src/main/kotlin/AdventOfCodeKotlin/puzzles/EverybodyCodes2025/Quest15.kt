package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

import AdventOfCodeKotlin.puzzles.AoC2023.pop
import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest15.Companion.isHorizontal
import AdventOfCodeKotlin.util.Combinatorics
import AdventOfCodeKotlin.util.Direction
import AdventOfCodeKotlin.util.Mover
import AdventOfCodeKotlin.util.Node
import kotlin.math.abs

class Quest15 {
    companion object {
        fun part1() {
            val input = """
                L6,L3,L6,R6,L6,L6,L3,R6,L3,R6,L6,L6,R6,L6,L3,R3,L3,R3,R6,L6,L6,R6,L3,L6,R6,L3,R6,L6,R6,L6,L6,R3,L3,R6,L3,R6,L3,R6,L3,L3,R6,L6,R3,L6,R3,L6,R6,L6,L3,R3,R6,L6,L6,L3,R3,R6,L3,R3,R6,L3
            """.trimIndent().split(",")
            val wall = Mover<Char, Node<Char>>(0 to 0, Direction.NORTH)
            val wallPieces = mutableListOf<Pair<Int, Int>>()
            wallPieces.add(Pair(0, 0))
            input
                .map { it[0] to it.substring(1).toInt() }
                .forEach { (dir, length) ->
                    when (dir) {
                        'R' -> wall.turnRight()
                        'L' -> wall.turnLeft()
                    }
                    repeat(length) {
                        wall.moveForward()
                        wallPieces.add(wall.loc)
                    }
                }
            val last = wallPieces.last()
            wallPieces.removeLast()
            var next = mutableSetOf(0 to 0)
            val visited = mutableSetOf(0 to 0)
            var dist = 0
            while (next.isNotEmpty()) {
                next = next.flatMap { n ->
                    Direction.entries.map { dir -> dir + n }
                        .filter { !wallPieces.contains(it) && !visited.contains(it) }
                }.toMutableSet()
                dist++
                if (last in next) {
                    println(dist)
                    break
                }
                visited.addAll(next)
            }
        }

        fun part2() {
            val input = """
                L44,L11,L22,R11,L11,R77,L44,L55,R55,L99,L11,L11,R88,R88,L66,L22,R88,L44,L99,R99,R88,L22,L77,R22,L11,R55,L66,R55,L44,L99,R77,L22,R66,L44,L88,R77,R33,L55,L11,L33,R55,L22,R66,R11,R11,L22,R33,L77,L44,L11,R44,R66,L33,L33,R33,L11,R88,L22,L22,R66,R66,L44,R99,L66,L66,L33,R44,R55,L66,R55,L55,R88,L44,R44,L66,R44,L55,L66,R11,L44,R77,L22,R22,L44,R66,L66,R44,L99,L44,L33,R55,R99,L99,R33,R33,L77,L88,R55,L55,L33,R99,L22,R33,R99,L44,L11,L33,R11,R99,L33,L66,L22,R11,R44,R11,L99,R77,L55,R44,R88,L33,L66,R33,L44,L44,R77,R66,L33,L33,R55,L33,L22,R66,R66,L44,R66,L55,R66,L99,R88,L33,R77,L11,L55,R33,L55,R44,R77,L66,L66,R22,L22,L33,R22,L22,R11,R44,L33,R77,L22,R88,L99,R99,L77,L55,R77,L22,R22,R22,L33,L99,R33,R44,L99,L11,R99,L22,L77,R88,R33,L44,R11,L33,R55,L99,R55,L11,L33,R55,L11
            """.trimIndent().split(",")
            val wall = Mover<Char, Node<Char>>(0 to 0, Direction.NORTH)
            val wallPieces = mutableListOf<Pair<Int, Int>>()
            wallPieces.add(Pair(0, 0))
            input
                .map { it[0] to it.substring(1).toInt() }
                .forEach { (dir, length) ->
                    when (dir) {
                        'R' -> wall.turnRight()
                        'L' -> wall.turnLeft()
                    }
                    repeat(length) {
                        wall.moveForward()
                        wallPieces.add(wall.loc)
                    }
                }
            val last = wallPieces.last()
            wallPieces.removeLast()
            var next = mutableSetOf(0 to 0)
            val visited = mutableSetOf(0 to 0)
            var dist = 0
            while (next.isNotEmpty()) {
                next = next.flatMap { n ->
                    Direction.entries.map { dir -> dir + n }
                        .filter { !wallPieces.contains(it) && !visited.contains(it) }
                }.toMutableSet()
                dist++
                if (last in next) {
                    println(dist)
                    break
                }
                visited.addAll(next)
            }
        }

        fun Pair<Pair<Int, Int>, Pair<Int, Int>>.isHorizontal(): Boolean {
            return this.first.first == this.second.first
        }

        fun part3() {
            val input = """
                R4599494,R1698691,R7996880,R1799298,R1899259,L4398284,R5296237,R599826,L4098811,R5798318,R3599604,L3699593,R999610,R1199892,L199978,L4296689,R6099451,L7497825,R2499275,R399844,L7796958,R6994610,R9492685,L9297303,L5599384,R6099329,R5395842,L8499065,R1199892,R5999460,L5299417,L6299433,R7194888,R4599494,L7594604,R9096451,R1599376,R5098011,L7399186,R4098811,L3799658,L5895457,L4396612,R3698557,R799432,L2499025,L4099549,R8993070,R199978,R2198306,L6598086,L2399304,R2299793,L9192916,R4798128,L9299163,R7397854,R1898537,R4896227,L5899469,L6494995,R4696381,L4796304,R1299077,L5895811,R7397854,R199978,L7499325,R7997680,R599934,R2897767,L5299523,L5395842,L7694071,L7699153,R8499235,R1599376,R4898579,L4798128,L2798908,R1199532,L2999130,R2499275,L3898479,R2699217,L7194456,R7297153,L9398966,R1999420,R5098011,R1699847,L2699703,L9399154,R3499615,R2499725,L299787,R6599274,L3598956,L8097651,R5199428,L4896227,L4496805,R9792454,R2698947,R7299343,L2599246,L5395842,R9299163,R8299253,R4199538,L699797,L4899461,L8499235,R4896521,R9193468,L7794462,R8093763,L8793752,R5495765,L6598086,L9192916,R7594148,R3899649,R7399334,L3899649,L9593184,R599574,R899739,L1798722,R4498695,L8193686,R1899791,L2499725,R5799362,L6099451,R9293397,L799928,L8693823,R4496535,R499645,L7694071,R5899351,R8699043,L5898289,L1498845,R4396876,L5795882,R5895811,R5795534,L9697187,L7197912,R9198988,R3499685,R3097613,L5799362,L4399604,R6694841,L9692531,R1699813,R6099451,L5498405,L9492685,R3199648,L8497535,R5596024,R2699703,L9696217,R7794462,L4896521,R2199802,R8197622,L2198306,L7694071,R3897231,L4696663,L7194456,R9696217,R9092993,R3999640,L1099571,R5197972,L699937,L4999550,R4498695,L6199318,R499945,L9399154,R4098811,R3199072,L7893917,R7499175,R999890,L5798318,L7494675,R4196766,L7299343,R3797074,L2298229,R8293609,L5195996,L1598768,R7499175,L8899021,R5597816,R399964,R3899649,L2999730,L5099541,L6595314,R7097941,R2697921,L9892971,R2398296,R3399014,L3298713,R2798012,L6395072,R8696607,L3098791,L6399424,R4899559,L8597506,R3599676,R1599856,L5999460,R1698793,R3399626,L2899681,L8596646,R899901,R1099681,L7899289,R4499595,R3299637,L1498845,L7494675,L6098231,R2399784,L4899559,L6698057
            """.trimIndent().split(",")
            val wall = Mover<Char, Node<Char>>(0 to 0, Direction.NORTH)
            val outsideCorners = mutableListOf(0 to 0)
            val walls = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            input
                .map { it[0] to it.substring(1).toInt() }
                .forEach { (dir, length) ->
                    val cornerMover = Mover<Char, Node<Char>>(wall.loc, wall.dir)
                    cornerMover.moveForward()
                    when (dir) {
                        'R' -> {
                            wall.turnRight()
                            cornerMover.turnLeft()
                        }
                        'L' -> {
                            wall.turnLeft()
                            cornerMover.turnRight()
                        }
                    }
                    cornerMover.moveForward()
                    outsideCorners.add(cornerMover.loc)
                    val start = wall.loc
                    wall.moveForward(length)
                    walls.add(start to wall.loc)
                }
            val start = 0 to 0
            val last = walls.last().second
            outsideCorners.add(last)
            if (walls.first().second.second > 0) {
                walls[0] = (0 to 1) to walls[0].second
            } else {
                walls[0] = (0 to -1) to walls[0].second
            }

            val lastWall = walls.last()
            if (lastWall.isHorizontal()) {
                if (lastWall.first.second < lastWall.second.second) {
                    walls[walls.size - 1] = lastWall.first to (lastWall.second.first to lastWall.second.second + 1)
                } else {
                    walls[walls.size - 1] = lastWall.first to (lastWall.second.first to lastWall.second.second - 1)
                }
            } else {
                if (lastWall.first.first < lastWall.second.first) {
                    walls[walls.size - 1] = lastWall.first to (lastWall.second.first - 1 to lastWall.second.second)
                } else {
                    walls[walls.size - 1] = lastWall.first to (lastWall.second.first + 1 to lastWall.second.second)
                }
            }

            class Line(val p1: Pair<Int, Int>, val p2: Pair<Int, Int>) {
                fun isHorizontal(): Boolean {
                    return p1.first == p2.first
                }

                override fun toString(): String {
                    return "Line(p1=$p1, p2=$p2)"
                }

                val minY: Int get() = if (p1.first < p2.first) p1.first else p2.first
                val minX: Int get() = if (p1.second < p2.second) p1.second else p2.second
                val maxY: Int get() = if (p1.first > p2.first) p1.first else p2.first
                val maxX: Int get() = if (p1.second > p2.second) p1.second else p2.second

                val slope: Double get() = (p1.first - p2.first) / (p1.second - p2.second).toDouble()


            }

            val distances = outsideCorners.associateWith { mutableMapOf(it to 0) }.toMutableMap()
            val cornerPairs = Combinatorics.combinations(outsideCorners, 2).map { Line(it[0], it[1]) }
            cornerPairs.forEach { line ->
                val wallBetween = walls.map { Line(it.first, it.second) }.any { wall ->
                    var result = false
                    if (wall.isHorizontal() && line.minY < wall.p1.first && line.maxY > wall.p1.first) {
                        val s = line.slope
                        val xAtY = (wall.p1.first - line.p1.first) / s + line.p1.second
                        result = xAtY > wall.minX && xAtY < wall.maxX
                    } else if(!wall.isHorizontal() && line.minX < wall.p1.second && line.maxX > wall.p1.second) {
                        val s = line.slope
                        val yAtX = s * (wall.p1.second - line.p1.second) + line.p1.first
                        result = yAtX > wall.minY && yAtX < wall.maxY
                    }
                    result
                }
                if (!wallBetween) {
                    val dist = abs(line.p1.first - line.p2.first) + abs(line.p1.second - line.p2.second)
                    distances.getOrPut(line.p1) { mutableMapOf() }[line.p2] = dist
                    distances.getOrPut(line.p2) { mutableMapOf() }[line.p1] = dist
                }
            }

            val toProcess = mutableSetOf(start)
            while (toProcess.isNotEmpty()) {
                val next = toProcess.pop()
                val currentDistances = distances[next] ?: mutableMapOf()
                currentDistances.toMap().forEach { (point, distance) ->
                    val nextNeighbors = distances[point] ?: mutableMapOf()
                    nextNeighbors.forEach { (neighbor, nextDistance) ->
                        if (currentDistances.getOrDefault(neighbor, Int.MAX_VALUE) > distance + nextDistance) {
                            currentDistances[neighbor] = distance + nextDistance
                            toProcess.add(neighbor)
                        }
                    }
                }
            }
            println(distances[start]!![last])
        }
    }
}

fun main(args: Array<String>) {
//    Quest15.part1()
//    Quest15.part2()
    Quest15.part3()
}

