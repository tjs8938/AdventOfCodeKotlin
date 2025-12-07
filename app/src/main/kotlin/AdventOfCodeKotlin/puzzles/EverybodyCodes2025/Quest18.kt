package AdventOfCodeKotlin.puzzles.EverybodyCodes2025

import kotlin.text.split

class Quest18 {
    companion object {


        abstract class Plant(val id: Int, val thickness: Int) {
            abstract fun energy(): Long
        }

        class FreeBranch(id: Int, thickness: Int) : Plant(id, thickness) {
            var activated = true
            override fun energy(): Long {
                return if (activated) 1 else 0
            }
        }

        class ConnectedBranch(id: Int, thickness: Int, val allPlants: Map<Int, Plant>) : Plant(id, thickness) {
            val connections = mutableMapOf<Int, Int>()
            override fun energy(): Long {
                val connectedEnergy = connections.map { (plantId, branchThickness) ->
                    val plant = allPlants[plantId]!!
                    plant.energy() * branchThickness
                }.sum()

                return if (connectedEnergy >= thickness) {
                    connectedEnergy
                } else {
                    0
                }
            }
        }

        fun part1() {
            val input = """
                Plant 1 with thickness 1:
                - free branch with thickness 1

                Plant 2 with thickness 1:
                - free branch with thickness 1

                Plant 3 with thickness 1:
                - free branch with thickness 1

                Plant 4 with thickness 1:
                - free branch with thickness 1

                Plant 5 with thickness 1:
                - free branch with thickness 1

                Plant 6 with thickness 1:
                - free branch with thickness 1

                Plant 7 with thickness 1:
                - free branch with thickness 1

                Plant 8 with thickness 1:
                - free branch with thickness 1

                Plant 9 with thickness 1:
                - free branch with thickness 1

                Plant 10 with thickness 37:
                - branch to Plant 4 with thickness 33
                - branch to Plant 3 with thickness 26
                - branch to Plant 5 with thickness 37

                Plant 11 with thickness 43:
                - branch to Plant 2 with thickness 25
                - branch to Plant 9 with thickness 10

                Plant 12 with thickness 49:
                - branch to Plant 8 with thickness 32
                - branch to Plant 2 with thickness 25
                - branch to Plant 7 with thickness 31
                - branch to Plant 1 with thickness 37
                - branch to Plant 3 with thickness 47
                - branch to Plant 9 with thickness 46

                Plant 13 with thickness 18:
                - branch to Plant 2 with thickness 19
                - branch to Plant 1 with thickness 22
                - branch to Plant 5 with thickness 32

                Plant 14 with thickness 45:
                - branch to Plant 7 with thickness 31
                - branch to Plant 5 with thickness 13

                Plant 15 with thickness 76:
                - branch to Plant 12 with thickness 65
                - branch to Plant 13 with thickness 40

                Plant 16 with thickness 77:
                - branch to Plant 14 with thickness 45
                - branch to Plant 12 with thickness 27
                - branch to Plant 11 with thickness 62

                Plant 17 with thickness 55:
                - branch to Plant 12 with thickness 11
                - branch to Plant 14 with thickness 49

                Plant 18 with thickness 96:
                - branch to Plant 12 with thickness 24
                - branch to Plant 11 with thickness 37

                Plant 19 with thickness 1:
                - branch to Plant 15 with thickness 31
                - branch to Plant 16 with thickness 97
                - branch to Plant 17 with thickness 84
                - branch to Plant 18 with thickness 73
            """.trimIndent().split("\n\n")


            val plants = parsePlants(input)

            println(plants[plants.size]!!.energy())

        }

        private fun parsePlants(input: List<String>): MutableMap<Int, Plant> {
            val plants = mutableMapOf<Int, Plant>()
            input.forEach { plantBlock ->
                val lines = plantBlock.lines()
                val header = lines[0]
                val plantId = header.substringAfter("Plant ").substringBefore(" with").toInt()
                val plantThickness = header.substringAfter("thickness ").substringBefore(":").toInt()
                if (lines.size == 2 && lines[1].trim() == "- free branch with thickness $plantThickness") {
                    plants[plantId] = FreeBranch(plantId, plantThickness)
                } else {
                    val connectedBranch = ConnectedBranch(plantId, plantThickness, plants)
                    lines.drop(1).forEach { line ->
                        val parts = line.trim().substringAfter("- branch to Plant ").split(" with thickness ")
                        val targetPlantId = parts[0].toInt()
                        val branchThickness = parts[1].toInt()
                        connectedBranch.connections[targetPlantId] = branchThickness
                    }
                    plants[plantId] = connectedBranch
                }
            }
            return plants
        }

        fun part2() {
            val (input, testCases) = """
                Plant 1 with thickness 1:
                - free branch with thickness 1

                Plant 2 with thickness 1:
                - free branch with thickness 1

                Plant 3 with thickness 1:
                - free branch with thickness 1

                Plant 4 with thickness 1:
                - free branch with thickness 1

                Plant 5 with thickness 1:
                - free branch with thickness 1

                Plant 6 with thickness 1:
                - free branch with thickness 1

                Plant 7 with thickness 1:
                - free branch with thickness 1

                Plant 8 with thickness 1:
                - free branch with thickness 1

                Plant 9 with thickness 1:
                - free branch with thickness 1

                Plant 10 with thickness 1:
                - free branch with thickness 1

                Plant 11 with thickness 1:
                - free branch with thickness 1

                Plant 12 with thickness 1:
                - free branch with thickness 1

                Plant 13 with thickness 1:
                - free branch with thickness 1

                Plant 14 with thickness 1:
                - free branch with thickness 1

                Plant 15 with thickness 1:
                - free branch with thickness 1

                Plant 16 with thickness 1:
                - free branch with thickness 1

                Plant 17 with thickness 16:
                - branch to Plant 1 with thickness 18
                - branch to Plant 2 with thickness 10
                - branch to Plant 3 with thickness 1
                - branch to Plant 4 with thickness 7
                - branch to Plant 5 with thickness -3
                - branch to Plant 6 with thickness 1
                - branch to Plant 7 with thickness 7
                - branch to Plant 8 with thickness 22
                - branch to Plant 9 with thickness -1
                - branch to Plant 10 with thickness 1
                - branch to Plant 11 with thickness 2
                - branch to Plant 12 with thickness 27
                - branch to Plant 13 with thickness -7
                - branch to Plant 14 with thickness 16
                - branch to Plant 15 with thickness 1
                - branch to Plant 16 with thickness 28

                Plant 18 with thickness 13:
                - branch to Plant 1 with thickness 19
                - branch to Plant 2 with thickness -6
                - branch to Plant 3 with thickness -7
                - branch to Plant 4 with thickness 14
                - branch to Plant 5 with thickness 22
                - branch to Plant 6 with thickness 12
                - branch to Plant 7 with thickness 10
                - branch to Plant 8 with thickness 27
                - branch to Plant 9 with thickness 15
                - branch to Plant 10 with thickness 6
                - branch to Plant 11 with thickness 2
                - branch to Plant 12 with thickness -7
                - branch to Plant 13 with thickness 23
                - branch to Plant 14 with thickness 11
                - branch to Plant 15 with thickness 27
                - branch to Plant 16 with thickness 5

                Plant 19 with thickness 11:
                - branch to Plant 1 with thickness 24
                - branch to Plant 2 with thickness 18
                - branch to Plant 3 with thickness -10
                - branch to Plant 4 with thickness 4
                - branch to Plant 5 with thickness -5
                - branch to Plant 6 with thickness 25
                - branch to Plant 7 with thickness 28
                - branch to Plant 8 with thickness 2
                - branch to Plant 9 with thickness 4
                - branch to Plant 10 with thickness -9
                - branch to Plant 11 with thickness -5
                - branch to Plant 12 with thickness -9
                - branch to Plant 13 with thickness 25
                - branch to Plant 14 with thickness 16
                - branch to Plant 15 with thickness 28
                - branch to Plant 16 with thickness 25

                Plant 20 with thickness 6:
                - branch to Plant 1 with thickness -2
                - branch to Plant 2 with thickness 15
                - branch to Plant 3 with thickness 1
                - branch to Plant 4 with thickness 9
                - branch to Plant 5 with thickness 10
                - branch to Plant 6 with thickness 6
                - branch to Plant 7 with thickness 24
                - branch to Plant 8 with thickness 14
                - branch to Plant 9 with thickness -7
                - branch to Plant 10 with thickness -6
                - branch to Plant 11 with thickness 29
                - branch to Plant 12 with thickness 14
                - branch to Plant 13 with thickness 11
                - branch to Plant 14 with thickness 15
                - branch to Plant 15 with thickness -1
                - branch to Plant 16 with thickness 5

                Plant 21 with thickness 2:
                - branch to Plant 1 with thickness 7
                - branch to Plant 2 with thickness 20
                - branch to Plant 3 with thickness -1
                - branch to Plant 4 with thickness -4
                - branch to Plant 5 with thickness 22
                - branch to Plant 6 with thickness 5
                - branch to Plant 7 with thickness 5
                - branch to Plant 8 with thickness -3
                - branch to Plant 9 with thickness 21
                - branch to Plant 10 with thickness 1
                - branch to Plant 11 with thickness -2
                - branch to Plant 12 with thickness -3
                - branch to Plant 13 with thickness 1
                - branch to Plant 14 with thickness 0
                - branch to Plant 15 with thickness -9
                - branch to Plant 16 with thickness 29

                Plant 22 with thickness 9:
                - branch to Plant 1 with thickness 15
                - branch to Plant 2 with thickness -6
                - branch to Plant 3 with thickness -6
                - branch to Plant 4 with thickness 27
                - branch to Plant 5 with thickness 19
                - branch to Plant 6 with thickness 10
                - branch to Plant 7 with thickness 9
                - branch to Plant 8 with thickness 19
                - branch to Plant 9 with thickness 14
                - branch to Plant 10 with thickness 22
                - branch to Plant 11 with thickness -6
                - branch to Plant 12 with thickness 11
                - branch to Plant 13 with thickness -5
                - branch to Plant 14 with thickness 22
                - branch to Plant 15 with thickness 6
                - branch to Plant 16 with thickness 27

                Plant 23 with thickness 9:
                - branch to Plant 1 with thickness 21
                - branch to Plant 2 with thickness 20
                - branch to Plant 3 with thickness 11
                - branch to Plant 4 with thickness 18
                - branch to Plant 5 with thickness 12
                - branch to Plant 6 with thickness -8
                - branch to Plant 7 with thickness -2
                - branch to Plant 8 with thickness 16
                - branch to Plant 9 with thickness 10
                - branch to Plant 10 with thickness 10
                - branch to Plant 11 with thickness 9
                - branch to Plant 12 with thickness -10
                - branch to Plant 13 with thickness 21
                - branch to Plant 14 with thickness -8
                - branch to Plant 15 with thickness 10
                - branch to Plant 16 with thickness 23

                Plant 24 with thickness 4:
                - branch to Plant 1 with thickness 14
                - branch to Plant 2 with thickness -1
                - branch to Plant 3 with thickness 29
                - branch to Plant 4 with thickness -1
                - branch to Plant 5 with thickness 8
                - branch to Plant 6 with thickness -9
                - branch to Plant 7 with thickness 1
                - branch to Plant 8 with thickness 0
                - branch to Plant 9 with thickness -8
                - branch to Plant 10 with thickness 4
                - branch to Plant 11 with thickness -8
                - branch to Plant 12 with thickness 8
                - branch to Plant 13 with thickness 16
                - branch to Plant 14 with thickness -10
                - branch to Plant 15 with thickness 4
                - branch to Plant 16 with thickness -8

                Plant 25 with thickness 13:
                - branch to Plant 1 with thickness 15
                - branch to Plant 2 with thickness 14
                - branch to Plant 3 with thickness 28
                - branch to Plant 4 with thickness -7
                - branch to Plant 5 with thickness 13
                - branch to Plant 6 with thickness 14
                - branch to Plant 7 with thickness 18
                - branch to Plant 8 with thickness 22
                - branch to Plant 9 with thickness 10
                - branch to Plant 10 with thickness 4
                - branch to Plant 11 with thickness 26
                - branch to Plant 12 with thickness -7
                - branch to Plant 13 with thickness 23
                - branch to Plant 14 with thickness 15
                - branch to Plant 15 with thickness -2
                - branch to Plant 16 with thickness 8

                Plant 26 with thickness 18:
                - branch to Plant 1 with thickness -2
                - branch to Plant 2 with thickness 25
                - branch to Plant 3 with thickness 18
                - branch to Plant 4 with thickness 15
                - branch to Plant 5 with thickness 13
                - branch to Plant 6 with thickness 14
                - branch to Plant 7 with thickness 21
                - branch to Plant 8 with thickness 4
                - branch to Plant 9 with thickness 17
                - branch to Plant 10 with thickness 18
                - branch to Plant 11 with thickness 4
                - branch to Plant 12 with thickness 16
                - branch to Plant 13 with thickness 12
                - branch to Plant 14 with thickness 17
                - branch to Plant 15 with thickness 4
                - branch to Plant 16 with thickness -3

                Plant 27 with thickness 89:
                - branch to Plant 17 with thickness -10
                - branch to Plant 18 with thickness -1
                - branch to Plant 19 with thickness -16
                - branch to Plant 20 with thickness -19
                - branch to Plant 21 with thickness 4
                - branch to Plant 22 with thickness 24
                - branch to Plant 23 with thickness 59
                - branch to Plant 24 with thickness 19
                - branch to Plant 25 with thickness -13
                - branch to Plant 26 with thickness 27

                Plant 28 with thickness 71:
                - branch to Plant 17 with thickness 15
                - branch to Plant 18 with thickness -7
                - branch to Plant 19 with thickness 31
                - branch to Plant 20 with thickness 35
                - branch to Plant 21 with thickness 67
                - branch to Plant 22 with thickness 54
                - branch to Plant 23 with thickness 60
                - branch to Plant 24 with thickness 28
                - branch to Plant 25 with thickness 25
                - branch to Plant 26 with thickness 8

                Plant 29 with thickness 68:
                - branch to Plant 17 with thickness 24
                - branch to Plant 18 with thickness 38
                - branch to Plant 19 with thickness 37
                - branch to Plant 20 with thickness -20
                - branch to Plant 21 with thickness 13
                - branch to Plant 22 with thickness 28
                - branch to Plant 23 with thickness -19
                - branch to Plant 24 with thickness -14
                - branch to Plant 25 with thickness 30
                - branch to Plant 26 with thickness 46

                Plant 30 with thickness 79:
                - branch to Plant 17 with thickness 37
                - branch to Plant 18 with thickness 49
                - branch to Plant 19 with thickness 47
                - branch to Plant 20 with thickness 52
                - branch to Plant 21 with thickness 11
                - branch to Plant 22 with thickness 13
                - branch to Plant 23 with thickness 46
                - branch to Plant 24 with thickness 43
                - branch to Plant 25 with thickness 49
                - branch to Plant 26 with thickness 5

                Plant 31 with thickness 66:
                - branch to Plant 17 with thickness 13
                - branch to Plant 18 with thickness -11
                - branch to Plant 19 with thickness 2
                - branch to Plant 20 with thickness 21
                - branch to Plant 21 with thickness 20
                - branch to Plant 22 with thickness 0
                - branch to Plant 23 with thickness 38
                - branch to Plant 24 with thickness 47
                - branch to Plant 25 with thickness 42
                - branch to Plant 26 with thickness 3

                Plant 32 with thickness 9:
                - branch to Plant 17 with thickness 33
                - branch to Plant 18 with thickness 6
                - branch to Plant 19 with thickness 42
                - branch to Plant 20 with thickness 44
                - branch to Plant 21 with thickness 33
                - branch to Plant 22 with thickness 61
                - branch to Plant 23 with thickness 40
                - branch to Plant 24 with thickness 69
                - branch to Plant 25 with thickness 1
                - branch to Plant 26 with thickness 30

                Plant 33 with thickness 13:
                - branch to Plant 27 with thickness 52
                - branch to Plant 28 with thickness 71
                - branch to Plant 29 with thickness 13
                - branch to Plant 30 with thickness 64
                - branch to Plant 31 with thickness 41
                - branch to Plant 32 with thickness 65

                Plant 34 with thickness 38:
                - branch to Plant 27 with thickness 48
                - branch to Plant 28 with thickness 55
                - branch to Plant 29 with thickness 25
                - branch to Plant 30 with thickness 14
                - branch to Plant 31 with thickness 64
                - branch to Plant 32 with thickness 29

                Plant 35 with thickness 46:
                - branch to Plant 27 with thickness 26
                - branch to Plant 28 with thickness 6
                - branch to Plant 29 with thickness 47
                - branch to Plant 30 with thickness -11
                - branch to Plant 31 with thickness -15
                - branch to Plant 32 with thickness -20

                Plant 36 with thickness 95264501:
                - branch to Plant 33 with thickness 25
                - branch to Plant 34 with thickness 10
                - branch to Plant 35 with thickness 27


                1 1 0 0 0 1 0 0 0 0 1 1 1 0 1 1
                0 1 0 0 1 1 0 0 0 1 0 0 1 0 0 0
                0 0 1 0 0 0 0 0 0 0 1 0 1 0 1 1
                0 0 1 1 0 1 0 0 0 0 0 0 0 1 0 0
                1 1 1 1 0 0 1 0 1 1 0 1 1 0 0 1
                0 0 0 0 1 0 1 1 1 0 1 0 0 0 1 1
                1 0 0 0 0 1 0 1 1 1 1 0 1 0 1 1
                1 0 1 0 0 0 1 0 0 1 0 1 1 0 1 0
                0 0 1 1 1 1 0 1 1 1 1 0 1 1 1 0
                1 0 0 1 1 1 0 0 0 1 1 1 1 0 1 0
                1 1 1 0 1 0 1 0 1 0 0 0 1 1 0 1
                0 0 1 1 1 0 1 0 1 1 1 1 1 0 0 1
                0 1 0 1 1 0 0 1 0 1 1 0 0 1 0 0
                0 0 0 1 1 1 1 1 0 0 0 1 1 0 1 0
                0 0 1 1 1 0 0 1 0 1 0 1 1 1 0 1
                1 0 0 0 0 0 1 0 1 0 0 1 1 0 1 0
                0 1 1 1 1 1 0 0 1 0 1 0 0 0 1 1
                1 1 0 1 1 0 0 0 1 1 1 1 1 0 1 0
                0 1 1 1 0 1 1 0 1 1 1 0 1 1 0 0
                1 0 1 1 0 0 1 1 1 0 1 1 1 1 1 0
                0 0 1 1 0 1 1 1 0 0 1 1 1 1 1 0
                0 0 0 0 1 1 0 1 1 1 0 1 0 0 1 0
                0 1 1 1 0 0 1 1 1 1 1 1 0 1 1 1
                1 0 0 0 0 0 1 0 0 0 1 1 1 1 0 0
                0 1 1 0 0 1 1 0 1 0 1 0 1 0 1 0
                1 0 0 0 1 1 1 1 0 0 1 0 0 1 1 0
                0 1 0 0 0 1 1 1 0 0 0 0 1 1 0 0
                0 1 1 0 0 0 1 1 0 1 0 1 1 1 0 0
                0 0 0 1 0 0 0 1 0 0 0 0 0 1 1 0
                1 0 0 0 1 1 1 1 0 0 0 1 0 0 1 1
                1 0 0 0 0 0 0 1 1 0 1 0 1 0 1 1
                1 1 0 0 0 1 1 0 0 0 1 1 0 0 1 0
                1 0 1 0 1 0 1 1 0 1 1 0 0 0 0 1
                1 1 0 0 1 0 0 0 1 1 0 1 1 1 0 0
                1 1 1 0 1 0 1 1 0 0 0 1 1 0 0 1
                1 0 1 0 0 0 1 1 1 1 1 1 0 0 1 0
                1 1 1 1 1 1 0 0 0 1 0 0 0 1 0 1
                0 1 1 0 0 1 0 1 0 0 1 1 0 1 0 0
                0 1 1 0 1 0 1 0 0 1 1 0 0 1 0 0
                1 1 1 0 0 1 1 0 0 0 0 0 0 1 0 0
                1 1 1 1 0 1 1 1 0 1 0 1 1 0 0 0
                0 1 0 0 1 1 0 1 1 0 0 1 0 1 0 0
                1 0 0 1 1 0 0 0 1 1 0 0 1 0 1 1
                1 1 1 1 0 1 0 1 1 1 0 1 1 1 0 1
                0 0 0 0 0 1 1 1 0 0 1 1 0 1 1 1
                1 0 0 1 1 0 0 1 0 0 1 1 0 1 1 1
                0 0 1 0 0 1 0 0 1 1 0 1 1 0 0 1
                0 1 1 1 0 0 1 0 1 0 0 1 0 0 0 1
                1 0 1 1 1 0 0 1 1 1 0 0 0 0 0 1
                1 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0
                0 0 0 1 0 0 1 0 1 1 0 0 0 0 0 1
                0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 0
                1 1 1 0 0 1 0 1 1 0 1 0 0 0 1 1
                1 1 0 1 0 0 0 1 0 1 0 1 0 0 1 1
                1 1 0 1 1 1 0 1 0 0 1 0 0 0 0 1
                1 1 1 0 0 0 0 0 0 1 1 1 1 1 0 1
                1 0 0 0 1 1 1 1 0 0 1 0 0 0 0 0
                1 1 0 1 1 1 0 0 0 1 0 0 0 0 1 0
                0 1 1 1 1 0 0 1 0 0 0 0 1 1 0 1
                1 0 1 0 1 0 0 0 1 1 1 1 1 1 1 0
                0 0 1 0 0 0 0 1 1 1 1 0 0 1 0 1
                1 0 1 1 0 0 1 0 0 1 1 1 1 1 0 1
                0 1 0 1 0 0 0 0 0 0 1 0 0 1 1 1
                1 1 1 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 1 1 1 0 1 1 1 1 0 1 1 0 1 0 0
                0 1 1 1 1 0 0 0 1 0 1 1 1 1 1 0
                0 0 1 0 1 1 1 0 0 0 0 0 1 1 1 0
                1 1 0 1 0 1 1 1 1 0 1 1 0 1 1 0
                0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1
                0 0 1 0 0 1 0 0 0 0 1 0 1 0 1 1
                0 1 1 1 0 0 1 1 0 1 1 0 0 0 0 0
                1 0 0 1 1 1 0 1 0 0 0 0 0 0 1 0
                0 0 0 0 0 0 1 1 1 0 1 1 1 1 1 1
                0 1 1 1 1 0 0 1 0 1 1 1 0 1 1 0
                1 0 0 1 0 0 0 0 0 0 1 0 0 1 1 0
                0 1 0 1 0 0 0 1 0 1 1 1 1 1 0 1
                0 0 0 1 1 0 1 0 1 1 1 0 0 1 1 1
                1 1 1 0 1 1 1 1 1 0 1 0 1 0 0 0
                1 0 1 0 1 0 1 0 1 0 1 1 0 1 0 1
                0 1 0 0 1 0 1 1 1 0 0 1 0 0 1 0
                1 0 1 0 1 0 0 0 0 0 1 1 0 1 0 0
                0 0 0 0 0 1 1 1 0 0 0 0 0 1 1 0
                0 1 0 1 0 0 1 1 1 1 0 1 1 0 1 1
                1 1 1 1 0 1 0 0 1 1 1 1 1 1 0 0
                1 1 0 0 0 0 0 1 0 0 1 0 1 1 1 1
                1 1 0 1 0 0 1 1 1 0 1 1 1 0 0 0
                0 1 1 0 1 0 1 0 0 1 0 1 0 0 1 1
                1 0 1 0 1 0 0 0 1 0 0 0 1 1 0 1
                0 0 0 0 1 0 0 1 0 0 0 0 1 1 0 1
                0 1 1 0 1 1 1 0 0 1 1 1 0 0 0 1
                1 0 0 1 0 1 0 0 1 0 0 1 1 1 0 0
                0 0 1 0 0 1 0 1 0 1 1 0 0 0 0 0
                1 1 0 1 1 1 1 1 1 1 1 1 0 1 1 0
                0 1 0 1 1 1 0 0 0 0 0 0 0 0 0 0
                1 0 1 0 0 0 1 1 0 0 0 0 1 1 1 1
                1 1 0 0 0 1 1 0 0 1 0 0 1 1 1 0
                0 0 0 0 1 1 1 1 0 0 1 0 0 0 0 0
                1 1 1 1 0 0 0 0 0 1 1 1 0 0 0 1
                0 0 0 0 1 1 1 0 0 1 1 1 1 0 0 0
                0 0 0 1 1 1 1 1 0 1 1 0 0 1 1 0
            """.trimIndent().split("\n\n\n").let { it[0] to it[1] }
            val plants = parsePlants(input.split("\n\n"))
            testCases.lines().sumOf { testCase ->
                val activations = testCase.split(" ").map { it.toInt() }
                plants.filterValues { it is FreeBranch }.forEach { (id, plant) ->
                    (plant as FreeBranch).activated = activations[id - 1] == 1
                }
                plants[plants.size]!!.energy()
            }.let { println(it) }
        }

        fun part3() {
            val (input, testCases) = """
                Plant 1 with thickness 1:
                - free branch with thickness 1

                Plant 2 with thickness 1:
                - free branch with thickness 1

                Plant 3 with thickness 1:
                - free branch with thickness 1

                Plant 4 with thickness 1:
                - free branch with thickness 1

                Plant 5 with thickness 1:
                - free branch with thickness 1

                Plant 6 with thickness 1:
                - free branch with thickness 1

                Plant 7 with thickness 1:
                - free branch with thickness 1

                Plant 8 with thickness 1:
                - free branch with thickness 1

                Plant 9 with thickness 1:
                - free branch with thickness 1

                Plant 10 with thickness 1:
                - free branch with thickness 1

                Plant 11 with thickness 1:
                - free branch with thickness 1

                Plant 12 with thickness 1:
                - free branch with thickness 1

                Plant 13 with thickness 1:
                - free branch with thickness 1

                Plant 14 with thickness 1:
                - free branch with thickness 1

                Plant 15 with thickness 1:
                - free branch with thickness 1

                Plant 16 with thickness 1:
                - free branch with thickness 1

                Plant 17 with thickness 1:
                - free branch with thickness 1

                Plant 18 with thickness 1:
                - free branch with thickness 1

                Plant 19 with thickness 1:
                - free branch with thickness 1

                Plant 20 with thickness 1:
                - free branch with thickness 1

                Plant 21 with thickness 1:
                - free branch with thickness 1

                Plant 22 with thickness 1:
                - free branch with thickness 1

                Plant 23 with thickness 1:
                - free branch with thickness 1

                Plant 24 with thickness 1:
                - free branch with thickness 1

                Plant 25 with thickness 1:
                - free branch with thickness 1

                Plant 26 with thickness 1:
                - free branch with thickness 1

                Plant 27 with thickness 1:
                - free branch with thickness 1

                Plant 28 with thickness 1:
                - free branch with thickness 1

                Plant 29 with thickness 1:
                - free branch with thickness 1

                Plant 30 with thickness 1:
                - free branch with thickness 1

                Plant 31 with thickness 1:
                - free branch with thickness 1

                Plant 32 with thickness 1:
                - free branch with thickness 1

                Plant 33 with thickness 1:
                - free branch with thickness 1

                Plant 34 with thickness 1:
                - free branch with thickness 1

                Plant 35 with thickness 1:
                - free branch with thickness 1

                Plant 36 with thickness 1:
                - free branch with thickness 1

                Plant 37 with thickness 1:
                - free branch with thickness 1

                Plant 38 with thickness 1:
                - free branch with thickness 1

                Plant 39 with thickness 1:
                - free branch with thickness 1

                Plant 40 with thickness 1:
                - free branch with thickness 1

                Plant 41 with thickness 1:
                - free branch with thickness 1

                Plant 42 with thickness 1:
                - free branch with thickness 1

                Plant 43 with thickness 1:
                - free branch with thickness 1

                Plant 44 with thickness 1:
                - free branch with thickness 1

                Plant 45 with thickness 1:
                - free branch with thickness 1

                Plant 46 with thickness 1:
                - free branch with thickness 1

                Plant 47 with thickness 1:
                - free branch with thickness 1

                Plant 48 with thickness 1:
                - free branch with thickness 1

                Plant 49 with thickness 1:
                - free branch with thickness 1

                Plant 50 with thickness 1:
                - free branch with thickness 1

                Plant 51 with thickness 1:
                - free branch with thickness 1

                Plant 52 with thickness 1:
                - free branch with thickness 1

                Plant 53 with thickness 1:
                - free branch with thickness 1

                Plant 54 with thickness 1:
                - free branch with thickness 1

                Plant 55 with thickness 1:
                - free branch with thickness 1

                Plant 56 with thickness 1:
                - free branch with thickness 1

                Plant 57 with thickness 1:
                - free branch with thickness 1

                Plant 58 with thickness 1:
                - free branch with thickness 1

                Plant 59 with thickness 1:
                - free branch with thickness 1

                Plant 60 with thickness 1:
                - free branch with thickness 1

                Plant 61 with thickness 1:
                - free branch with thickness 1

                Plant 62 with thickness 1:
                - free branch with thickness 1

                Plant 63 with thickness 1:
                - free branch with thickness 1

                Plant 64 with thickness 1:
                - free branch with thickness 1

                Plant 65 with thickness 1:
                - free branch with thickness 1

                Plant 66 with thickness 1:
                - free branch with thickness 1

                Plant 67 with thickness 1:
                - free branch with thickness 1

                Plant 68 with thickness 1:
                - free branch with thickness 1

                Plant 69 with thickness 1:
                - free branch with thickness 1

                Plant 70 with thickness 1:
                - free branch with thickness 1

                Plant 71 with thickness 1:
                - free branch with thickness 1

                Plant 72 with thickness 1:
                - free branch with thickness 1

                Plant 73 with thickness 1:
                - free branch with thickness 1

                Plant 74 with thickness 1:
                - free branch with thickness 1

                Plant 75 with thickness 1:
                - free branch with thickness 1

                Plant 76 with thickness 1:
                - free branch with thickness 1

                Plant 77 with thickness 1:
                - free branch with thickness 1

                Plant 78 with thickness 1:
                - free branch with thickness 1

                Plant 79 with thickness 1:
                - free branch with thickness 1

                Plant 80 with thickness 1:
                - free branch with thickness 1

                Plant 81 with thickness 1:
                - free branch with thickness 1

                Plant 82 with thickness 36:
                - branch to Plant 1 with thickness -2
                - branch to Plant 2 with thickness -8
                - branch to Plant 3 with thickness 7
                - branch to Plant 4 with thickness -5
                - branch to Plant 5 with thickness 6
                - branch to Plant 6 with thickness -3
                - branch to Plant 7 with thickness 2
                - branch to Plant 8 with thickness -10
                - branch to Plant 9 with thickness -3

                Plant 83 with thickness 11:
                - branch to Plant 10 with thickness -2
                - branch to Plant 11 with thickness -3
                - branch to Plant 12 with thickness 3
                - branch to Plant 13 with thickness 2
                - branch to Plant 14 with thickness 6
                - branch to Plant 15 with thickness 3
                - branch to Plant 16 with thickness 6
                - branch to Plant 17 with thickness -2
                - branch to Plant 18 with thickness -7

                Plant 84 with thickness 11:
                - branch to Plant 19 with thickness -5
                - branch to Plant 20 with thickness 6
                - branch to Plant 21 with thickness -10
                - branch to Plant 22 with thickness -2
                - branch to Plant 23 with thickness -6
                - branch to Plant 24 with thickness -3
                - branch to Plant 25 with thickness -3
                - branch to Plant 26 with thickness 8
                - branch to Plant 27 with thickness -10

                Plant 85 with thickness 12:
                - branch to Plant 28 with thickness 6
                - branch to Plant 29 with thickness -5
                - branch to Plant 30 with thickness -6
                - branch to Plant 31 with thickness 8
                - branch to Plant 32 with thickness -6
                - branch to Plant 33 with thickness 8
                - branch to Plant 34 with thickness -5
                - branch to Plant 35 with thickness -10
                - branch to Plant 36 with thickness 8

                Plant 86 with thickness 11:
                - branch to Plant 37 with thickness 9
                - branch to Plant 38 with thickness -1
                - branch to Plant 39 with thickness -6
                - branch to Plant 40 with thickness -5
                - branch to Plant 41 with thickness -1
                - branch to Plant 42 with thickness -9
                - branch to Plant 43 with thickness -4
                - branch to Plant 44 with thickness -2
                - branch to Plant 45 with thickness 7

                Plant 87 with thickness 16:
                - branch to Plant 46 with thickness 5
                - branch to Plant 47 with thickness -4
                - branch to Plant 48 with thickness -10
                - branch to Plant 49 with thickness 5
                - branch to Plant 50 with thickness 5
                - branch to Plant 51 with thickness 2
                - branch to Plant 52 with thickness -7
                - branch to Plant 53 with thickness -5
                - branch to Plant 54 with thickness 9

                Plant 88 with thickness 28:
                - branch to Plant 55 with thickness -4
                - branch to Plant 56 with thickness 9
                - branch to Plant 57 with thickness -5
                - branch to Plant 58 with thickness -5
                - branch to Plant 59 with thickness 1
                - branch to Plant 60 with thickness -9
                - branch to Plant 61 with thickness -8
                - branch to Plant 62 with thickness 6
                - branch to Plant 63 with thickness -5

                Plant 89 with thickness 27:
                - branch to Plant 64 with thickness -6
                - branch to Plant 65 with thickness 7
                - branch to Plant 66 with thickness -10
                - branch to Plant 67 with thickness -3
                - branch to Plant 68 with thickness -3
                - branch to Plant 69 with thickness -10
                - branch to Plant 70 with thickness -7
                - branch to Plant 71 with thickness 6
                - branch to Plant 72 with thickness -10

                Plant 90 with thickness 24:
                - branch to Plant 73 with thickness -6
                - branch to Plant 74 with thickness -2
                - branch to Plant 75 with thickness 4
                - branch to Plant 76 with thickness 3
                - branch to Plant 77 with thickness 9
                - branch to Plant 78 with thickness 6
                - branch to Plant 79 with thickness 6
                - branch to Plant 80 with thickness -9
                - branch to Plant 81 with thickness -10

                Plant 91 with thickness 11:
                - branch to Plant 1 with thickness -10
                - branch to Plant 10 with thickness -8
                - branch to Plant 19 with thickness -1
                - branch to Plant 28 with thickness 4
                - branch to Plant 37 with thickness 6
                - branch to Plant 46 with thickness 4
                - branch to Plant 55 with thickness -10
                - branch to Plant 64 with thickness -1
                - branch to Plant 73 with thickness -10

                Plant 92 with thickness 18:
                - branch to Plant 2 with thickness -5
                - branch to Plant 11 with thickness -5
                - branch to Plant 20 with thickness 2
                - branch to Plant 29 with thickness -7
                - branch to Plant 38 with thickness -7
                - branch to Plant 47 with thickness -9
                - branch to Plant 56 with thickness 2
                - branch to Plant 65 with thickness 3
                - branch to Plant 74 with thickness -8

                Plant 93 with thickness 41:
                - branch to Plant 3 with thickness 9
                - branch to Plant 12 with thickness 8
                - branch to Plant 21 with thickness -5
                - branch to Plant 30 with thickness -4
                - branch to Plant 39 with thickness -6
                - branch to Plant 48 with thickness -10
                - branch to Plant 57 with thickness -5
                - branch to Plant 66 with thickness -3
                - branch to Plant 75 with thickness 8

                Plant 94 with thickness 29:
                - branch to Plant 4 with thickness -10
                - branch to Plant 13 with thickness 9
                - branch to Plant 22 with thickness -7
                - branch to Plant 31 with thickness 6
                - branch to Plant 40 with thickness -7
                - branch to Plant 49 with thickness 5
                - branch to Plant 58 with thickness -10
                - branch to Plant 67 with thickness -4
                - branch to Plant 76 with thickness 9

                Plant 95 with thickness 26:
                - branch to Plant 5 with thickness 1
                - branch to Plant 14 with thickness 4
                - branch to Plant 23 with thickness -7
                - branch to Plant 32 with thickness -1
                - branch to Plant 41 with thickness -1
                - branch to Plant 50 with thickness 4
                - branch to Plant 59 with thickness 1
                - branch to Plant 68 with thickness -5
                - branch to Plant 77 with thickness 3

                Plant 96 with thickness 22:
                - branch to Plant 6 with thickness -5
                - branch to Plant 15 with thickness 9
                - branch to Plant 24 with thickness -4
                - branch to Plant 33 with thickness 8
                - branch to Plant 42 with thickness -8
                - branch to Plant 51 with thickness 9
                - branch to Plant 60 with thickness -7
                - branch to Plant 69 with thickness -9
                - branch to Plant 78 with thickness 6

                Plant 97 with thickness 41:
                - branch to Plant 7 with thickness 1
                - branch to Plant 16 with thickness 6
                - branch to Plant 25 with thickness -4
                - branch to Plant 34 with thickness -10
                - branch to Plant 43 with thickness -10
                - branch to Plant 52 with thickness -8
                - branch to Plant 61 with thickness -5
                - branch to Plant 70 with thickness -4
                - branch to Plant 79 with thickness 6

                Plant 98 with thickness 21:
                - branch to Plant 8 with thickness -5
                - branch to Plant 17 with thickness -7
                - branch to Plant 26 with thickness 2
                - branch to Plant 35 with thickness -5
                - branch to Plant 44 with thickness -1
                - branch to Plant 53 with thickness -5
                - branch to Plant 62 with thickness 7
                - branch to Plant 71 with thickness 9
                - branch to Plant 80 with thickness -3

                Plant 99 with thickness 24:
                - branch to Plant 9 with thickness -3
                - branch to Plant 18 with thickness -4
                - branch to Plant 27 with thickness -8
                - branch to Plant 36 with thickness 9
                - branch to Plant 45 with thickness 6
                - branch to Plant 54 with thickness 4
                - branch to Plant 63 with thickness -6
                - branch to Plant 72 with thickness -8
                - branch to Plant 81 with thickness -4

                Plant 100 with thickness 116:
                - branch to Plant 82 with thickness 19
                - branch to Plant 91 with thickness 11

                Plant 101 with thickness 125:
                - branch to Plant 83 with thickness 10
                - branch to Plant 92 with thickness 12

                Plant 102 with thickness 115:
                - branch to Plant 84 with thickness 17
                - branch to Plant 93 with thickness 11

                Plant 103 with thickness 113:
                - branch to Plant 85 with thickness 16
                - branch to Plant 94 with thickness 10

                Plant 104 with thickness 119:
                - branch to Plant 86 with thickness 15
                - branch to Plant 95 with thickness 11

                Plant 105 with thickness 120:
                - branch to Plant 87 with thickness 12
                - branch to Plant 96 with thickness 14

                Plant 106 with thickness 127:
                - branch to Plant 88 with thickness 11
                - branch to Plant 97 with thickness 14

                Plant 107 with thickness 121:
                - branch to Plant 89 with thickness 11
                - branch to Plant 98 with thickness 18

                Plant 108 with thickness 114:
                - branch to Plant 90 with thickness 13
                - branch to Plant 99 with thickness 19

                Plant 109 with thickness 9715:
                - branch to Plant 100 with thickness 9
                - branch to Plant 101 with thickness 5
                - branch to Plant 102 with thickness 4
                - branch to Plant 103 with thickness 3
                - branch to Plant 104 with thickness 9
                - branch to Plant 105 with thickness 8
                - branch to Plant 106 with thickness 8
                - branch to Plant 107 with thickness 4
                - branch to Plant 108 with thickness 4


                0 1 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 1 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 0 1 1 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 0 0 0
                0 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 0 0 0 0 1 0 1 0 1 1 0 0 1 0 0 1 0 0 0 0 0 1 1 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 1 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 1 0 1 1 0 0 0 0 0 0 0 1 0 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 1 1 0 1 0 0 0 0 1 1 1 1 1 0 1 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 1 0 0 1 1 1 1 1 0 0 0 1 0 0 0 1 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 1 1 0 0 0 1 0 1 1 1 0 0
                0 0 1 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 1 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 1 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 0 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 0 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 1 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 1 1 0 1 0 0
                0 0 1 0 1 0 1 0 1 1 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 0 0 0 1 1 1 1 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 1 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 1 1 0 1 0 0 1 0 0 1 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 1 1 0 1 0 0 1 1 0 1 0 0 1 0 0 1 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 1 0 0 0 0 1 0 1 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 1 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 1 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 1 1 1 1 0 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 0 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 1 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 1 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 1 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 1 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 1 1 0 1 1 1 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 1 1 1 0 0 1 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                1 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 1 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 1 0 0 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 0 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 0 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 0 1 1 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 1 1 1 1 0 0 1 0 1 0 0 1 0 0 1 1 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 1
                0 0 1 0 1 0 1 1 0 0 0 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 0 1 0 1 0 0 0 0 1 1 1 0 0 0 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 1 1 0 0 0
                0 0 1 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 1 0 0 0 1 0 1 0 0 1 0 0 1 1 0 1 0 0 0 0 0 1 0 0 0 1 0 1 1 1 0 0
                0 0 1 0 1 1 0 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 1 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 1 0 1 0 0 1 0 1 0 0 0 1 0 0 0 1 1 0 1 0 0 0
                0 1 1 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 1 0 0 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 1 1 0 0 0 1 1 0 1 1 0 0
                1 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 1 0 1 1 1 0 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 1 0 1 0 1
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 1 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 0 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 0 0 0 0 1 0 0 0 1 1 0 0 1 1 1 0 1 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 0 1 1 0
                0 0 1 0 1 0 1 0 0 0 0 0 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 0 1 1 0
                0 0 1 0 1 0 1 0 0 1 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 1 0 1 1 1 0 0 0
                0 0 1 0 1 0 1 0 0 1 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 1 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 1 0 1 0 0 1 1 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 1 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 1 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 1 0 0 0 1 0 0 1 0 1 0 0 1 1 0 1 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 0 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 1 1 0 1 0 0 1 1 0 0 0 0 1 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 0 1 1 1 1 0 0 0 1 0 0 0 1 0 1 1 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 1 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 0 1 1 0 0
                0 0 1 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 1 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 0 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 1 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 1 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 1 1 1 1 0 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 0 1 1 1 1 0 0
                0 1 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 0 1 1 1 1 0 0
                1 0 1 0 1 1 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 1 0 1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 1 0 0 0 1 1 1 1 1 0 0 1 0 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 0 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 0 0 1 0 0 0 1 0 1 0 0 1 0 1 1 0 1 1 0 0 0 0 1 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 1 0 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 1 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 1 0 1 0 0 0 0 1 1 1 1 0 0 1 0 1 0 0 1 1 0 0 0 0 1 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 1 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 0 0 0 1 1 1 0 0 1 0 1 0 0 1 1 1 1 0 0 1 0 0 0 0 0 0 1 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 1 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 0 1 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 1 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 1 1 1 0 0 0 1 0 0 1 0 1 0 0 1 0 0 1 0 1 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 1 0 0 1 0 0 0 0 0 0 0 1 0 1 1 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 0 0 1 0 1 0 0 0 0 1 1 1 1 1 1 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 1 1 1 0 0 0 0 0 0 0 0 0 0 0 1 1 1 0 1 1 0 1 0 0 1 0 0 1 0 0 0 1 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 1 0 0 0 0 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1 0 1 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 1 1 1 1 1 0 0
                0 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 1 1 1 1 0 0 1 0 1 0 0 1 0 1 1 0 0 1 0 0 0 0 0 1 0 0 1 0 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 1 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 1 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 1 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 1 1 1 1 0 0
                0 0 1 0 0 0 1 0 0 0 0 1 1 1 0 1 1 0 0 1 0 1 0 0 0 1 0 0 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 0 0 1 0 1 1 0 0 1 0 0 1 0 0 0 1 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 1 1 0 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 1 0 0 0 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 0 0 0 0 1 0 0 0 0 0 1 0 1 0 1 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 1 0 1 1 1 1 0 0 1 0 1 0 0 0 0 0 1 0 1 0 1 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 1 0 0 1 0 0 0 1 0 1 0 0 0 1 0 1 1 1 0 0
                0 1 1 0 1 0 0 0 0 0 0 1 1 1 0 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 1 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 1 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 0 0 1 0 1 0 0 0 0 1 1 1 0 1 0 0 0 1 0 0 0 0 1 1 0 1 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 1
                0 0 1 0 1 0 1 0 0 0 0 0 1 1 1 1 0 1 0 1 0 0 0 0 0 1 0 1 0 0 0 0 0 1 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 0 0 1 0 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 1 0 1 1 1 0 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 0 1 1 1 0 0 0 0 0 0 0 0 1 1 0 1 0 1 1 1 1 0 0 1 1 0 0 1 0 0 0 0 1 1 0 0 1 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 1 1 0 0
                0 1 1 0 1 0 1 0 0 0 0 1 0 1 1 1 0 0 0 0 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 0 1 1 0 0
                0 0 1 0 1 0 1 0 0 1 0 1 1 1 1 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 1 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 0 0 1 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 1 1 1 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 0
                0 0 1 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 1 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 0 0 0 0 0 1 1 1 1 0 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 0 1 0 1 1 0 1 0 0 0 0 0 1 0 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 1 0 0 1 1 1 1 1 0 0
                0 0 0 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 1 1 1 1 1 1 0 1
                0 0 1 0 1 0 1 0 0 0 0 1 0 0 1 1 0 0 0 1 0 0 0 0 0 0 1 1 1 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 1 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 1 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 1 1 0 1 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 0 0 1 0 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 1 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 0 1 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 0
                0 0 1 0 1 0 0 0 0 0 0 1 0 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 1 0 1 0 0 1 1 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                1 0 1 0 1 0 1 0 0 0 0 1 1 1 1 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 1 0 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 1 0 1 0 0 1 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 0 1 0 0 1 0 1 1 0 0 0 1 1 0 0 1 0 1 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 1 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 1 0 0 1 1 0 0 0 0 0 1 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 1 1 1 0 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 1 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 1
                0 0 1 0 1 0 0 0 0 0 0 1 0 1 1 1 0 1 0 1 0 0 0 0 0 1 0 1 0 1 1 0 1 0 0 1 1 0 0 0 1 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 0 0 0 0 1 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 1 0 0 1 0 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 0 1 1 0 0 0 0 0 0 1 0 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 1 1 0 1 1 1 1 1 0 0
                1 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 1 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 1 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 0 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 1 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 0 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 1 1 1 1 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 1 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 0 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 0 1 1 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 1 1 0 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 1 1 0 1 0 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 1 0 1 1 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 1 0 1 0 0 0 0 1 1 0 1 0 1 1 1 0 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 0 0 1 0 0 1 0 0 1 0 1 0 0 1 0 0 0 0 1 1 0 0 1 0 0 0 0 1 1 0 0 1 1 1 0 0 1 1 1 0 0 1 0 0 1 1 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 1 1 1 1 0 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 1 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 1 0 1 1 1 1 1 0 0
                0 0 1 0 1 0 1 0 0 0 0 1 1 1 1 1 0 1 0 1 0 0 0 0 0 1 0 1 0 0 1 0 1 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 1 0 1 0 0 1 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 1 1 1 1 0 0
            """.trimIndent().split("\n\n\n").let { it[0] to it[1] }
            val plants = parsePlants(input.split("\n\n"))

            val activationStatus = plants.mapNotNull { it.value as? ConnectedBranch }
                .flatMap { it.connections.toList().map { (plant, branch) -> plant to (branch >= 0) } }
                .groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }

            val setPlants = mutableSetOf<Int>()
            activationStatus.forEach { (plant, statuses) ->
                if (statuses.size == 1 && plants[plant] is FreeBranch) {
                    (plants[plant] as? FreeBranch)?.activated = statuses.first()
                    setPlants.add(plant)
                }
            }

            println(plants.keys.subtract(setPlants))

            val maxEnergy = plants[plants.size]!!.energy()

            testCases.lines().sumOf { testCase ->
                val activations = testCase.split(" ").map { it.toInt() }
                plants.filterValues { it is FreeBranch }.forEach { (id, plant) ->
                    (plant as FreeBranch).activated = activations[id - 1] == 1
                }
                plants[plants.size]!!.energy().takeIf { it > 0 }?.let { maxEnergy - it } ?: 0
            }.let { println(it) }

        }
    }
}

fun main(args: Array<String>) {
    Quest18.part1()
    Quest18.part2()
    Quest18.part3()
}

