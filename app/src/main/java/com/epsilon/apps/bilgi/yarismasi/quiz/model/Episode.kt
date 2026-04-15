package com.epsilon.apps.bilgi.yarismasi.quiz.model

import com.epsilon.apps.bilgi.yarismasi.quiz.R

enum class Episode(val id: Int, val episodeName: String, val chapter: Chapter, val imageId: Int, val numberOfLevels : Int = 20) {
    LONDON(id = 1, episodeName = "Londra", chapter = Chapter.CITIES, imageId = R.drawable.london),
    PARIS(id = 2, episodeName = "Paris", chapter = Chapter.CITIES, imageId = R.drawable.paris),
    NEW_YORK(id=3, episodeName = "New York", chapter = Chapter.CITIES, imageId = R.drawable.new_york),
    TOKYO(id=4, episodeName = "Tokyo", chapter = Chapter.CITIES, imageId = R.drawable.tokyo),
    BERLIN(id = 5, episodeName = "Berlin", chapter = Chapter.CITIES, imageId = R.drawable.berlin),
    ISTANBUL(id = 6, episodeName = "İstanbul", chapter = Chapter.CITIES, imageId = R.drawable.istanbul),
    DUBAI(id = 7, episodeName = "Dubai", chapter = Chapter.CITIES, imageId = R.drawable.dubai),
    AMSTERDAM(id = 8, episodeName = "Amsterdam", chapter = Chapter.CITIES, imageId = R.drawable.amsterdam),

}