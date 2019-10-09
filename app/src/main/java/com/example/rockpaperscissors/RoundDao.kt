package com.example.rockpaperscissors

import androidx.room.*

@Dao
interface RoundDao {

    @Query("SELECT * FROM round_table")
    suspend fun getAllRounds(): List<Round>

    @Insert
    suspend fun insertRound(round: Round)

    @Delete
    suspend fun deleteRound(round: Round)

    @Query("DELETE FROM round_table")
    suspend fun deleteAllRounds()

    @Query("SELECT COUNT(*) FROM round_table WHERE result = 0")
    suspend fun getLosses(): Int

    @Query("SELECT COUNT(*) FROM round_table WHERE result = 1")
    suspend fun getDraws(): Int

    @Query("SELECT COUNT(*) FROM round_table WHERE result = 2")
    suspend fun getWins(): Int

}
