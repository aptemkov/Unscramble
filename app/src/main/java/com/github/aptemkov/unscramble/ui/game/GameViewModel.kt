package com.github.aptemkov.unscramble.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private var _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    private var hints = 0


    private var wordsList = mutableListOf<String>()
    private lateinit var currentWord: Question

    init {
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val scrambled = currentWord.word.toCharArray()
        scrambled.shuffle()

        while (String(scrambled).equals(currentWord.word, false)) {
            scrambled.shuffle()
        }
        if (wordsList.contains(currentWord.word)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(scrambled)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord.word)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)

    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord.word, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializationData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    fun getHint():String {
        when(currentWord.hints) {
            0 -> { currentWord.hints++; return currentWord.firstLetterHint }
            1 -> { currentWord.hints++; return currentWord.hint }
        }
        return ""
    }
}