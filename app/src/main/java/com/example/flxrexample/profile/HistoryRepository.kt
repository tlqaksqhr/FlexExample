package com.example.flxrexample.profile

object HistoryRepository {

    fun getHistories() : MutableList<History>{
        return mutableListOf(
            History("user1","profile1",1,10),
            History("user2","profile2",2,20),
            History("user3","profile3",3,30),
            History("user4","profile4",4,40)
        )
    }
}