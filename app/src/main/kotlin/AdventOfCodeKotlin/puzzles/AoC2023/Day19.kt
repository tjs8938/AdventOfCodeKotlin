package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day19 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val (ruleStrings, partStrings) = puzzle.get().split("\n\n").map { it.split("\n") }
            val ruleMap = ruleStrings.associate {
                val regex = Regex("""(.*)\{(.*)\}""")
                regex.matchEntire(it)!!
                    .destructured
                    .let { (label, ruleString) ->
                        label to RuleSet(ruleString)
                    }
            }

            val parts = partStrings.map {
                val split = it.substring(1, it.length - 1).split(",")
                split.associate { valueString ->
                    valueString[0].toString() to valueString.substringAfter("=").toInt()
                }
            }

            val result = parts.sumOf { part ->
                var ruleSet = ruleMap["in"]!!
                var status: Int? = null
                while (status == null) {
                    when (val r = ruleSet.process(part)) {
                        "A" -> status = part.values.sum()
                        "R" -> status = 0
                        else -> ruleSet = ruleMap[r]!!
                    }
                }
                status.toLong()
            }


            return result.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val (ruleStrings, _) = puzzle.get().split("\n\n").map { it.split("\n") }
            val ruleMap = ruleStrings.associate {
                val regex = Regex("""(.*)\{(.*)\}""")
                regex.matchEntire(it)!!
                    .destructured
                    .let { (label, ruleString) ->
                        label to RuleSet(ruleString)
                    }
            }

            val startingRange = mapOf(
                "x" to 1..4000,
                "m" to 1..4000,
                "a" to 1..4000,
                "s" to 1..4000
            )
            val acceptedRanges = mutableListOf<Map<String, IntRange>>()

            process(startingRange, "in", ruleMap, acceptedRanges)

            return acceptedRanges.sumOf { m ->
                m.map { (it.value.last - it.value.first + 1).toLong() }
                    .reduce { acc, l -> acc * l }
            }.toString()
        }

        private fun process(
            range: Map<String, IntRange>,
            s: String,
            ruleMap: Map<String, RuleSet>,
            acceptedRanges: MutableList<Map<String, IntRange>>
        ) {
            val ruleSet = ruleMap[s]!!
            var passed: Map<String, IntRange>?
            var rejected: Map<String, IntRange>? = range
            ruleSet.conditionals.forEach { rule ->
                if (rejected != null) {
                    val split: Pair<Map<String, IntRange>?, Map<String, IntRange>?> =
                        rule.process(rejected!!)
                    passed = split.first
                    rejected = split.second

                    passed?.let {
                        when (rule.result) {
                            "A" -> acceptedRanges.add(it)
                            "R" -> {}
                            else -> process(it, rule.result, ruleMap, acceptedRanges)
                        }
                    }
                }
            }
            rejected?.let {
                when (ruleSet.default) {
                    "A" -> acceptedRanges.add(it)
                    "R" -> {}
                    else -> process(it, ruleSet.default, ruleMap, acceptedRanges)
                }
            }
        }
    }

    class RuleSet(s: String) {
        fun process(part: Map<String, Int>): String {
            conditionals.forEach { rule ->
                val propertyValue = part[rule.property]!!
                if (rule.compare(propertyValue)) {
                    return rule.result
                }
            }
            return default
        }

        val conditionals: List<Rule>
        val default: String

        init {
            val splits = s.split(",")
            default = splits.last()
            conditionals = splits.subList(0, splits.size - 1).map { Rule(it) }
        }
    }

    class Rule(s: String) {
        fun process(toSplit: Map<String, IntRange>): Pair<Map<String, IntRange>?, Map<String, IntRange>?> {
            var passed: MutableMap<String, IntRange>? = mutableMapOf()
            var rejected: MutableMap<String, IntRange>? = mutableMapOf()
            toSplit.forEach { (property, range) ->
                if (property != this.property) {
                    passed?.let { it[property] = range.first..range.last }
                    rejected?.let { it[property] = range.first..range.last }
                } else {
                    if (comparison == ">") {
                        if (range.contains(compareTo)) {
                            passed?.let { it[property] = compareTo + 1..range.last }
                            rejected?.let { it[property] = range.first..compareTo }
                        } else if (range.first > compareTo) {
                            passed?.let { it[property] = range.first..range.last }
                            rejected = null
                        } else {
                            passed = null
                            rejected?.let { it[property] = range.first..range.last }
                        }
                    } else {
                        if (range.contains(compareTo)) {
                            passed?.let { it[property] = range.first until compareTo }
                            rejected?.let { it[property] = compareTo..range.last }
                        } else if (range.first < compareTo) {
                            passed?.let { it[property] = range.first..range.last }
                            rejected = null
                        } else {
                            passed = null
                            rejected?.let { it[property] = range.first..range.last }
                        }
                    }
                }
            }
            return passed to rejected
        }

        fun compare(propertyValue: Int): Boolean {
            return if (comparison == ">") {
                propertyValue > compareTo
            } else {
                propertyValue < compareTo
            }
        }

        val property: String
        val comparison: String
        val compareTo: Int
        val result: String

        init {
            val regex = Regex("""([xmas])([><])([0-9]+):(.*)""")
            regex.matchEntire(s)!!
                .destructured
                .let { (p, o, v, l) ->
                    property = p
                    compareTo = v.toInt()
                    comparison = o
                    result = l
                }
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}

        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}
    """.trimIndent()
    )

    Day19.part1(ex1).let {
        println(it)
        assert(it == "19114")
    }
    Runner.solve(2023, 19, part1 = Day19::part1)

    Day19.part2(ex1).let {
        println(it)
        assert(it == "167409079868000")
    }
    Runner.solve(2023, 19, part2 = Day19::part2)
}