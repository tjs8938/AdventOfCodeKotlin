package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day05 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString()
            val seeds = input[0].split(" ").drop(1).map { it.toLong() }.toList()
            input.drop(2)

            val maps = mutableListOf<MutableList<RangeMapping>>()
            var current: MutableList<RangeMapping>? = null
            input.forEach {
                if (it.contains("map")) {
                    if (current != null) {
                        maps.add(current!!)
                    }
                    current = mutableListOf()
                } else if (it != "") {
                    current?.add(RangeMapping(it))
                }
            }
            maps.add(current!!)

            val seedLocations = mutableMapOf<Long, Long>()
            seeds.forEach { seed ->
                var value = seed
                maps.forEach { map ->
                    val mapping = map.firstOrNull { it.range.contains(value) }
                    if (mapping != null) {
                        value -= mapping.offset
                    }
                }
                seedLocations[seed] = value
            }

            return seedLocations.values.min().toString()
        }
        fun part1b(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString()
            val seeds = input[0].split(" ").drop(1).map { it.toLong() }.chunked(2) { it[0] to it[1] }.toList()
            input.drop(2)

            val maps = mutableListOf<MutableList<RangeMapping>>()
            var current: MutableList<RangeMapping>? = null
            input.forEach {
                if (it.contains("map")) {
                    if (current != null) {
                        maps.add(current!!)
                    }
                    current = mutableListOf()
                } else if (it != "") {
                    current?.add(RangeMapping(it))
                }
            }
            maps.add(current!!)

            var bestLocation = Long.MAX_VALUE
            seeds.forEach { seed ->
                (seed.first..seed.first+seed.second-1).forEach {
                    var value = it
                    maps.forEach { map ->
                        val mapping = map.firstOrNull { it.range.contains(value) }
                        if (mapping != null) {
                            value -= mapping.offset
                        }
                    }
                    bestLocation = minOf(bestLocation, value)
                }
            }

            return bestLocation.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString()
            val seeds = input[0].split(" ").drop(1).map { it.toLong() }.chunked(2) { LongRange(it[0], it[0] + it[1] - 1) }.toMutableList()
            input.drop(2)

            val maps = mutableListOf<MutableList<RangeMapping>>()
            var current: MutableList<RangeMapping>? = null
            input.forEach {
                if (it.contains("map")) {
                    if (current != null) {
                        current!!.sortBy { it.range.start }
                        maps.add(current!!)
                    }
                    current = mutableListOf()
                } else if (it != "") {
                    current?.add(RangeMapping(it))
                }
            }
            current!!.sortBy { it.range.first }
            maps.add(current!!)

            var values: MutableList<LongRange> = seeds
            maps.forEach { map ->
                val newValues = mutableListOf<LongRange>()
                while (!values.isEmpty()) {
                    val value = values.removeFirst()
                    if (map.none { rangeMapping -> rangeMapping.range.contains(value.first) || rangeMapping.range.contains(value.last) }) {
                        newValues.add(value)
                    } else {
                        map.forEach { rangeMapping ->

                            if (rangeMapping.range.hasIntersection(value)) {
                                if (rangeMapping.range.contains(value)) {
                                    newValues.add(value.shift(-rangeMapping.offset))
                                } else {
                                    newValues.add(rangeMapping.range.intersection(value).shift(-rangeMapping.offset))
                                    values.add(value.minus(rangeMapping.range))
                                }
                            }
                        }
                    }
                }
                values = newValues
            }
            values.sortBy { it.first }

            return values.minBy { it.first }.first.toString()
        }
    }
}

fun LongRange.shift(offset: Long) : LongRange {
    return LongRange(first + offset, last + offset)
}

fun LongRange.minus(other: LongRange) : LongRange {
    return if (first < other.first) {
        LongRange(first, other.first - 1)
    } else {
        LongRange(other.last + 1, last)
    }
}

fun LongRange.hasIntersection(other: LongRange) : Boolean {
    return contains(other.first) || contains(other.last)
}

fun LongRange.contains(other: LongRange) : Boolean {
    return contains(other.first) && contains(other.last)
}

fun LongRange.intersection(other: LongRange) : LongRange {
    return LongRange(maxOf(first, other.first), minOf(last, other.last))
}

class RangeMapping(s:String) {
    lateinit var range: LongRange
    var offset = 0L

    init {
        val splits = s.split(" ")
        val start = splits[1].toLong()
        val end = start + splits[2].toLong() - 1
        offset = start - splits[0].toLong()
        range = LongRange(start, end)
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent())

    assert(Day05.part1(ex1) == "35")

//    Runner.solve(2023, 5, part1 = Day05::part1)

    assert(Day05.part1b(ex1) == "46")
    assert(Day05.part2(ex1) == "46")

    Runner.solve(2023, 5, part2 = Day05::part2)
    Runner.solve(2023, 5, part2 = Day05::part1b)
}