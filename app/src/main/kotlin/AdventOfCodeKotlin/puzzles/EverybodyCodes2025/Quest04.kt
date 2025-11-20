package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

import kotlin.math.ceil

fun main(args: Array<String>) {
    Quest04.part1()
    Quest04.part2()
    Quest04.part3()
}

class Quest04 {
    companion object {
        fun part1() {
            val input = """
                1000
                973
                971
                968
                950
                948
                942
                919
                904
                888
                861
                835
                820
                805
                776
                756
                728
                726
                712
                696
                684
                658
                631
                620
                599
                596
                576
                547
                519
                495
                473
                464
                447
                435
                412
                391
                381
                376
                359
                340
                314
                287
                266
                264
                254
                234
                211
                184
                179
                178
            """.trimIndent().lines().map { line -> line.toInt() }
            println(input.first() * 2025 / input.last())
        }

        fun part2() {
            val input = """
                950
                926
                916
                896
                892
                880
                876
                870
                852
                824
                806
                778
                769
                757
                756
                748
                720
                700
                691
                665
                652
                647
                643
                629
                615
                607
                595
                594
                577
                553
                528
                511
                484
                455
                429
                412
                388
                377
                367
                340
                325
                298
                279
                269
                265
                263
                248
                243
                218
                176
            """.trimIndent().lines().map { line -> line.toInt() }
            println(ceil(10000000000000.0 * input.last() / input.first()).toLong())
        }

        fun part3() {
            val input = """
                656
                640|640
                626|626
                610|610
                597|1194
                584|584
                578|1734
                575|575
                558|2232
                553|553
                545|2180
                543|543
                528|1056
                517|517
                514|514
                513|513
                511|2044
                493|493
                484|968
                469|469
                460|460
                454|454
                450|1800
                445|445
                433|1732
                425|425
                410|820
                405|405
                388|1552
                374|374
                359|359
                340|340
                339|339
                332|332
                318|1272
                317|317
                299|897
                280|280
                278|1112
                273|273
                261|783
                244|244
                239|478
                233|233
                221|221
                216|216
                201|603
                192|192
                187|561
                155
            """.trimIndent().lines()
            val firstGear = input.first().toLong()
            val lastGear = input.last().toLong()
            val multiplier = input.drop(1).dropLast(1).map { it.split('|') }.map { it[1].toInt() / it[0].toInt() }.fold(1L) { acc, i -> acc * i }
            println(100 * firstGear * multiplier / lastGear)
        }
    }
}