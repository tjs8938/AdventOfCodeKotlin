package AdventOfCodeKotlin.puzzles

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Passport(val map: Map<String, String>) {
    companion object {
        fun fromString(s: String): Passport {
            val fieldsAndValues = s.split(" ", "\n", "\r\n")
            val map = fieldsAndValues.associate {
                val (key, value) = it.split(":")
                key to value
            }
            return Passport(map)
        }
    }

    fun validate(): Boolean {
        return map.entries.all {
            when (it.key) {
                "byr" -> it.value.toInt() in 1920..2002
                "iyr" -> it.value.toInt() in 2010..2020
                "eyr" -> it.value.toInt() in 2020..2030
                "hgt" -> when (it.value.takeLast(2)) {
                    "cm" -> it.value.removeSuffix("cm").toIntOrNull() in 150..193
                    "in" -> it.value.removeSuffix("in").toIntOrNull() in 59..76
                    else -> false
                }
                "hcl" -> Regex("#[0-9a-f]{6}").matches(it.value)
                "ecl" -> it.value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> Regex("\\d{9}").matches(it.value)
                "cid" -> true
                else -> false
            }
        }
    }
}


class Day04 {
    companion object {

        val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

        fun parse(puzzle: PuzzleInputProvider): List<Passport> {
            return puzzle.get().split("\r\n\r\n", "\n\n")
                .map { Passport.fromString(it) }
        }


        fun part1(puzzle: PuzzleInputProvider): String {
            val passports = parse(puzzle)
            return passports.count { it.map.keys.containsAll(requiredFields) }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val passports = parse(puzzle)
            return passports.count { it.map.keys.containsAll(requiredFields) && it.validate()}.toString()
        }
    }
}


fun main() {
    Runner.solve(2020, 4, Day04::part1, Day04::part2)
}