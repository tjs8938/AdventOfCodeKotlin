package framework

data class User(val email: String, val token: String) {
    companion object {
        fun allUsers() : List<User> {
            return listOf(User("tsheppard@solutechnology.com", "53616c7465645f5f4e553e8646556d799ba87d8e49481a16ce056ee227098201b9fcec45ec72ce9fa65631be92a4f13a24c82911c0f9b9d36241b341497ae9d7"),
                User("tjs8938@gmail.com", "53616c7465645f5f156d9368f33edb792ecffa1957290d6c2741d30bdc41d9450891cfac39622c81754201f5b7fec37356f0dd4797a0ddae87262da5794d79fd")
                ).reversed()
        }
    }
}