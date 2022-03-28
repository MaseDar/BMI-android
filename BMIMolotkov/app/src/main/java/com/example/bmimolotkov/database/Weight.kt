package com.example.bmimolotkov.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "weight")
class Weight(
    @PrimaryKey
    val id: Int,

    var weight: Float,

    val dateOfWeight: LocalDateTime,

    var difference: Float,

    val indexWeight: Float
)