package com.epsilon.apps.bilgi.yarismasi.quiz.model

import com.epsilon.apps.bilgi.yarismasi.quiz.R

enum class QuestionCategoryEnum(val id: Int, val categoryName: String, val color: Int) {
    HISTORY(id = 1, categoryName = "Tarih", color = R.color.app_history_color),
    ART(id = 2, categoryName = "Kültür-Sanat", color = R.color.app_art_color),
    MEDIA(id = 5, categoryName = "Medya ve Teknoloji", color = R.color.app_media_color),
    GEOGRAPHY(id = 4, categoryName = "Coğrafya", color = R.color.app_geography_color),
    SPORT(id = 6, categoryName = "Spor", color = R.color.app_sport_color),
    SCIENCE(id = 3, categoryName = "Doğa ve Bilim", color = R.color.app_science_color);
}