package example.com.database.questions

import org.jetbrains.exposed.sql.Random
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object Questions : Table("questions") {
    private val questions_id = Questions.integer("questions_id")
    private val questions_title = Questions.varchar("questions_title", 64)
    private val question = Questions.varchar("question", 64)
    private val ans_wrong_1 = Questions.varchar("ans_wrong_1", 64)
    private val ans_wrong_2 = Questions.varchar("ans_wrong_2", 64)
    private val ans_wrong_3 = Questions.varchar("ans_wrong_3", 64)
    private val ans_correct = Questions.varchar("ans_correct", 64)

    fun getQuestion(): QuestionsDTO? {
        return transaction {
            Questions
                .selectAll()
                .orderBy(Random())
                .limit(1)
                .mapNotNull { row ->
                    QuestionsDTO(
                        questions_id = row[Questions.questions_id],
                        questions_title = row[Questions.questions_title],
                        question = row[Questions.question],
                        ans_wrong_1 = row[Questions.ans_wrong_1],
                        ans_wrong_2 = row[Questions.ans_wrong_2],
                        ans_wrong_3 = row[Questions.ans_wrong_3],
                        ans_correct = row[Questions.ans_correct]
                    )
                }
                .singleOrNull() // Возвращает единственный элемент или null, если нет данных
        }
    }

}