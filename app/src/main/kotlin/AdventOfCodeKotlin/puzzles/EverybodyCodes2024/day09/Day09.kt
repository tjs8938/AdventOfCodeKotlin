package year2024.day09

import kotlin.math.ceil

fun main() {
    Day09.part1()
    Day09.part2()
    Day09.part3()
}

class Day09 {
    companion object {
        fun part1() {
            val stamps = listOf(1, 3, 5, 10)
            val input = """
                16489
                10059
                13774
                14482
                10099
                19286
                17622
                10107
                12910
                10000
            """.trimIndent().split("\n").map { it.toInt() }

            val result = input.sumOf { it ->
                var remaining = it
                var beetles = 0
                stamps.reversed().forEach { stamp ->
                    beetles += remaining / stamp
                    remaining %= stamp
                }
                beetles
            }
            println(result)
        }

        fun part2() {
            val stamps = listOf(1, 3, 5, 10, 15, 16, 20, 24, 25, 30).reversed()
            val input = """
                1473
                1251
                1470
                1308
                1517
                1447
                1590
                1998
                1316
                1467
                1147
                1442
                1752
                1543
                1332
                1744
                1300
                1025
                1233
                1264
                1846
                1018
                1137
                1705
                1461
                1573
                1933
                1533
                1457
                1591
                1435
                1413
                1584
                1599
                1347
                1130
                1706
                1668
                1671
                1030
                1708
                1859
                1460
                1438
                1114
                1328
                1360
                1504
                1952
                1309
                1231
                1937
                1034
                1295
                1044
                1222
                1472
                1242
                1276
                1935
                1098
                1361
                1644
                1968
                1557
                1361
                1946
                1227
                1581
                1877
                1115
                1400
                1139
                1394
                1272
                1413
                1374
                1677
                1320
                1962
                1171
                1357
                1384
                1318
                1371
                1000
                1418
                1001
                1488
                1118
                1248
                1113
                1412
                1634
                1568
                1357
                1935
                1874
                1050
                1784
            """.trimIndent().split("\n").map { it.toInt() }.toMutableList()
            val inputAgain = input.toMutableList()

            var startTime = System.currentTimeMillis()
            var result = 0
            val seen = mutableSetOf(0)
            val queue = mutableListOf(0 to 0)
            while (input.size > 0) {
                val (current, steps) = queue.removeFirst()
                stamps.forEach { stamp ->
                    val next = current + stamp
                    if (next in input) {
                        result += steps + 1
                        input.remove(next)
                    }

                    if (next !in seen) {
                        seen.add(next)
                        queue.add(next to steps + 1)
                    }
                }
            }

            println(result)
            println("Time: ${System.currentTimeMillis() - startTime}")

            startTime = System.currentTimeMillis()
            val stampsNeeded = stamps.associateWith { 1 }.toMutableMap()
            fun getStampsNeeded(target: Int): Int {
                if (target !in stampsNeeded) {
                    stampsNeeded[target] = stamps.reversed().filter { target - it > 0 }.minOf { stamp ->
                        getStampsNeeded(target - stamp) + 1
                    }
                }
                return stampsNeeded[target]!!
            }
            println(inputAgain.sumOf { getStampsNeeded(it) })
            println("Time: ${System.currentTimeMillis() - startTime}")
        }

        fun part3() {
            val input = """
                166142
                185502
                145766
                118258
                143614
                117086
                168229
                167592
                145195
                122856
                195227
                184209
                147766
                137249
                182125
                192708
                132701
                166717
                155727
                168273
                141463
                183373
                139452
                103469
                164305
                114274
                122581
                123158
                104584
                143620
                131484
                149714
                177413
                195594
                116639
                158267
                176771
                179841
                170042
                137043
                171348
                165227
                182924
                164761
                175768
                109454
                120168
                187781
                128558
                156432
                178016
                144619
                166553
                136188
                164956
                147944
                117871
                187307
                139596
                129262
                132394
                153996
                175136
                198158
                125768
                138314
                163149
                111091
                100909
                103631
                151556
                143757
                178658
                136819
                133088
                146643
                127723
                151699
                157738
                188553
                155642
                139343
                150340
                127563
                175662
                121606
                171725
                105221
                184852
                189724
                193977
                149961
                104102
                195074
                117717
                183488
                147041
                114396
                105391
                198987
            """.trimIndent().split("\n").map { it.toInt() }

            val stamps = listOf(1, 3, 5, 10, 15, 16, 20, 24, 25, 30, 37, 38, 49, 50, 74, 75, 100, 101).reversed()
            val stampsNeeded = stamps.associateWith { 1 }.toMutableMap()
            fun getStampsNeeded(target: Int): Int {
                if (target !in stampsNeeded) {
                    stampsNeeded[target] = stamps.filter { target - it > 0 }.minOf { stamp ->
                        getStampsNeeded(target - stamp) + 1
                    }
                }
                return stampsNeeded[target]!!
            }

            println(input.sumOf { target ->
                (0..50).minOf {
                    getStampsNeeded(ceil(target.toDouble() / 2).toInt() - it) + getStampsNeeded((target / 2) + it)
                }
            })
        }
    }
}