package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Day15 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val y = 2000000

            // read/parse sensors from file
            val sensors = parseInput(puzzle)

            // All X values that are reachable by a sensor
            val beaconsOnY = mutableSetOf<Int>()
            val allScannableX = sensors.mapNotNull {
                val distToY = abs(it.sensorLocation.second - y)
                if (distToY > it.scanDistance) {
                    null
                } else {
                    if (it.beaconLocation.second == y) {
                        beaconsOnY.add(it.beaconLocation.first)
                    }

                    val remainingX = it.scanDistance - distToY
                    ((it.sensorLocation.first - remainingX)..(it.sensorLocation.first + remainingX)).toSet()
                }
            }.flatten().toSet()


            return allScannableX.minus(beaconsOnY).size.toString()
        }

        private fun parseInput(puzzle: PuzzleInputProvider): List<Sensor> {
            val sensors = puzzle.getAsString().map { inputString ->
                Regex("""Sensor at x=(-?\d*), y=(-?\d*): closest beacon is at x=(-?\d*), y=(-?\d*)""").matchEntire(
                    inputString
                )!!
                    .groupValues
                    .mapNotNull { it.toIntOrNull() }
            }.map { it.let { (a, b, c, d) -> Sensor(a, b, c, d) } }
            return sensors
        }

        // unused
//        fun part2IfYouWantToBlowTheHeap(puzzle: PuzzleInputProvider): String {
//            val y = 4000000
//            var unscannedSpace = mutableListOf((0 to 0) to (y to y))
//
//            // read/parse sensors from file
//            val sensors = parseInput(puzzle)
//
//            sensors.forEachIndexed { index, sensor ->
//                println("Sensor $index - sections = ${unscannedSpace.size}")
//                val newUnscannedSpace = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
//                unscannedSpace.forEach { unscanned ->
//
//                    val reachableX =
//                        sensor.reachableX().let { (low, high) -> max(low, 0) to min(high, y) }
//                    val reachableY =
//                        sensor.reachableY().let { (low, high) -> max(low, 0) to min(high, y) }
//
//                    val addSpace =
//                        { space: Pair<Pair<Int, Int>, Pair<Int, Int>> -> newUnscannedSpace.add(space) }
//
//                    if (sensor.contains(unscanned)) {
//                        // nothing to add
//                    } else if (sensor.overlaps(unscanned)) {
//                        // Look "above" the sensor region
//                        if (unscanned.first.second < reachableY.first) {
//                            addSpace(unscanned.first to (unscanned.second.first to (reachableY.first - 1)))
//                        }
//
//                        // Look "left" of the sensor region
//                        if (unscanned.first.first < reachableX.first) {
//                            addSpace((unscanned.first.first to reachableY.first) to (reachableX.first - 1 to unscanned.second.second))
//                        }
//
//                        // Look "right" of the sensor region
//                        if (unscanned.second.first > reachableX.second) {
//                            addSpace((reachableX.second + 1 to reachableY.first) to unscanned.second)
//                        }
//
//                        // Look "below" the sensor region
//                        if (unscanned.second.second > reachableY.second) {
//                            addSpace((reachableX.first to reachableY.second + 1) to (reachableX.second to unscanned.second.second))
//                        }
//
//                        (max(reachableY.first, unscanned.first.second)..min(
//                            reachableY.second,
//                            unscanned.second.second
//                        )).forEach { y ->
//                            sensor.scannableOnY(y)?.let { (lowX, highX) ->
//                                if (unscanned.first.first < lowX) {
//                                    addSpace(
//                                        (unscanned.first.first to y) to (min(
//                                            lowX - 1,
//                                            unscanned.second.first
//                                        ) to y)
//                                    )
//                                }
//
//                                if (unscanned.second.first > highX) {
//                                    addSpace(
//                                        (max(
//                                            highX + 1,
//                                            unscanned.first.first
//                                        ) to y) to (unscanned.second.first to y)
//                                    )
//                                }
//                            }
//                        }
//                    } else {
//                        addSpace(unscanned)
//                    }
//                }
//                unscannedSpace = newUnscannedSpace
//            }
//
//            println(unscannedSpace)
//            return ""
//        }

        fun part2(puzzle: PuzzleInputProvider): String {
            // read/parse sensors from file
            val sensors = parseInput(puzzle)
            sensors.forEach {
                it.justOutOfReach(4000000, sensors)?.let {
                    return BigInteger(it.first.toString()).times(BigInteger("4000000")).plus(BigInteger(it.second.toString())).toString()
                }
            }

            return ""
        }
    }
}


fun main() {
    Runner.solve(2022, 15, part1 = Day15::part1)
    Runner.solve(2022, 15, part2 = Day15::part2)
}


class Sensor(sx: Int, sy: Int, bx: Int, by: Int) {
    val sensorLocation: Pair<Int, Int>
    val beaconLocation: Pair<Int, Int>
    val scanDistance: Int

    init {
        sensorLocation = sx to sy
        beaconLocation = bx to by
        scanDistance = abs(sx - bx) + abs(sy - by)
    }

    fun reachableY() =
        (sensorLocation.second - scanDistance) to (sensorLocation.second + scanDistance)

    fun reachableX() =
        (sensorLocation.first - scanDistance) to (sensorLocation.first + scanDistance)

    fun scannableOnY(y: Int): Pair<Int, Int>? {
        val distToY = abs(sensorLocation.second - y)
        return if (distToY > scanDistance) {
            null
        } else {
            val remainingX = scanDistance - distToY
            (sensorLocation.first - remainingX) to (sensorLocation.first + remainingX)
        }
    }

    fun justOutOfReach(upperCorner: Int, sensors: List<Sensor>): Pair<Int, Int>? {
        if (reachableY().first > 0) {
            (sensorLocation.first to reachableY().first - 1).takeIf { sensors.none { s -> s.reaches(it) } }?.let { return it }
        }
        if (reachableY().second < upperCorner) {
            (sensorLocation.first to reachableY().second + 1).takeIf { sensors.none { s -> s.reaches(it) } }?.let { return it }
        }
        (max(0, reachableY().first)..min(upperCorner, reachableY().second)).forEach { y ->
            scannableOnY(y)?.let { (lowX, highX) ->
                (lowX - 1).takeIf { it >= 0 }?.let { (it to y).takeIf { sensors.none { s -> s.reaches(it) } }?.let { return it } }
                (highX + 1).takeIf { it <= upperCorner }?.let { (it to y).takeIf { sensors.none { s -> s.reaches(it) } }?.let { return it } }
            }
        }
        return null
    }

    // unused
    fun overlaps(unscanned: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean {
        return unscanned.corners().any { this.reaches(it) } || this.corners()
            .any { unscanned.contains(it) }
    }

    fun contains(unscanned: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean {
        return unscanned.corners().all { this.reaches(it) }
    }

    fun reaches(otherPoint: Pair<Int, Int>): Boolean {
        return abs(sensorLocation.first - otherPoint.first) + abs(sensorLocation.second - otherPoint.second) <= scanDistance
    }

    private fun corners(): List<Pair<Int, Int>> {
        return listOf(
            (sensorLocation.first to reachableY().first),
            (reachableX().first to sensorLocation.second),
            (reachableX().second to sensorLocation.second),
            (sensorLocation.first to reachableY().second)
        )
    }
}

private fun Pair<Pair<Int, Int>, Pair<Int, Int>>.corners(): List<Pair<Int, Int>> {
    return listOf(
        this.first,
        this.second,
        (this.first.first to this.second.second),
        (this.second.first to this.first.second)
    )
}

private fun Pair<Pair<Int, Int>, Pair<Int, Int>>.contains(point: Pair<Int, Int>): Boolean {
    return point.first >= this.first.first && point.first <= this.second.first && point.second >= this.first.second && point.second <= this.second.second
}