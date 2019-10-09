package com.example.rockpaperscissors

import android.content.Context

class RoundRepository(context: Context) {

    private var roundDao: RoundDao

    init {
        val roundRoomDatabase = RoundRoomDatabase.getDatabase(context)
        roundDao = roundRoomDatabase!!.roundDao()
    }

    suspend fun getAllRounds(): List<Round> {
        return roundDao.getAllRounds()
    }

    suspend fun insertRound(round: Round) {
        roundDao.insertRound(round)
    }

    suspend fun deleteRound(round: Round) {
        roundDao.deleteRound(round)
    }

    suspend fun deleteAllRounds() {
        roundDao.deleteAllRounds()
    }

    suspend fun getLosses(): Int{
        return roundDao.getLosses()
    }

    suspend fun getDraws(): Int{
        return roundDao.getDraws()
    }

    suspend fun getWins(): Int{
        return roundDao.getWins()
    }


}
