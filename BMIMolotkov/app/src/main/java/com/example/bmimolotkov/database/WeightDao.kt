package com.example.bmimolotkov.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface WeightDao {

    @Insert
    fun addWeight(weight: Weight)

    @Update
    fun updateWeight(weight: Weight)

    @Delete
    fun deleteWeight(weight: Weight)

    @Query("SELECT * FROM weight WHERE id = :weightId")
    fun getWeight(weightId: Int): Weight?

    @Query("SELECT * FROM weight ORDER BY dateOfWeight DESC")
    fun getAllWeights(): LiveData<List<Weight>>

    @Query("SELECT * FROM weight ORDER BY dateOfWeight DESC LIMIT 1")
    fun getLastWeight(): Weight?

    @Query("SELECT * FROM weight WHERE dateOfWeight > :startDate")
    fun getWeightsByDate(startDate: LocalDateTime): LiveData<List<Weight>>
}