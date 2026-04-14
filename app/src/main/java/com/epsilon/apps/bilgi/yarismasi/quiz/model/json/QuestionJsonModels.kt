package com.epsilon.apps.bilgi.yarismasi.quiz.model.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class QuestionJson(
    @Json(name = "soru_metni") val questionText: String,
    @Json(name = "secenekler") val options: QuestionOptionsJson,
    @Json(name = "dogru_cevap") val correctAnswer: String,
    @Json(name = "zorluk_derecesi") val difficulty: Int,
    @Json(name = "kategori_id") val categoryId: Int,
    @Json(name = "kategori_adi") val categoryName: String,
    @Json(name = "bilgi_notu") val infoNote: String,
    @Json(name = "hashtags") val hashtags: List<String>
)

@JsonClass(generateAdapter = true)
internal data class QuestionOptionsJson(
    @Json(name = "a") val a: String,
    @Json(name = "b") val b: String,
    @Json(name = "c") val c: String,
    @Json(name = "d") val d: String,
    @Json(name = "e") val e: String
)
