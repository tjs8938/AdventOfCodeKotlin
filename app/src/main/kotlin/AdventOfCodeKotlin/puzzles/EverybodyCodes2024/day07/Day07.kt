package year2024.day07

import year2024.day06.Day06
import kotlin.math.ceil

fun main() {
    Day07.part1()
    Day07.part2()
    Day07.part3()
}

class Day07 {
    companion object {
        fun part1() {
            val input = """
                D:=,+,+,-,+,=,-,-,=,+
                H:-,+,=,=,+,-,+,=,+,-
                A:=,+,+,-,+,=,=,-,+,-
                F:+,-,=,+,=,-,=,+,+,-
                J:+,+,+,=,=,-,=,-,+,-
                K:=,+,+,+,-,=,-,-,+,=
                G:=,+,+,-,=,+,=,+,-,-
                I:+,=,-,-,=,+,=,+,-,+
                E:-,+,+,+,-,=,-,=,+,=
            """.trimIndent().split("\n")
            println(input.sortedBy {
                var points = 0
                var increment = 10
                it.split(":")[1].split(",").forEach { action ->
                    if (action == "+") {
                        increment++
                    } else if (action == "-" && increment > 0) {
                        increment--
                    }
                    points += increment
                }
                points
            }.reversed().joinToString("") { it.split(":")[0] })
        }

        fun part2() {
            val input = """
                E:-,+,+,+,+,=,-,+,-,+,+,-,=,-,+,=,-,+,+,=,=,+,+,+,-,=,+,+,=,+,+,=,-,-,+,+,=,=,-,+
                C:+,+,+,+,=,+,-,+,=,-,-,=,=,+,+,-,+,+,=,+,-,+,+,=,=,+,+,-,-,+,+,-,-,=,=,-,+,=,+,+
                J:+,+,+,=,+,-,-,=,+,+,+,=,-,+,+,-,=,=,=,+,=,+,+,-,+,-,-,-,+,+,=,+,=,+,+,-,-,+,+,=
                B:+,+,-,=,-,+,=,+,+,-,=,+,=,+,=,+,-,=,+,+,-,+,+,+,-,+,=,-,+,-,=,+,+,-,-,+,=,=,+,+
                F:+,+,+,-,+,-,+,-,+,+,=,=,=,+,-,+,-,=,+,+,=,-,-,-,=,+,+,+,+,-,=,=,-,+,+,=,+,=,+,+
                H:-,=,=,+,+,-,-,-,+,=,+,-,=,=,=,+,+,-,=,+,+,+,+,-,+,+,-,=,+,=,+,+,+,-,-,+,+,+,+,=
                I:=,=,+,-,-,=,+,+,+,+,-,=,+,+,+,+,+,+,+,+,+,=,=,=,+,=,-,+,+,+,-,=,-,=,+,-,-,-,-,+
                A:-,=,+,+,+,+,-,+,-,=,+,=,-,+,+,+,+,=,=,-,=,+,+,+,+,-,+,+,-,+,=,+,+,+,-,-,=,=,=,-
                G:+,-,=,+,+,+,=,+,+,-,+,-,-,=,+,-,+,+,=,+,-,-,=,=,+,+,-,+,+,-,+,=,=,+,+,+,+,-,=,=
            """.trimIndent().split("\n")

            val trackInput = """
                S-=++=-==++=++=-=+=-=+=+=--=-=++=-==++=-+=-=+=-=+=+=++=-+==++=++=-=-=--
                -                                                                     -
                =                                                                     =
                +                                                                     +
                =                                                                     +
                +                                                                     =
                =                                                                     =
                -                                                                     -
                --==++++==+=+++-=+=-=+=-+-=+-=+-=+=-=+=--=+++=++=+++==++==--=+=++==+++-
            """.trimIndent().split("\n")

            val track =
                trackInput[0].drop(1) + (1 until trackInput.size - 1).map { trackInput[it][trackInput[0].length - 1] }
                    .joinToString("") +
                        trackInput.last().reversed() + (1 until trackInput.size - 1).reversed()
                    .map { trackInput[it][0] }.joinToString("") + "S"

            val knights = input.map { Knight(it.split(":")[0], it.split(":")[1].split(",")) }
            repeat(10) {
                track.forEach {
                    knights.forEach { knight ->
                        knight.move(it.toString())
                    }
                }
            }
            println(knights.sorted().reversed().map { it.name }.joinToString(""))
        }

        class Knight(val name: String, val actions: List<String>) : Comparable<Knight> {
            var energy = 0
            var increment = 10
            var index = 0

            fun move(track: String) {
                val action = if (track == "+" || track == "-") {
                    track
                } else {
                    actions[index]
                }
                if (action == "+") {
                    increment++
                } else if (action == "-" && increment > 0) {
                    increment--
                }
                energy += increment
                index = (index + 1) % actions.size
            }

            override fun compareTo(other: Knight): Int {
                return energy.compareTo(other.energy)
            }

            override fun toString(): String {
                return actions.joinToString(",")
            }
        }


        fun part3() {
            val trackInput = """
                S+= +=-== +=++=     =+=+=--=    =-= ++=     +=-  =+=++=-+==+ =++=-=-=--
                - + +   + =   =     =      =   == = - -     - =  =         =-=        -
                = + + +-- =-= ==-==-= --++ +  == == = +     - =  =    ==++=    =++=-=++
                + + + =     +         =  + + == == ++ =     = =  ==   =   = =++=
                = = + + +== +==     =++ == =+=  =  +  +==-=++ =   =++ --= + =
                + ==- = + =   = =+= =   =       ++--          +     =   = = =--= ==++==
                =     ==- ==+-- = = = ++= +=--      ==+ ==--= +--+=-= ==- ==   =+=    =
                -               = = = =   +  +  ==+ = = +   =        ++    =          -
                -               = + + =   +  -  = + = = +   =        +     =          -
                --==++++==+=+++-= =-= =-+-=  =+-= =-= =--   +=++=+++==     -=+=++==+++-
            """.trimIndent().split("\n")
            val visited = mutableSetOf(0 to 0)
            val trackSB = StringBuilder("+")
            var current = 0 to 1
            while (current != 0 to 0) {
                val neighbors = listOf(
                    current.first - 1 to current.second,
                    current.first + 1 to current.second,
                    current.first to current.second - 1,
                    current.first to current.second + 1
                )
                val next = neighbors.firstOrNull {
                    try {
                        !visited.contains(it) && trackInput[it.first][it.second] != ' '
                    } catch (e: Exception) {
                        false
                    }
                }
                if (next == null) {
                    trackSB.append("S")
                    break
                }
                trackSB.append(trackInput[next!!.first][next.second])
                visited.add(current)
                current = next
            }
            val track = trackSB.repeat(11)

            var actionPlan = StringBuilder()
            val remainingActions = mutableMapOf(
                "+" to 5,
                "-" to 3,
                "=" to 3
            )
            val actions = listOf("+", "-", "=")
            val allPlans = mutableSetOf<String>()

            fun buildPlans(currentPlan: StringBuilder, remainingActions: MutableMap<String, Int>) {
                if (remainingActions.values.sum() == 0) {
                    allPlans.add(currentPlan.toString())
                    return
                }

                actions.forEach { action ->
                    if (remainingActions[action]!! > 0) {
                        actionPlan.append(action)
                        remainingActions[action] = remainingActions[action]!! - 1
                        buildPlans(actionPlan, remainingActions)
                        remainingActions[action] = remainingActions[action]!! + 1
                        actionPlan.deleteCharAt(actionPlan.length - 1)
                    }
                }

            }
            buildPlans(actionPlan, remainingActions)
            val knights = allPlans.mapIndexed { index, s -> Knight("Knight$index", s.toList().map { it.toString() }) }
            track.forEach {
                knights.forEach { knight ->
                    knight.move(it.toString())
                }
            }
            val enemyScore = knights.first { it.actions.joinToString(",") == "+,-,-,+,+,=,+,-,=,+,="  }.energy
            println(knights.filter { it.energy > enemyScore }.size)
        }
    }
}