package AdventOfCodeKotlin.framework

import java.time.Instant

abstract class Puzzle(val params: ParamProvider, val api: PuzzleAPI) {

    val examples = mutableMapOf<Int, MutableList<Example>>()

    fun addExample(part: Int, example: Example) {
        examples.getOrPut(part, ::mutableListOf).add(example)
    }

    fun get(user: User): String {
        return api.get(params, user)
    }


    fun post(answer: String, part: Int, user: User) {
        api.post(answer, part, params, user)
    }

    fun part1(input: String): String {
        return ""
    }

    fun part2(input: String): String {
        return ""
    }

    fun part3(input: String): String {
        return ""
    }

    fun execute() {
        println(params)
        executePart(1, ::part1)
        executePart(2, ::part2)
        executePart(3, ::part3)
    }

    fun executePart(part: Int, solver: (String) -> String) {
        println("Solving Part $part")
        examples[part]?.forEachIndexed { index, example ->
            val answer = solver.invoke(example.input)
            println("Example $index is $answer")
            if (answer != example.expected) {
                error("Example $index failed: expected '${example.expected}' but got '$answer'")
            }
        }
        User.users(userType()).forEach { user ->
            println("User $user")
            solver.let {
                val startTime = Instant.now()
                val answer = it.invoke(get(user))
                println(
                    "Answer $answer - calculated in ${
                        Instant.now().toEpochMilli() - startTime.toEpochMilli()
                    } ms"
                )
                post(answer, part, user)
            }
        }

    }

    abstract fun <T : User> userType() : Class<T>

}


fun String.getAsString(): List<String> {
    return split("\r\n", "\n")
}

fun String.getAsGrid(): List<List<Char>> {
    return getAsString().map { it.toList() }
}

fun String.getAsInt(): List<Int> {
    return getAsString().map { it.toInt() }
}