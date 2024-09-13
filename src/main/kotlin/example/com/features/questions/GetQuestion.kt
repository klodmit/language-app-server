package example.com.features.userinfo


import example.com.database.questions.Questions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureQuestionsRouting() {
    routing {
        get("/getQuestion") {
            val question = Questions.getQuestion()
            if (question != null) {
                call.respond(question)
            } else {
                call.respond(HttpStatusCode.NotFound, "Question not found")
            }
        }
    }
}