package com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases

import com.epsilon.apps.bilgi.yarismasi.quiz.model.ChapterEnum

class ChapterCase {
    fun getChapterById(id: Int): ChapterEnum {
        return requireNotNull(ChapterEnum.entries.firstOrNull { it.id == id })
    }
}
