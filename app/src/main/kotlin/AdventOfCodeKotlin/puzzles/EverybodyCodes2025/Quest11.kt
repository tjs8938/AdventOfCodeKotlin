package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest11.Companion.part1
import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest11.Companion.part2
import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest11.Companion.part3
import kotlin.math.ceil
import kotlin.math.min
import kotlin.system.exitProcess

class Quest11 {
    companion object {
        fun part1() {
            val input = """
                3
                1
                16
                19
                17
                10
            """.trimIndent().lines().map { it.trim().toInt() }.toMutableList()
            val ROUNDS = 10
            var round = 0
            var phaseTwo = false
            while (round < ROUNDS) {
                val phaseOne = input.zipWithNext().any { it.first > it.second }
                if (phaseOne && !phaseTwo) {
                    var index = 0
                    while (index < input.size - 1) {
                        if (input[index] > input[index + 1]) {
                            input[index]--
                            input[index + 1]++
                        }
                        index++
                    }
                } else {
                    phaseTwo = true
                    var index = 0
                    while (index < input.size - 1) {
                        if (input[index] < input[index + 1]) {
                            input[index]++
                            input[index + 1]--
                        }
                        index++
                    }
                }
                round++
            }
            input.mapIndexed { index, ducks -> index * ducks + ducks }
                .sum()
                .let { println(it) }
        }

        fun part2() {
            val input = """
                805
                706
                179
                48
                158
                150
                232
                885
                598
                524
                423
            """.trimIndent().lines().map { it.trim().toInt() }.toMutableList()
            var round = 0
            while (input.zipWithNext().any { it.first > it.second }) {
                var index = 0
                while (index < input.size - 1) {
                    if (input[index] > input[index + 1]) {
                        input[index]--
                        input[index + 1]++
                    }
                    index++
                }
                round++
            }

            while (input.zipWithNext().any { it.first < it.second }) {
                var index = 0
                while (index < input.size - 1) {
                    if (input[index] < input[index + 1]) {
                        input[index]++
                        input[index + 1]--
                    }
                    index++
                }
                round++
            }
            println(round)
        }

        fun part3() {
            val columnHeights = """
                11888179286
                20201729226
                34998923050
                36876854847
                44737047048
                45949544669
                46200169086
                78386754294
                84253764964
                84689516348
                89923293644
                210372979674
                251226935066
                258604720442
                411185780165
                540867406513
                853940386999
                1288986684662
                1345782175055
                1407133209283
                1410124254671
                1483888591432
                1529353736569
                1567750079210
                1723734777330
                1749186652428
                1779631766030
                1828603089963
                1847784577655
                1930273445700
                2334423090167
                2414513372053
                2444646285090
                2497779146410
                2571724177262
                2613723935479
                2660980315900
                2860288598590
                3167555841862
                3335686907705
                3714711070098
                3716314559629
                3934116606700
                4344299350001
                4362803286290
                4408872872370
                4568498916097
                4597118104578
                4672803581773
                4766152612337
                4994129099002
                5174293685303
                5176340976474
                5196829047567
                5229670804339
                5478350342340
                5502115580655
                5516294490445
                5676338323308
                5881902693305
                5949743999518
                6112874977764
                6246035374973
                6279982041306
                6482889611635
                6483344531711
                6506497029042
                6539754080645
                6612195891071
                6649622067856
                6669736597870
                6796105509663
                6837272188597
                7053590776045
                7064981393749
                7149777323456
                7149873256897
                7169074186677
                7211494866435
                7443180672500
                7613255823303
                7672384112527
                7867591196532
                7889477041199
                8201084812194
                8232731034727
                8356847114723
                8403889976803
                9070766085493
                9075850921651
                9131694510142
                9143545202366
                9243150600220
                9286135175972
                9291275436254
                9562371360859
                9771022624818
                9848973503730
                9884218155473
                9947704697066
            """.trimIndent().lines().map { it.trim().toLong() }.toMutableList()
            class DuckColumn(val index: Int) {
                var height: Long
                    get() = columnHeights[index]
                    set(value) {
                        columnHeights[index] = value
                    }

                override fun toString(): String {
                    return "DuckColumn(index=$index, height=$height)"
                }
            }

            class DuckColumnGroup(val columns: MutableList<DuckColumn> = mutableListOf()) {

                val targetHeight: Long
                    get() = getColumnHeights().average().toLong()

                fun getColumnHeights(): List<Long> {
                    return this.columns.map { it.height }
                }

                fun isLevel(): Boolean {
                    return this.columns.toSet().size == 1
                }

                fun rounds(): Long {
                    return getColumnHeights().map { it - targetHeight }.filter { it > 0 }.sum()
                }

                fun spread() {
                    var extras = getColumnHeights().sum() - (targetHeight * columns.size)
                    var newHeight = targetHeight

                    columns.forEachIndexed { i, column ->
                        if (i < columns.size - extras) {
                            column.height = newHeight
                        } else {
                            column.height = newHeight + 1
                        }
                    }
                }

                override fun toString(): String {
                    return "DuckColumnGroup(columns=${getColumnHeights()})"
                }
            }

            var round = 0L
            while (columnHeights.zipWithNext().any { it.first > it.second }) {
                var index = 0
                var columnGroup = DuckColumnGroup()
                val columnGroups = mutableListOf<DuckColumnGroup>()

                // build descending column groups
                while (index < columnHeights.size) {
                    if (columnGroup.columns.isNotEmpty() && columnHeights[index - 1] < columnHeights[index]) {
                        columnGroups.add(columnGroup)
                        columnGroup = DuckColumnGroup()
                    }
                    columnGroup.columns.add(DuckColumn(index))
                    index++
                }
                columnGroups.add(columnGroup)

                // combine column groups if they would join after averaging out
                var changed = true
                while (changed) {
                    changed = false
                    var index = columnGroups.size - 1
                    while (index > 0) {
                        val leftGroup = columnGroups[index - 1]
                        val rightGroup = columnGroups[index]
                        if (leftGroup.targetHeight >= rightGroup.targetHeight) {
                            leftGroup.columns.addAll(rightGroup.columns)
                            columnGroups.removeAt(index)
                            changed = true
                        }
                        index--
                    }
                }

                round = columnGroups.maxOf { it.rounds() }

                columnGroups
                    .filterNot { it.isLevel() }
                    .forEach { it.spread() }
            }

            val fullColumnGroup = DuckColumnGroup(columnHeights.indices.map { DuckColumn(it) }.toMutableList())
            round += fullColumnGroup.rounds()

            println(round)

        }
    }
}

fun main(args: Array<String>) {
    part1()
    part2()
    part3()
}