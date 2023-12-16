package AdventOfCodeKotlin.framework

import framework.User
import java.io.File
import org.jsoup.Jsoup
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

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

private const val RESOURCE_PATH = "C:\\personal\\AdventOfCodeKotlin\\app\\src\\main\\resources"

class Puzzle(val year: Int, val day: Int, val user: User) : PuzzleInputProvider {

    val INPUT_URL = "$URL/input"
    val SUBMIT_URL = "$URL/answer"

    override fun get() : String {

        val filename: String = String.format("$RESOURCE_PATH\\%d\\Day%02d\\%s\\input.txt", year, day, user.token)
        val inputFile = File(filename)
        if (!inputFile.exists()) {
            // Need to get input from AoC
            val response = khttp.get(
                url = String.format(INPUT_URL, year, day),
                cookies = mapOf("session" to user.token)
            )
            File(String.format("$RESOURCE_PATH\\%d\\Day%02d\\%s", year, day, user.token)).mkdirs()
            inputFile.writeText(response.text)
        }

        return inputFile.readText().trimEnd()
    }

    override fun post(answer: String, part: Int) {
        if (answer == "") {
            return
        }
        val mapper = jacksonObjectMapper()
        val submissionFilename = String.format("$RESOURCE_PATH\\%d\\Day%02d\\%s\\submissions.json", year, day, user.token)
        val submissionFile = File(submissionFilename)
        val submissions: MutableMap<Int, MutableMap<String, String>> = if (submissionFile.exists()) {
            mapper.readValue(submissionFile.readText())
        } else {
            mutableMapOf()
        }

        val message = if (submissions.containsKey(part) && submissions[part]?.containsKey(answer) == true && !submissions[part]!![answer]!!.contains("You gave an answer too recently")) {
            submissions[part]?.get(answer)
        } else {
            val response = khttp.post(
                url = String.format(SUBMIT_URL, year, day),
                cookies = mapOf("session" to user.token),
                data = mapOf("level" to part, "answer" to answer)
            )
            val parsed = Jsoup.parse(response.text)
            val resp = parsed.allElements.find { it.tagName() == "article" }?.text()!!

            val a_map = submissions.getOrDefault(part, mutableMapOf())
            a_map[answer] = resp
            submissions[part] = a_map
            mapper.writeValue(submissionFile, submissions)

            // return the response to message
            resp
        }
        println(message)
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
