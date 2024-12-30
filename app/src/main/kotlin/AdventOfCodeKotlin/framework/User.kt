package framework

data class User(val email: String, val token: String) {
    companion object {
        fun allUsers() : List<User> {
            return listOf(User("tjs8938@gmail.com", "53616c7465645f5f1f4164239b4d10e293645aea516c7c040264390f2c0fedd773b15a1238af8110e679efde20f34d9e6cd16ff553a1abf6a4aaed6c10907366"),
                User("tsheppard@solutechnology.com", "53616c7465645f5f9d6a29596eb006d417122807c177eb10bd4e86366676ad80f62bc314b28a22b13e42d400fdcc6c764accb04a2c8e1f37df065eebbdfe80d8"),
                User("sheppard.thomas.j@gmail.com", "53616c7465645f5fe0802fed0616f49b0882f32bc47690e0a4efcafd5541832787fd707dd64f04da40930af349e0b955a5675b43498f239b72ce3b9159e5b85b")
            )
        }
    }
}