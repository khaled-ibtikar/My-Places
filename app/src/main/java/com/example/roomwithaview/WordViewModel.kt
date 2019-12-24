package com.example.roomwithaview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomwithaview.data.Word
import com.example.roomwithaview.data.WordRepository
import com.example.roomwithaview.data.WordRoomDatabase
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {

        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    fun insertWord(word: Word) = viewModelScope.launch {
        repository.insertWord(word)
    }

    fun deleteWord(word: Word) = viewModelScope.launch {
        repository.deleteWord(word)
    }
}