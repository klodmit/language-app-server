package example.com.database.questions

import kotlinx.serialization.Serializable

@Serializable
class QuestionsDTO (
    val questions_id: Int,
    val questions_title: String,
    val question: String,
    val ans_wrong_1: String,
    val ans_wrong_2: String,
    val ans_wrong_3: String,
    val ans_correct: String,
)