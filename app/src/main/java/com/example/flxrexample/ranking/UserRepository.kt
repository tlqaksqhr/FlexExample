package com.example.flxrexample.ranking

object UserRepository {

    fun getUsers() : MutableList<User>{
        return mutableListOf(
            User("user1","profile1",1,10),
            User("user2","profile2",2,20),
            User("user3","profile3",3,30),
            User("user4","profile4",4,40)
        )
    }
}