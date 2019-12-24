package com.example.roomwithaview.data

import androidx.lifecycle.LiveData

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getWords()

    suspend fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word.word)
    }
}