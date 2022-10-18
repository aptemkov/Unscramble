package com.github.aptemkov.unscramble.ui.game

data class Question (
    val word: String,
    val hint: String = "Падказка адсутнічае",
    var hints: Int = 0
) {
    val firstLetterHint: String
    get() = word.first().toString()
}