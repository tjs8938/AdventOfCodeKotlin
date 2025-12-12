package AdventOfCodeKotlin.framework

open class User : HashMap<String, String>() {
    companion object {
        fun allUsers(): List<User> {
            return listOf()
        }

        fun <T : User> users(type: Class<T>): List<T> {
            return allUsers().filter { type.isInstance(it) }.map { type.cast(it)!! }
        }
    }
}