package com.example.hasiruusiru

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trees")
data class Tree(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val species: String,
    val girth: Float,
    val health: String,
    val isEmptyPit: Boolean,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)