package AdventOfCodeKotlin.framework

import framework.User
import java.io.File
import org.jsoup.Jsoup

interface PuzzleInputProvider {
    fun get() : String

    fun post(answer: String, part: Int)

    fun getAsString() : List<String> {
        return get().split("\r\n", "\n")
    }

    fun getAsInt() : List<Int> {
        return getAsString().map { it.toInt() }
    }
}

private const val URL = "https://adventofcode.com/%d/day/%d"

class Puzzle(val year: Int, val day: Int, val user: User) : PuzzleInputProvider {

    val INPUT_URL = "$URL/input"
    val SUBMIT_URL = "$URL/answer"

    override fun get() : String {

        val filename: String = String.format("C:\\personal\\AdventOfCodeKotlin\\app\\src\\main\\resources\\%d\\Day%02d\\%s\\input.txt", year, day, user.token)
        val inputFile = File(filename)
        if (!inputFile.exists()) {
            // Need to get input from AoC
            val response = khttp.get(
                url = String.format(INPUT_URL, year, day),
                cookies = mapOf("session" to user.token)
            )
            File(String.format("C:\\personal\\AdventOfCodeKotlin\\app\\src\\main\\resources\\%d\\Day%02d\\%s", year, day, user.token)).mkdirs()
            inputFile.writeText(response.text)
        }

        return inputFile.readText().trimEnd()
    }

    override fun post(answer: String, part: Int) {
        if (answer == "") {
            return
        }
        val response = khttp.post(
            url = String.format(SUBMIT_URL, year, day),
            cookies = mapOf("session" to user.token),
            data=mapOf("level" to part, "answer" to answer)
        )
        val parsed = Jsoup.parse(response.text)
        println(parsed.allElements.find { it.tagName() == "article" }?.text())
    }
}

class ExamplePuzzle(private val input: String) : PuzzleInputProvider {
    override fun get(): String {
        return input
    }

    override fun post(answer: String, part: Int) {
        // do nothing
    }

}
