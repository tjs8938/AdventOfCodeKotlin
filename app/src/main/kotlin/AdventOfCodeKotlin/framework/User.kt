package framework

data class User(val email: String, val token: String) {
    companion object {
        fun allUsers() : List<User> {
            return listOf(User("tsheppard@solutechnology.com", "53616c7465645f5ffc7a5b1e3314922dfa8d99a67ee81ece736a52c29674200e3769e3d145dc5b4b474fae38864e3e76964b878ddf431ce3f9d9dc3bec58c544"),
                User("tjs8938@gmail.com", "53616c7465645f5f5dfb60fe167571b808233e37a155356b8d7a5bff35c7cad035ac48b72ee67fb75b6d47891c65d7db5ccbd9de408e13011b659d7950e150d1")
                )
        }
    }
}