package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest01.Companion.part1
import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest01.Companion.part2
import AdventOfCodeKotlin.puzzles.EverybodyCodes2025.Quest01.Companion.part3

fun main(args: Array<String>) {
    part1()
    part2()
    part3()
}

class Quest01 {

    companion object {
        fun part1() {
            println("Everybody Codes 2025 - Quest 01 - Part 1")
        }

        fun part2() {
            val (names, moves) = """
                Narpyros,Urardith,Xendgalor,Kalmirix,Darfroth,Qalwyris,Orylar,Elarulth,Fenmyr,Vornrovan,Palthendris,Xyrlyr,Elarroth,Drethlyr,Adalxel,Zyrzal,Lirulrix,Torpyr,Balkynar,Ralzris

                L12,R5,L13,R14,L13,R19,L8,R8,L17,R8,L5,R17,L5,R9,L5,R8,L5,R5,L5,R6,L13,R10,L19,R15,L14,R12,L13,R13,L13
            """.trimIndent().split("\n\n")
            val nameList = names.split(",").map { it.trim() }
            val movesList = moves.split(",").map { it.replace("R", "") .replace ('L', '-').trim().toInt() }
            var counter = 0
            for (move in movesList) {
                counter = (counter + move).mod(nameList.size)
            }
            println(nameList[counter])
        }

        fun part3() {
            val (names, moves) = """
                Nyrixidris,Thalrovan,Thyroskyris,Ylarkael,Durntal,Darardith,Ferxaril,Drethvoran,Kronsyron,Gorathvyr,Lithkris,Brythfal,Zyrvoran,Rahnarel,Zarathvynar,Belzris,Elthal,Ardengaz,Zornoris,Qyrapyxis,Rylarasis,Silthyn,Tharnnarith,Naldnixis,Rynmirath,Vyrlfeth,Drakthar,Brivnix,Agnarryn,Aeldra

                L43,R13,L22,R13,L5,R31,L39,R13,L16,R30,L35,R33,L32,R41,L13,R30,L26,R7,L10,R21,L5,R40,L5,R24,L5,R17,L5,R18,L5,R25,L5,R16,L5,R36,L5,R21,L5,R47,L5,R32,L49,R38,L40,R33,L46,R19,L46,R24,L26,R34,L36,R7,L42,R16,L20,R37,L26,R8,L39
            """.trimIndent().split("\n\n")
            val nameList = names.split(",").map { it.trim() }.toMutableList()
            val movesList = moves.split(",").map { it.replace("R", "") .replace ('L', '-').trim().toInt() }
            for (move in movesList) {
                val index = move.mod(nameList.size)
                val temp = nameList[index]
                nameList[index] = nameList[0]
                nameList[0] = temp
            }
            println(nameList[0])
        }
    }
}