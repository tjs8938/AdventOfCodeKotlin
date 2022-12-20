package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.time.Instant

data class RobotFactory(
    val blueprint: Blueprint,
    var timeRemaining: Int = 24,
    var rockPiles: MutableList<Int> = mutableListOf(0, 0, 0, 0),
    var robots: MutableList<Int> = mutableListOf(1, 0, 0, 0),
) {

    fun canBuildRobot(type: Material): Boolean {
        val maxCost = blueprint.robotCosts.values.maxOfOrNull { it[type.ordinal] }!!
        return if (type == Material.GEODE || robots[type.ordinal] < maxCost) {
            val cost = blueprint.robotCosts[type]!!
            Material.values().all {
                rockPiles[it.ordinal] >= cost[it.ordinal]
            }
        } else {
            false
        }
    }

    fun buildRobot(type: Material) {
        val cost = blueprint.robotCosts[type]!!
        Material.values().forEach {
            rockPiles[it.ordinal] -= cost[it.ordinal]
        }
        robots[type.ordinal] += 1
    }

    fun mineMaterials() {
        Material.values().forEach {
            rockPiles[it.ordinal] += robots[it.ordinal]
        }
    }

    fun copyFactory() : RobotFactory {
        return this.copy(rockPiles = this.rockPiles.toMutableList(), robots = this.robots.toMutableList())
    }
}

class Blueprint(input: String) {
    val blueprintNumber: Int

    // material type to triple of (ore cost, clay cost, obsidian cost)
    val robotCosts: Map<Material, List<Int>>

    init {
        Regex("""Blueprint (\d*): Each ore robot costs (\d*) ore. Each clay robot costs (\d*) ore. Each obsidian robot costs (\d*) ore and (\d*) clay. Each geode robot costs (\d*) ore and (\d*) obsidian.""")
            .matchEntire(input)!!
            .groupValues.mapNotNull { it.toIntOrNull() }
            .let {
                blueprintNumber = it[0]
                robotCosts = mapOf(
                    Material.ORE to listOf(it[1], 0, 0, 0),
                    Material.CLAY to listOf(it[2], 0, 0, 0),
                    Material.OBSIDIAN to listOf(it[3], it[4], 0, 0),
                    Material.GEODE to listOf(it[5], 0, it[6], 0)
                )
            }
    }

    override fun toString(): String {
        return "Blueprint(blueprintNumber=$blueprintNumber)"
    }
}

enum class Material { ORE, CLAY, OBSIDIAN, GEODE }

class Day19 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val blueprints = puzzle.getAsString().map { Blueprint(it) }
            val bestForBlueprint = findBestForEachBlueprint(blueprints)

//            println(bestForBlueprint)

            return bestForBlueprint.map { it.key.blueprintNumber * it.value }.sum().toString()
        }

        private fun findBestForEachBlueprint(blueprints: List<Blueprint>, timeToBuild: Int = 24): MutableMap<Blueprint, Int> {
            val bestForBlueprint = mutableMapOf<Blueprint, Int>()
            blueprints.forEach { blueprint ->

                val factories = ArrayDeque(listOf(RobotFactory(blueprint, timeRemaining = timeToBuild)))
                val previousFactories = mutableSetOf<RobotFactory>()
                previousFactories.addAll(factories)

                while (factories.isNotEmpty()) {
                    val factory = factories.removeFirst().copyFactory()
                    val robotsToBuild = Material.values().filter { factory.canBuildRobot(it) }

                    factory.mineMaterials()
                    factory.timeRemaining--
                    if (factory.timeRemaining > 0) {

                        if (Material.GEODE in robotsToBuild) {
                            val newFactory = factory.copyFactory()
                            newFactory.buildRobot(Material.GEODE)
                            addFactory(factories, newFactory, previousFactories)
                        } else if (Material.OBSIDIAN in robotsToBuild) {
                            val newFactory = factory.copyFactory()
                            newFactory.buildRobot(Material.OBSIDIAN)
                            addFactory(factories, newFactory, previousFactories)

                            addFactory(factories, factory, previousFactories)
                        } else {

                            robotsToBuild.reversed().forEach {
                                val newFactory = factory.copyFactory()
                                newFactory.buildRobot(it)
                                addFactory(factories, newFactory, previousFactories)
                            }
                            addFactory(factories, factory, previousFactories)
                        }

                    } else {
                        if (!bestForBlueprint.containsKey(factory.blueprint) || bestForBlueprint[factory.blueprint]!! < factory.rockPiles[Material.GEODE.ordinal]) {
                            bestForBlueprint[factory.blueprint] =
                                factory.rockPiles[Material.GEODE.ordinal]
                        }
                    }
                }
            }
            return bestForBlueprint
        }

        private fun addFactory(
            factories: ArrayDeque<RobotFactory>,
            factory: RobotFactory,
            previousFactories: MutableSet<RobotFactory>
        ) {
            if (!previousFactories.contains(factory)) {
                previousFactories.add(factory)
                factories.addFirst(factory)
            }
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val blueprints = puzzle.getAsString().slice(0..2).map { Blueprint(it) }
            val bestForBlueprint = findBestForEachBlueprint(blueprints, 32)

            return bestForBlueprint.map { it.value }.reduce(Int::times).toString()
        }
    }
}

fun main() {

    val example = ExamplePuzzle(
        "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n" +
            "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."
    )

//    assert(Day19.part1(example) == "33")

    Runner.solve(2022, 19, part1 = Day19::part1)
    Runner.solve(2022, 19, part2 = Day19::part2)
}
