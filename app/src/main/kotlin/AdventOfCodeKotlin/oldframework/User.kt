package framework

data class User(val email: String, val token: String) {
    companion object {
        fun allUsers() : List<User> {
            return listOf(
                User("tjs8938@gmail.com", "53616c7465645f5f0ade16eacf8c9a8a308d786397eade77ac2f44e2664fb62593a72487f88b25691ab2e708fd8f14fcbaf30c9cfe084a84468281d5eed357a3"),
//                User("tsheppard@solutechnology.com", "53616c7465645f5f9d6a29596eb006d417122807c177eb10bd4e86366676ad80f62bc314b28a22b13e42d400fdcc6c764accb04a2c8e1f37df065eebbdfe80d8")
                User("sheppard.thomas.j@gmail.com", "53616c7465645f5f4e0fe192ae6fc13c07179fe25a16cf96d2f19b4a6d64ef0b2c9d0df052d414acf93fd46e1483b17a2c6a5d988d2fa11e3812d5b6e4053c68")
            )
        }
    }
}