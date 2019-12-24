package com.example.roomwithaview.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomwithaview.data.Word

@Dao
interface WordDao {

    @Query("SELECT * from word_table")
    fun getWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)

    @Query("DELETE FROM word_table WHERE word = :word")
    suspend fun deleteWord(word: String)
}