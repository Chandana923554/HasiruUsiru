package com.example.hasiruusiru

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TreeDao {

    @Insert
    fun insertTree(tree: Tree)

    @Query("SELECT * FROM trees")
    fun getAllTrees(): List<Tree>

    @Query("SELECT COUNT(*) FROM trees")
    fun getTreeCount(): Int

    @Query("SELECT COUNT(*) FROM trees WHERE isEmptyPit = 1")
    fun getEmptyPitCount(): Int

    @Query("SELECT COALESCE(SUM(girth), 0) FROM trees WHERE isEmptyPit = 0")
    fun getTotalGirth(): Float
}